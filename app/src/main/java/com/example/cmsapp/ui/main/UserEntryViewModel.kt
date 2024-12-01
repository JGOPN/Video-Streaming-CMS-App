package com.example.cmsapp.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cmsapp.model.User
import com.example.cmsapp.network.CMSApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
import kotlinx.datetime.*
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi


data class UserEntryState(
    val userEntry : User = User(0,"","",LocalDate(2000,1,1),"","",false),
    val isDialogOpen : Boolean = false,
    val plainPassword : String = ""
)

class UserEntryViewModel() : ViewModel() {
    private val _userEntryState = MutableStateFlow(UserEntryState())
    val userEntryState: StateFlow<UserEntryState> = _userEntryState.asStateFlow()

    fun updateUserEntryState(userEntry: User = _userEntryState.value.userEntry) {
        _userEntryState.update {
                currentState -> currentState.copy(userEntry = userEntry)
        }
    }

    fun updatePlainPassword(pwd: String){
        _userEntryState.update {
                currentState -> currentState.copy(plainPassword = pwd)
        }
    }

    fun toggleConfirmationDialog(isOpen: Boolean? = null) {
        _userEntryState.update { currentState ->
            currentState.copy(isDialogOpen = isOpen ?: !currentState.isDialogOpen)
        }
    }

    fun validateUserEntry() : List<String>{
        val errorList = mutableListOf<String>()
        val userEntry = _userEntryState.value.userEntry
        val password = _userEntryState.value.plainPassword

        if (userEntry.username.isBlank())
            errorList.add("Username cannot be blank.")

        if (userEntry.username.length < 6)
            errorList.add("Username must be at least 6 characters long.")

        // Validate password
        if (password.length < 8)
            errorList.add("Password must be at least 8 characters long.")
        if (!password.any { it.isDigit() })
            errorList.add("Password must contain at least one number.")
        if (!password.any { it.isUpperCase() })
            errorList.add("Password must contain at least one uppercase letter.")

        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
        if (!emailRegex.matches(userEntry.email))
            errorList.add("Email is not valid.")

        val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val age = userEntry.birthdate.yearsUntil(currentDate)
        if (age < 13) errorList.add("User must be at least 13 years old to create an account.")
        if (age > 120) errorList.add("Please input a valid age.")

        return errorList
        }

    private fun generatePassword(){
        val salt = generateSalt()
        val hashedPassword = hashPassword(_userEntryState.value.plainPassword, salt)
        updateUserEntryState(_userEntryState.value.userEntry.copy(password = hashedPassword, salt = salt))
    }

    fun addUser(onResult: (Boolean) -> Unit){
        generatePassword() // generates a salt, hashes the plain password with salt
        val userEntry = _userEntryState.value.userEntry
        Log.d("MainActivity","adding user ${userEntry.username}")

        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) { CMSApi.retrofitService.addUser(userEntry) }
            }.onSuccess { response ->
                if (response.isSuccessful) {
                    Log.d("MainActivity", "User added successfully")
                    onResult(true)
                } else {
                    Log.e("MainActivity", "Failed to add user: ${response.code()} ${response.message()}")
                    onResult(false)
                }
            }.onFailure { exception ->
                handleExceptions(exception)
                onResult(false)
            }
        }
    }
}

@OptIn(ExperimentalEncodingApi::class)
private fun generateSalt(): String {
    val random = SecureRandom()
    val salt = ByteArray(16)
    random.nextBytes(salt)
    return Base64.Default.encode(salt)
}

@OptIn(ExperimentalEncodingApi::class)
fun hashPassword(password: String, salt: String): String {
    val iterations = 65536
    val keyLength = 256
    val saltBytes = Base64.Default.decode(salt)

    val spec = PBEKeySpec(password.toCharArray(), saltBytes, iterations, keyLength)
    val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
    val hash = factory.generateSecret(spec).encoded
    return Base64.Default.encode(hash)
}