package com.example.cmsapp.ui.main

import androidx.lifecycle.ViewModel
import com.example.cmsapp.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

enum class MainScreens{
    UserList,
    MovieList,
    AddUser,
    AddMovie
}

class MainViewModel( /*private val itemsRepository: ItemsRepository */ ) : ViewModel() {


    private val _mainUiState = MutableStateFlow(MainUiState())
    val mainUiState: StateFlow<MainUiState> = _mainUiState.asStateFlow()

    private val _userEntryState = MutableStateFlow(UserEntryState())
    val userEntryState: StateFlow<UserEntryState> = _userEntryState.asStateFlow()

    fun setCurrentScreen(screen: MainScreens){
        _mainUiState.update {
            MainUiState(currentScreen = screen)
        }
    }

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

    fun updateUserEntryState(
        userEntry: User = _userEntryState.value.userEntry
    ) {
        _userEntryState.update {
            UserEntryState(userEntry)
        }
    }

}

data class MainUiState(
    val isDisplayingUsers : Boolean = false, //true = is showing userlist, false = is showing movielist
    val expandedCardId : Int = -1,  //id for the selected movie or user card
    val currentScreen : MainScreens = MainScreens.UserList
)

data class UserEntryState(
   val userEntry : User = User(0,"","","",false, LocalDate.of(1,1,1))
)
