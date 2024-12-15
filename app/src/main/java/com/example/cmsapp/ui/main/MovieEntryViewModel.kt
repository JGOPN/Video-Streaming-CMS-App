package com.example.cmsapp.ui.main

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
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
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import java.io.InputStream
import java.time.LocalDateTime

data class MovieEntryState(
    val movieEntry : Movie = Movie(
        id = 0,
        title = "",
        description = "",
        releaseYear = 1990,
        submittedBy = 1,
        duration = 0,
        genres = listOf(),
        movieUrl = null, //this is only not null if user is sending url instead of file
    ),
    val movieUrl: String = "", //textfield to send a url to a movie instead of sending a local file
    val fileUri: Uri = Uri.EMPTY,
    val isDialogOpen : Boolean = false,
    val urlFieldEnabled: Boolean = true,
    val allGenres: List<String> = listOf()
)

class MovieEntryViewModel() : ViewModel(){

    private val _movieEntryState = MutableStateFlow(MovieEntryState())
    val movieEntryState: StateFlow<MovieEntryState> = _movieEntryState.asStateFlow()

    fun updateMovieEntryState(
        movieEntry: Movie = _movieEntryState.value.movieEntry
    ) {
        _movieEntryState.update { currentState ->
            currentState.copy(movieEntry = movieEntry)
        }
    }

    fun updateMovieURL(url : String){
        _movieEntryState.update {
            currentState -> currentState.copy(movieUrl = url)
        }
    }

    fun updateMovieUri(uri : Uri){
        _movieEntryState.update {
                currentState -> currentState.copy(fileUri = uri)
        }
    }

    fun toggleConfirmationDialog() {
        _movieEntryState.update {
                currentState -> currentState.copy(isDialogOpen = !currentState.isDialogOpen)
        }
    }

    fun toggleUrlField(enabled: Boolean = true) {
        _movieEntryState.update {
                currentState -> currentState.copy(urlFieldEnabled = enabled)
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

        if (movie.duration >= 300) {
            errors.add("Duration must be lower than 5 hours.")
        }

        if (movie.genres.isEmpty()) {
            errors.add("At least one genre must be specified.")
        }

/*        if(_movieEntryState.value.movieUrl.isBlank()){
            errors.add("Select a local video or input a URL.")
        }*/

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

    fun getFilePathFromUri(context: Context, uri: Uri): String? {
        var path: String? = null
        val projection = arrayOf(MediaStore.Video.Media.DATA)
        val cursor: Cursor? = context.contentResolver.query(uri, projection, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                path = it.getString(it.getColumnIndexOrThrow(MediaStore.Video.Media.DATA))
            }
        }
        return path
    }


    private fun getInputStreamFromUri(context: Context, uri: Uri): InputStream? {
        return try {
            context.contentResolver.openInputStream(uri)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun uploadMovieFromStream(context: Context) {
        Log.d("MainActivity", "uploadMovieFromStream called!")
        val inputStream = getInputStreamFromUri(context, _movieEntryState.value.fileUri) ?: return

        val requestBody = object : RequestBody() {
            override fun contentType() = "video/mp4".toMediaTypeOrNull()

            override fun writeTo(sink: okio.BufferedSink) {
                // Do not use `inputStream.use` here to prevent premature closing
                inputStream.copyTo(sink.outputStream())
            }
        }

        val multipartBody = MultipartBody.Part.createFormData("movie", _movieEntryState.value.movieEntry.title, requestBody)

        val call = CMSApi.retrofitService.uploadMovie(multipartBody)

        call.enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onResponse(
                call: retrofit2.Call<ResponseBody>,
                response: retrofit2.Response<ResponseBody>
            ) {
                if (response.isSuccessful) {
                    Log.d("MainActivity", "Upload successful: ${response.body()?.string()}")
                } else {
                    Log.e("MainActivity", "Upload failed: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<ResponseBody>, t: Throwable) {
                Log.e("MainActivity", "Error uploading video", t)
            }
        })
    }

}