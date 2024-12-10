package com.example.cmsapp.ui.main

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import androidx.lifecycle.viewModelScope
import com.example.cmsapp.model.Movie
import com.example.cmsapp.model.User
import com.example.cmsapp.network.CMSApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException


enum class MainScreens(val title : String){
    UserList("Users"),
    MovieList("Movies"),
    AddUser("Add new user"),
    AddMovie("Add new movie")
}

data class MainUiState(
    val currentScreen : MainScreens = MainScreens.UserList,
    val expandedCardId : Int = -1,  //id for the selected movie or user card
    val userList : List<User> = listOf(),
    val movieList : List<Movie> = listOf()
)


class MainViewModel( /*private val itemsRepository: ItemsRepository */ ) : ViewModel() {

    init {
        getUserList()
        getMovieList()
    }

    private val _mainUiState = MutableStateFlow(MainUiState())
    val mainUiState: StateFlow<MainUiState> = _mainUiState.asStateFlow()

    private val _dialogState = mutableStateOf(false)    //confirmation dialog
    val dialogState: State<Boolean> = _dialogState

    private val _selectedId = mutableStateOf<Int?>(null)        //selected id for delete
    val selectedUserId: State<Int?> = _selectedId

    fun setCurrentScreen(screen: MainScreens){
        _mainUiState.update {
            currentState -> currentState.copy(currentScreen = screen)
        }
    }

    fun toggleCardExpansion(cardId: Int) {
        _mainUiState.update {
            currentState -> currentState.copy(expandedCardId = cardId)
        }
    }

    fun showDialog(id: Int) {
        _selectedId.value = id
        _dialogState.value = true
    }

    fun hideDialog() {
        _selectedId.value = null
        _dialogState.value = false
    }


    fun confirmDelete(onDelete: (Int) -> Unit) {
        _selectedId.value?.let { id ->
            onDelete(id)
        }
        hideDialog()
    }

    fun getMovieList() {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) { CMSApi.retrofitService.getMovies() }
            }.onSuccess { movieList ->
                _mainUiState.update { currentState ->
                    currentState.copy(movieList = movieList)
                }
            }.onFailure { exception ->
                handleExceptions(exception)
            }
        }
    }

    fun getUserList() {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) { CMSApi.retrofitService.getUsers() }
            }.onSuccess { userList ->
                _mainUiState.update { currentState ->
                    currentState.copy(userList = userList) }
            }.onFailure { exception ->
                handleExceptions(exception)
            }
        }
    }

    fun deleteUser(id : Int){
        Log.d("MainActivity","deleting user $id")
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) { CMSApi.retrofitService.deleteUser(id) }
            }.onSuccess { response ->
                if (response.isSuccessful) {
                    _mainUiState.update { currentState ->
                        currentState.copy(
                            userList = currentState.userList.filter { it.id != id }
                        )
                    }
                    Log.d("MainActivity", "User deleted successfully")
                } else {
                    Log.e("MainActivity", "Failed to delete user: ${response.code()} ${response.message()}")
                }
            }.onFailure { exception ->
                handleExceptions(exception)
            }
        }
    }

    fun deleteMovie(id : Int){
        Log.d("MainActivity","deleting video $id")
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) { CMSApi.retrofitService.deleteMovie(id) }
            }.onSuccess { response ->
                if (response.isSuccessful) {
                    _mainUiState.update { currentState ->
                        currentState.copy(
                            movieList = currentState.movieList.filter { it.id != id }
                        )
                    }
                    Log.d("MainActivity", "Movie deleted successfully")
                } else {
                    Log.e("MainActivity", "Failed to delete movie: ${response.code()} ${response.message()}")
                }
            }.onFailure { exception ->
                handleExceptions(exception)
            }
        }
    }
}

fun handleExceptions(exception: Throwable){
    when (exception) {
        is IOException -> Log.e("Network", "Network error: ${exception.message}")
        is HttpException -> Log.e("Network", "HTTP error: ${exception.message}")
        else -> Log.e("Network", "Unexpected error: ${exception.message}")
    }
}
