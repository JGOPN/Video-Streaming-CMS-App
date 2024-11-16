package com.example.cmsapp.ui.main

import androidx.lifecycle.ViewModel
import com.example.cmsapp.model.Movie
import com.example.cmsapp.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period

enum class MainScreens(val title : String){
    UserList("Users"),
    MovieList("Movies"),
    AddUser("Add new user"),
    AddMovie("Add new movie")
}

class MainViewModel( /*private val itemsRepository: ItemsRepository */ ) : ViewModel() {

    private val _mainUiState = MutableStateFlow(MainUiState())
    val mainUiState: StateFlow<MainUiState> = _mainUiState.asStateFlow()


    fun setCurrentScreen(screen: MainScreens){
        _mainUiState.update {
            MainUiState(currentScreen = screen)
        }
    }

    fun toggleCardExpansion(cardId: Int) {
        _mainUiState.update {
            currentState -> currentState.copy(expandedCardId = cardId)
        }
    }
}

data class MainUiState(
    //val isDisplayingUsers : Boolean = false, //true = is showing userlist, false = is showing movielist
    val expandedCardId : Int = -1,  //id for the selected movie or user card
    val currentScreen : MainScreens = MainScreens.UserList
)
