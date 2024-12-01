package com.example.cmsapp.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cmsapp.model.Movie
import com.example.cmsapp.network.CMSApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

data class MovieEntryState(
    val movieEntry : Movie = Movie(
        id = 0,
        title = "",
        description = "",
        releaseYear = 1990,
        submittedBy = 1,
        duration = 0,
        genres = listOf()
    ),
    val movieUrl: String = "",
    val isDialogOpen : Boolean = false
)

class MovieEntryViewModel() : ViewModel(){
    private val _movieEntryState = MutableStateFlow(MovieEntryState())
    val movieEntryState: StateFlow<MovieEntryState> = _movieEntryState.asStateFlow()

    fun updateMovieEntryState(
        movieEntry: Movie = _movieEntryState.value.movieEntry
    ) {
        _movieEntryState.update {
            MovieEntryState(movieEntry)
        }
    }

    fun updateMovieURL(url : String){
        _movieEntryState.update {
            currentState -> currentState.copy(movieUrl = url)
        }
    }


    fun toggleConfirmationDialog() {
        _movieEntryState.update {
                currentState -> currentState.copy(isDialogOpen = !currentState.isDialogOpen)
        }
    }

    fun validateMovieEntry(): List<String> {

        val errors = mutableListOf<String>()
        val movie = _movieEntryState.value.movieEntry

        if (movie.title.isBlank()) {
            errors.add("Title must not be empty.")
        }

        if (movie.description.isBlank()) {
            errors.add("Description must not be empty.")
        } else if (movie.description.length > 500) {
            errors.add("Description must not exceed 500 characters.")
        }

        val currentYear = LocalDateTime.now().year
        if (movie.releaseYear < 1888 || movie.releaseYear > currentYear) { // First film released in 1888
            errors.add("Release year must be between 1888 and $currentYear.")
        }

        if (movie.duration <= 0) {
            errors.add("Duration must be a positive number.")
        }

        if (movie.genres.isEmpty()) {
            errors.add("At least one genre must be specified.")
        }

        if(_movieEntryState.value.movieUrl.isBlank()){
            errors.add("Select a local video or input a URL.")
        }

        return errors
    }

    fun addMovie(onResult: (Boolean) -> Unit){
        val movieEntry = _movieEntryState.value.movieEntry
        Log.d("MainActivity","adding movie ${movieEntry.title}")

        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) { CMSApi.retrofitService.addMovie(movieEntry) }
            }.onSuccess { response ->
                if (response.isSuccessful) {
                    Log.d("MainActivity", "Movie added successfully")
                    onResult(true)
                } else {
                    Log.e("MainActivity", "Failed to add movie: ${response.code()} ${response.message()}")
                    onResult(false)
                }
            }.onFailure { exception ->
                handleExceptions(exception)
                onResult(false)
            }
        }
    }
}