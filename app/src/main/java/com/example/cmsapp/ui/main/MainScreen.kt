package com.example.cmsapp.ui.main

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cmsapp.R
import com.example.cmsapp.data.Datasource
import com.example.cmsapp.model.Movie
import com.example.cmsapp.model.User
import com.example.cmsapp.ui.components.ConfirmationDialog
import com.example.cmsapp.ui.theme.CMSappTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(mainViewModel: MainViewModel = viewModel()) {
    val mainUiState by mainViewModel.mainUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Log.d("MainActivity","MainScreen recompose triggered. Current screen: ${mainUiState.currentScreen}")

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = mainUiState.currentScreen.title, textAlign = TextAlign.Center) },
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            CMSBottomAppBar(
                mainUiState,
                onClickAppbarIcon = mainViewModel::setCurrentScreen,
            )
        },
    ) { innerPadding ->

        when (mainUiState.currentScreen) {
            MainScreens.UserList -> {
                if (mainUiState.userList.isEmpty()) {
                    println("Showing error")
                    ShowError("users")
                } else {
                    println("Showing list")
                    LazyCardList(
                        innerPadding = innerPadding,
                        mainUiState = mainUiState,
                        dialogState = mainViewModel.dialogState.value,
                        selectedUserId = mainViewModel.selectedUserId.value,
                        onClickCard = mainViewModel::toggleCardExpansion,
                        hideDialog = mainViewModel::hideDialog,
                        showDialog = mainViewModel::showDialog,
                        confirmDelete = mainViewModel::confirmDelete,
                        deleteItem = mainViewModel::deleteUser,
                    )
                }
            }

            MainScreens.MovieList -> {
                if (mainUiState.movieList.isEmpty()) {
                    ShowError()
                } else {
                    LazyCardList(
                        innerPadding = innerPadding,
                        mainUiState = mainUiState,
                        dialogState = mainViewModel.dialogState.value,
                        selectedUserId = mainViewModel.selectedUserId.value,
                        onClickCard = mainViewModel::toggleCardExpansion,
                        hideDialog = mainViewModel::hideDialog,
                        showDialog = mainViewModel::showDialog,
                        confirmDelete = mainViewModel::confirmDelete,
                        deleteItem = mainViewModel::deleteMovie,
                    )
                }
            }

            MainScreens.AddUser -> AddUserScreen()
            MainScreens.AddMovie -> AddMovieScreen()
        }
    }
}


