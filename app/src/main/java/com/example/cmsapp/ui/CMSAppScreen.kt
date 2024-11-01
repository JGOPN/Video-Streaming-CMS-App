package com.example.cmsapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cmsapp.CMSViewModel

import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

enum class CMSAppScreen(val title: String) {
    Login(title = "login"),
    Register(title = "register")
}

@Composable
fun CMSApp(
    viewModel: CMSViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = CMSAppScreen.valueOf(
        backStackEntry?.destination?.route ?: CMSAppScreen.Login.name
    )
    Scaffold { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()
        NavHost(
            navController = navController,
            startDestination = CMSAppScreen.Login.name,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(route = CMSAppScreen.Login.name) {
                LoginRegisterBase(
                    //quantityOptions = DataSource.quantityOptions,
                    onSwitch = {
                        //CMSViewModel.setQuantity(it)
                        navController.navigate(CMSAppScreen.Register.name)
                    },
                    onSubmitClick = {},
                    lorr = true,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }
            composable(route = CMSAppScreen.Register.name) {
                val context = LocalContext.current
                LoginRegisterBase(
                    onSwitch = { navController.navigate(CMSAppScreen.Login.name) },
                    onSubmitClick = {},
                    lorr = false,
                    modifier = Modifier.fillMaxSize().padding(16.dp))
            }
        }
    }
}
