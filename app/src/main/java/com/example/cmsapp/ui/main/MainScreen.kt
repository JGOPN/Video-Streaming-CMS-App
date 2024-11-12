package com.example.cmsapp.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cmsapp.R
import com.example.cmsapp.data.Datasource
import com.example.cmsapp.model.User
import com.example.cmsapp.ui.theme.CMSappTheme

@Composable
fun MainScreen(users: List<User> = Datasource.users){
    Scaffold(
        bottomBar = { CMSBottomAppBar() },
    ) { innerPadding ->
       LazyColumn(modifier = Modifier.padding(innerPadding)) {
           items(users) { item ->
               Row {
                   Text(text=item.name)
                   Text(text=item.email)
               }
           }
       }
    }
}

@Composable
fun CMSBottomAppBar(){
    BottomAppBar() {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ){
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    painter = painterResource(R.drawable.movie),
                    contentDescription = "Manage movies",
                )
            }
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    painter = painterResource(R.drawable.manage_accounts),
                    contentDescription = "Manage users",
                )
            }
            FloatingActionButton(
                onClick = { /* do something */ },
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