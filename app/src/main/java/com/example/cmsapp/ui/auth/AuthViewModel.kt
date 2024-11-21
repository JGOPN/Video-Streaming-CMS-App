package com.example.cmsapp.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class AuthViewModel( /*private val itemsRepository: ItemsRepository */ ) : ViewModel() {

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

}

data class AuthUiState(
    val username : String = "",
    val password : String = "",
    val isDialogOpen : Boolean = false
)
