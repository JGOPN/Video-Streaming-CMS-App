package com.example.cmsapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cmsapp.network.CMSApi
import com.example.cmsapp.ui.main.handleExceptions
import com.example.cmsapp.ui.main.hashPassword
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AuthViewModel() : ViewModel() {

    private val _authUiState = MutableStateFlow(AuthUiState())
    val authUiState: StateFlow<AuthUiState> = _authUiState.asStateFlow()

    fun updateUiState(username : String = _authUiState.value.username, password: String =  _authUiState.value.password, isDialogOpen: Boolean = _authUiState.value.isDialogOpen) {
        _authUiState.update { currentState ->
                currentState.copy(
                    username = username,
                    password = password,
                    isDialogOpen = isDialogOpen
                )
            }
    }

    fun toggleConfirmationDialog() {
        _authUiState.update {
                currentState -> currentState.copy(isDialogOpen = !currentState.isDialogOpen)
        }
    }

    fun validateLoginInput(): List<String> {
        //Validate input and returns a list of errors (or empty list)
        val errorList = mutableListOf<String>()

        if (_authUiState.value.username.isBlank()) errorList.add("Username cannot be blank")

        if (_authUiState.value.password.isBlank()) errorList.add("Password cannot be blank")

        return errorList
    }

    fun authenticate(onResult: (Boolean) -> Unit){
    }

    fun logIn(){

    }

}

data class AuthUiState(
    val username : String = "",
    val password : String = "",
    val isDialogOpen : Boolean = false
)
