package com.example.cmsapp.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cmsapp.ui.auth.AuthBaseScreen
import com.example.cmsapp.ui.main.AddUserScreen
import com.example.cmsapp.ui.main.MainScreen

enum class CMSDestinations(val title: String) {
    Login(title = "login"),
    Main(title = "main"),
}

@Composable
fun CMSNavGraph(navController: NavHostController,
                modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = CMSDestinations.Login.name,
        modifier = modifier
    ){
        //consider login and register same destination, leave internal logic to viewmodel
        composable(route = CMSDestinations.Login.name) {
            AuthBaseScreen(
                onLogin = { navController.navigate(CMSDestinations.Main.name) },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
        }
        composable(route = CMSDestinations.Main.name){
            MainScreen()
            BackHandler {
                // Do nothing when the back button is pressed on Main screen
            }
        }
    }
}
