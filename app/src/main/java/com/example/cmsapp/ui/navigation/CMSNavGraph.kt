package com.example.cmsapp.ui.navigation

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
import com.example.cmsapp.ui.main.MainScreen

enum class CMSDestinations(val title: String) {
    Login(title = "login"),
    Register(title = "register"),
    Main(title = "main")
}

@Composable
fun CMSNavGraph(navController: NavHostController,
                modifier: Modifier = Modifier) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = CMSDestinations.valueOf(
        backStackEntry?.destination?.route ?: CMSDestinations.Main.name
    )
    NavHost(
        navController = navController,
        startDestination = CMSDestinations.Login.name,
        modifier = modifier
    ){
        //consider login and register same destination, leave internal logic to viewmodel??
        composable(route = CMSDestinations.Login.name) {
            AuthBaseScreen(
                onSwitch = {
                    navController.navigate(CMSDestinations.Register.name)
                },
                onSubmitClick = {navController.navigate(CMSDestinations.Main.name)},
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
        }
        composable(route = CMSDestinations.Register.name) {
            AuthBaseScreen(
                onSwitch = { navController.navigate(CMSDestinations.Login.name) },
                onSubmitClick = {navController.navigate(CMSDestinations.Main.name)},
                modifier = Modifier.fillMaxSize().padding(16.dp))
        }
        composable(route = CMSDestinations.Main.name){
            MainScreen()
        }
    }
}
