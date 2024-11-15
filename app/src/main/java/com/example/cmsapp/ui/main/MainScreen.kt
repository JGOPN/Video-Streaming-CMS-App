package com.example.cmsapp.ui.main

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cmsapp.R
import com.example.cmsapp.data.Datasource
import com.example.cmsapp.model.Movie
import com.example.cmsapp.model.User
import com.example.cmsapp.ui.theme.CMSappTheme


@Composable
fun MainScreen(mainViewModel: MainViewModel = viewModel()){
    Log.d("MAIN","MainScreen recompose triggered")
    val mainUiState by mainViewModel.mainUiState.collectAsState()
    val userEntryState by mainViewModel.userEntryState.collectAsState()
    val isDisplayingUsers = mainUiState.isDisplayingUsers
    Scaffold(
        bottomBar = { CMSBottomAppBar(
            mainUiState,
            onClickAppbarIcon = mainViewModel::setIsDisplayingUsers,
            onClickAppbarAddIcon = mainViewModel::setCurrentScreen
        ) },
    ) { innerPadding ->
        when(mainUiState.currentScreen) {
            MainScreens.UserList, MainScreens.MovieList ->{
                LazyCardList(
                    isDisplayingUsers,
                    mainUiState.expandedCardId,
                    onClickCard = mainViewModel::toggleCardExpansion,
                    innerPadding = innerPadding
                )
            }
            MainScreens.AddUser, MainScreens.AddMovie -> {
                AddUserScreen(
                    onSubmit = {},
                    //viewModel = mainViewModel,
                    userEntryState = userEntryState,
                    onUpdate = mainViewModel::updateUserEntryState
                )
            }
        }
    }
}

@Composable
fun LazyCardList(
    isDisplayingUsers: Boolean,
    expandedCard: Int,
    onClickCard: (Int) -> Unit,
    users: List<User> = Datasource.users,
    movies: List<Movie> = Datasource.movies,
    innerPadding: PaddingValues,
){
    LazyColumn(modifier = Modifier
        .padding(innerPadding)
        .fillMaxSize()) {
        if(isDisplayingUsers){
            items(
                items = users,
                key = {
                        item -> item.id
                }
            ) { item ->
                UserCard(
                    user = item,
                    isExpanded = expandedCard == item.id,
                    onClick = {onClickCard(item.id)},
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
        else{
            items(
                items = movies,
                key = {
                        item -> item.id
                }
            ) { item ->
                MovieCard(
                    movie = item,
                    isExpanded = expandedCard == item.id,
                    onClick = {onClickCard(item.id)},
                    modifier = Modifier.padding(4.dp)
                )
            }
        }

    }
}

@Composable
fun UserCard(user: User, isExpanded : Boolean = false, onClick : (Int) -> Unit, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth(), onClick = {onClick(user.id)}) {
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
                    )
                }
            }
            Column {
                IconButton(onClick = {}) {
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
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3,
                )
            }
        }
    }
}

@Composable
fun MovieCard(movie: Movie, isExpanded : Boolean = false, onClick : (Int) -> Unit, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth(), onClick = {onClick(movie.id)}) {
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
                IconButton(onClick = {}) {
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
                    text = "${movie.releaseYear}",
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = movie.description,
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3,
                )
            }
        }
    }
}

@Composable
//onClickAppbarIcon refers to selecting videos or users. onClickAppbarAddIcon refers to the add icon.
fun CMSBottomAppBar(mainUiState: MainUiState, onClickAppbarIcon : (Boolean) -> Unit, onClickAppbarAddIcon: (MainScreens) -> Unit){
    val isDisplayingUsers = mainUiState.isDisplayingUsers
    BottomAppBar(containerColor = MaterialTheme.colorScheme.tertiary, modifier = Modifier.height(120.dp)) {
        val iconModifier = Modifier.padding(0.dp).clip(RoundedCornerShape(10.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ){
            IconButton(
                onClick = {onClickAppbarIcon(false)},
                modifier = if (isDisplayingUsers) iconModifier else iconModifier.background(MaterialTheme.colorScheme.secondary)
            ) {
                Icon(
                    painter = painterResource(R.drawable.movie),
                    contentDescription = "Manage movies",
                    tint = if (isDisplayingUsers) Color.Unspecified else Color.Black
                )
            }
            IconButton(
                onClick = {onClickAppbarIcon(true)},
                modifier = if (isDisplayingUsers) iconModifier.background(MaterialTheme.colorScheme.secondary) else iconModifier
            ) {
                Icon(
                    painter = painterResource(R.drawable.manage_accounts),
                    contentDescription = "Manage users",
                    tint = if (isDisplayingUsers) Color.Black else Color.Unspecified
                )
            }
            FloatingActionButton(
                onClick = { onClickAppbarAddIcon(
                    if(mainUiState.currentScreen == MainScreens.UserList)
                        MainScreens.AddUser
                    else MainScreens.AddMovie
                ) },
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(Icons.Filled.Add, "Localized description")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    CMSappTheme{
        MainScreen()
    }
}