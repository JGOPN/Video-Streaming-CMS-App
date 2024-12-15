package com.example.cmsapp.ui.main

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells.Fixed
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cmsapp.data.Datasource
import com.example.cmsapp.ui.components.MinimalDialog
import com.example.cmsapp.ui.components.VideoPicker
import com.example.cmsapp.ui.theme.CMSappTheme

@Composable
fun AddMovieScreen(movieEntryViewModel: MovieEntryViewModel = viewModel()){
    val movieEntryState: MovieEntryState by movieEntryViewModel.movieEntryState.collectAsState()
    val movieEntry = movieEntryState.movieEntry
    val addSuccessful = remember {  mutableStateOf<Boolean?>(null) } //if true, shows "user movie" confirmation.

    val validateInputs = movieEntryViewModel::validateMovieEntry
    val onUpdate = movieEntryViewModel::updateMovieEntryState
    val onSubmit: ((Boolean) -> Unit) -> Unit = movieEntryViewModel::addMovie
    val context = LocalContext.current

    if(movieEntryState.isDialogOpen){
        val errorList = validateInputs()
        if(errorList.isNotEmpty())
            MinimalDialog(
                messageList = errorList,
                onDismissRequest = {movieEntryViewModel.toggleConfirmationDialog()}
            )
        else{
            movieEntryViewModel.toggleConfirmationDialog()
            onSubmit() { isSuccess ->
                addSuccessful.value = isSuccess
                if(isSuccess && !movieEntryState.urlFieldEnabled){ //if submitted successfully & user selected local video, upload it using stream from uri
                    movieEntryViewModel.uploadMovieFromStream(context)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = movieEntry.title,
            onValueChange = {  onUpdate(movieEntry.copy(title = it)) },
            label = { Text(text="Title") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = movieEntry.description,
            onValueChange = { onUpdate(movieEntry.copy(description = it)) },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 7
        )
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp), horizontalArrangement = Arrangement.Center) {
            TextField(
                value = movieEntry.releaseYear.toString(),
                onValueChange = { onUpdate(movieEntry.copy(releaseYear = it.toIntOrNull() ?: 0 )) },//if parse error, sets to 0
                label = { Text("Release Year") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            )
            TextField(
                value = movieEntry.duration.toString(),
                onValueChange = { onUpdate(movieEntry.copy(duration = it.toIntOrNull() ?: 0)) },
                label = { Text("Duration (minutes)") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            )
        }

        GenreSelection(onSelectionChange = {onUpdate(movieEntry.copy(genres = it))})

        Spacer(modifier = Modifier.height(4.dp))

        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            val hasPermission = remember {  mutableStateOf(true) } //if user denies pemission, set to false and shows text to user
            RequestVideoPermission { hasPermission.value = it }
            VideoPicker(onMovieSelect = {
                movieEntryViewModel.toggleUrlField(false) //disable textField if file is selected
                movieEntryViewModel.updateMovieUri(it) //sets the uri in movieEntryState (we use it to get stream later)
            }, onCancel = {
                movieEntryViewModel.toggleUrlField(true)
            })

            if(!hasPermission.value)  Text(text = "File access permission denied.",textAlign = TextAlign.Center, color = Color.Red)

            if(movieEntryState.urlFieldEnabled)
                Text(text = "OR",textAlign = TextAlign.Center)
            else
                Text(text = "File picked: ${movieEntryViewModel.getFilePathFromUri(context, movieEntryState.fileUri)?.substringAfterLast("/")}"
                    ,textAlign = TextAlign.Center, color = Color.Green)

            TextField(
                value = movieEntryState.movieUrl,
                onValueChange = {  movieEntryViewModel.updateMovieURL(url = it) },
                label = { Text(text="Enter Movie URL") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = movieEntryState.urlFieldEnabled
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Button(
            //if user wrote url in url bar, set the movieEntryState.movieEntry.movieUrl to it.
            onClick = {
                if(movieEntryState.movieUrl.isNotBlank() && movieEntryState.urlFieldEnabled) movieEntryViewModel.updateMovieEntryState(movieEntry.copy(movieUrl = movieEntryState.movieUrl))
                movieEntryViewModel.toggleConfirmationDialog()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Submit")
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(3.dp), horizontalArrangement = Arrangement.Center){
            when (addSuccessful.value) {
                true -> Text(text = "Movie added successfully", textAlign = TextAlign.Center, color = Color.Green)
                false -> Text(text = "Error adding movie", textAlign = TextAlign.Center, color = Color.Red)
                null -> {}
            }
        }
    }
}

@Composable
fun GenreSelection(genres: List<String> = Datasource.genres, onSelectionChange: (List<String>) -> Unit) {
//fun GenreSelection(genres: List<String>, onSelectionChange: (List<String>) -> Unit) {
    // State to manage selected genres
    val selectedGenres = remember { mutableStateMapOf<String, Boolean>() }

    // Initialize all genres as unselected
    genres.forEach { genre ->
        if (selectedGenres[genre] == null) {
            selectedGenres[genre] = false
        }
    }
    LazyVerticalGrid(
        columns = Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )  {
        items(genres) { genre ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
            ) {
                Checkbox(
                    checked = selectedGenres[genre] == true,
                    onCheckedChange = { isChecked ->
                        selectedGenres[genre] = isChecked
                        // Notify the parent composable about the selection change
                        onSelectionChange(selectedGenres.filterValues { it }.keys.toList())
                    }
                )
                Text(
                    text = genre,
                    modifier = Modifier.padding(start = 2.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
fun RequestVideoPermission(onPermissionResult: (Boolean) -> Unit) {
    val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_VIDEO
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    // Register the launcher in a lifecycle-aware manner
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = onPermissionResult
    )

    LaunchedEffect(Unit) {
        // Launch permission request when the composable enters composition
        permissionLauncher.launch(permission)
    }
}


@Preview(showBackground = true)
@Composable
fun GenrePreview() {
    CMSappTheme{
        AddMovieScreen()
    }
}