@Composable
fun LazyCardList(
    innerPadding: PaddingValues,
    mainUiState: MainUiState,
    dialogState: Boolean,
    selectedUserId: Int?,
    onClickCard: (Int) -> Unit,
    hideDialog: () -> Unit,
    showDialog: (Int) -> Unit,
    confirmDelete: ((Int) -> Unit) -> Unit,
    deleteItem: (Int) -> Unit,
){

    ConfirmationDialog(//confirmation dialog for deleting users/movies. Opens when isVisible=true.
        selectedItem =
            if  (mainUiState.currentScreen==MainScreens.UserList) mainUiState.userList.find { it.id == selectedUserId }?.username //gets username/movietitle from list or null
            else mainUiState.movieList.find { it.id == selectedUserId }?.title,
        isVisible = dialogState,
        onDismissRequest = hideDialog,
        onAcceptRequest = {  confirmDelete(deleteItem) }
    )

    LazyColumn(modifier = Modifier
        .padding(innerPadding)
        .fillMaxSize()) {
        if(mainUiState.currentScreen==MainScreens.UserList){
            val users = mainUiState.userList
            items(
                items = users,
                key = {
                        item -> item.id
                }
            ) { item ->
                UserCard(
                    user = item,
                    isExpanded = mainUiState.expandedCardId == item.id,
                    onClickCard = {onClickCard(item.id)},
                    onShowDialog = showDialog,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
        else{
            val movies = mainUiState.movieList
            items(
                items = movies,
                key = {
                        item -> item.id
                }
            ) { item ->
                MovieCard(
                    movie = item,
                    isExpanded = mainUiState.expandedCardId == item.id,
                    onClickCard = {onClickCard(item.id)},
                    onShowDialog = showDialog,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}

@Composable
fun UserCard(
    user: User,
    isExpanded: Boolean = false,
    onClickCard: (Int) -> Unit,             // expand card
    onShowDialog: (Int) -> Unit,            //on delete icon, show confirmation dialog
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier.fillMaxWidth(), onClick = {onClickCard(user.id)}) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .fillMaxWidth()
                .padding(6.dp, 6.dp, 6.dp, 0.dp)
        ) {
            Row{
                //use two rows to align the last icon to the right, suggested by chat.
                Column(modifier = Modifier.width(30.dp)) {
                    Text(
                        text = "${user.id}",
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
                Column {
                    Text(
                        text = user.username,
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 2
                    )
                }
            }
            Column {
                IconButton(onClick = {onShowDialog(user.id)}) {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = "delete user"
                    )
                }
            }
        }
        if(isExpanded) Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .fillMaxWidth()
                .padding(6.dp, 0.dp, 6.dp, 6.dp)
        ){
            Column {
                Text(
                    text = if(user.isAdmin) "Admin" else "User",
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            Column {
                Text(
                    text = "E-mail: ${user.email}",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                )
                Text(
                    text = "Birthdate: ${user.birthdate}",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@Composable
fun MovieCard(
    movie: Movie,
    isExpanded: Boolean = false,
    onClickCard: (Int) -> Unit,             // expand card
    onShowDialog: (Int) -> Unit,            //on delete icon, show confirmation dialog
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier.fillMaxWidth(), onClick = {onClickCard(movie.id)}) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .fillMaxWidth()
                .padding(6.dp)
        ) {
            Row{
                //use two rows to align the last icon to the right, suggested by chat.
                Column(modifier = Modifier.width(30.dp)) {
                    Text(
                        text = "${movie.id}",
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
                Column {
                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.titleLarge,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        modifier = Modifier.width(275.dp)
                    )
                }
            }
            Column {
                IconButton(onClick = {onShowDialog(movie.id)}) {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = "delete movie"
                    )
                }
            }
        }
        if(isExpanded) Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .fillMaxWidth()
                .padding(6.dp, 0.dp, 6.dp, 6.dp)
        ){
            Column(Modifier.weight(2f)) {
                Text(
                    text = "Release year: ${movie.releaseYear}",
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = "Duration: ${movie.duration} minutes",
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = "Description: ${movie.description}",
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3,
                )

            }
            Column(Modifier.weight(1f)) {
                Text(
                    text = "Genre(s): ${movie.genres.joinToString(", ")}",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = "Submitted by: User (id=${movie.submittedBy})",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@Composable
//onClickAppbarIcon receives a MainScreens and updates viewModel to go to that screen (doesnt use navigation)
fun CMSBottomAppBar(mainUiState: MainUiState, onClickAppbarIcon : (MainScreens) -> Unit){
    val currentScreen = mainUiState.currentScreen
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier.height(120.dp)
    ) {
        val iconModifier = Modifier
            .padding(0.dp)
            .clip(RoundedCornerShape(10.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ){
            IconButton(
                onClick = {onClickAppbarIcon(MainScreens.MovieList)},
                modifier = iconModifier
            ) {
                Icon(
                    painter = painterResource(R.drawable.movie),
                    contentDescription = "Manage movies",
                    tint = if (currentScreen==MainScreens.MovieList) Color.Black else Color.Unspecified
                )
            }
            IconButton(
                onClick = {onClickAppbarIcon(MainScreens.UserList)},
                modifier = iconModifier
            ) {
                Icon(
                    painter = painterResource(R.drawable.manage_accounts),
                    contentDescription = "Manage users",
                    tint = if (currentScreen==MainScreens.UserList) Color.Black else Color.Unspecified
                )
            }

            FloatingActionButton(
                onClick = { onClickAppbarIcon(MainScreens.AddUser) },
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally){
                    Icon(Icons.Filled.Add, "User", tint = if(currentScreen==MainScreens.AddUser) Color.Black else LocalContentColor.current)
                    Text("User")
                    /*color = if(mainUiState.currentScreen==MainScreens.AddUser) Color.Black else LocalContentColor.current
                    * this doesnt work for some reason.*/
                }
            }

            FloatingActionButton(
                onClick = { onClickAppbarIcon(MainScreens.AddMovie) },
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally){
                    Icon(Icons.Filled.Add, "Add a movie", tint = if(currentScreen==MainScreens.AddMovie) Color.Black else LocalContentColor.current)
                    Text("Movie")
                }
            }
        }
    }
}

@Composable
fun ShowLoading() {
        Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(R.drawable.loading_img), contentDescription = "loading", modifier = Modifier.size(300.dp))
        }
}

@Composable
fun ShowError(str: String = "movies") {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
        Icon(
            Icons.Rounded.Warning,
            contentDescription = "warning",
            tint = Color.Red
        )
        Text(text = "Failed to fetch $str. Server is offline or you're not connected to the internet", textAlign = TextAlign.Center)
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    CMSappTheme{
        //MainScreen()
        MovieCard(isExpanded = true,movie = Datasource.movies[8], onClickCard = {}, onShowDialog = {})
    }
}