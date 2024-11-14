package com.example.cmsapp.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class AuthViewModel( /*private val itemsRepository: ItemsRepository */ ) : ViewModel() {


    private val _authUiState = MutableStateFlow(AuthUiState())
    val authUiState: StateFlow<AuthUiState> = _authUiState.asStateFlow()

    fun toggleUiState(){
        /*Toggles between register/login. false = login screen, true = register screen*/
        _authUiState.update {
            currentState ->
                currentState.copy(isRegisterMode = !currentState.isRegisterMode)
        }
    }

    fun updateUiState(username : String = _authUiState.value.username, password: String =  _authUiState.value.password) {
        _authUiState.update { currentState ->
                currentState.copy(
                    username = username,
                    password = password
                )
            }
    }

    private fun validateInput(): Boolean {
        return (_authUiState.value.username.isNotBlank() && _authUiState.value.password.isNotBlank())
    }

}

data class AuthUiState(
    val isRegisterMode : Boolean = false,
    val username : String = "",
    val password : String = "",
)
