package com.example.cmsapp.ui.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells.*
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cmsapp.ui.components.MinimalDialog
import com.example.cmsapp.data.Datasource
import com.example.cmsapp.ui.theme.CMSappTheme

@Composable
fun AddMovieScreen(movieEntryViewModel: MovieEntryViewModel = viewModel()){
    val movieEntryState: MovieEntryState by movieEntryViewModel.movieEntryState.collectAsState()
    val movieEntry = movieEntryState.movieEntry

    val validateInputs = movieEntryViewModel::validateMovieEntry
    val onUpdate = movieEntryViewModel::updateMovieEntryState
    val onSubmit: () -> Unit = {}

    if(movieEntryState.isDialogOpen){
        val errorList = validateInputs()
        if(errorList.isNotEmpty())
            MinimalDialog(
                messageList = validateInputs(),
                onDismissRequest = {movieEntryViewModel.toggleConfirmationDialog()}
            )
        else
            onSubmit()
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
        Row(modifier = Modifier.fillMaxWidth().padding(top = 10.dp), horizontalArrangement = Arrangement.Center) {
            TextField(
                value = movieEntry.releaseYear.toString(),
                onValueChange = { onUpdate(movieEntry.copy(releaseYear = it.toIntOrNull() ?: 0 )) },//if parse error, sets to 0
                label = { Text("Release Year") },
                modifier = Modifier.weight(1f).padding(end = 8.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            TextField(
                value = movieEntry.duration.toString(),
                onValueChange = { onUpdate(movieEntry.copy(duration = it.toIntOrNull() ?: 0)) },
                label = { Text("Duration (minutes)") },
                modifier = Modifier.weight(1f).padding(start = 8.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }

        GenreSelection(onSelectionChange = {onUpdate(movieEntry.copy(genres = it))})
        //TODO: Genres
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                movieEntryViewModel.toggleConfirmationDialog()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Submit")
        }
    }
}

@Composable
fun GenreSelection(genres: List<String> = Datasource.genres, onSelectionChange: (List<String>) -> Unit) {
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
                    .fillMaxWidth().height(30.dp)
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

@Preview(showBackground = true)
@Composable
fun GenrePreview() {
    CMSappTheme{
        GenreSelection(onSelectionChange = {})
    }
}