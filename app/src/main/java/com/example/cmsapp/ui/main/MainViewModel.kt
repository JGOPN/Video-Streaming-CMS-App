package com.example.cmsapp.ui.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel( /*private val itemsRepository: ItemsRepository */ ) : ViewModel() {

    private val _mainUiState = MutableStateFlow(MainUiState())
    val mainUiState: StateFlow<MainUiState> = _mainUiState.asStateFlow()

    fun setIsDisplayingUsers(value : Boolean = false){
        /*Toggles between videos/users list. true = user list, false = video list*/
        _mainUiState.update {
                MainUiState(isDisplayingUsers = value)
        }
    }

    fun toggleCardExpansion(cardId: Int) {
        _mainUiState.update {
            currentState -> currentState.copy(expandedCardId = cardId)
        }
    }

    /*fun updateUiState(username : String = _mainUiState.value.username, password: String =  _mainUiState.value.password) {
        _mainUiState.update { currentState ->
            currentState.copy(
                username = username,
                password = password
            )
        }
    }*/

}

data class MainUiState(
    val isDisplayingUsers : Boolean = false,
    val expandedCardId : Int = -1
)
