package com.example.cmsapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

import com.example.cmsapp.ui.navigation.CMSNavGraph

@Composable
fun CMSAppScreen(
    navController: NavHostController = rememberNavController()
) {
    CMSNavGraph(navController)
}
