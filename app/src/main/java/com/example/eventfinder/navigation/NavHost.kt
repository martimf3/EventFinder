package com.example.eventfinder.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eventfinder.ui.screens.HomeScreen
import com.example.eventfinder.ui.screens.TestePage


import com.example.eventfinder.auth.*
@Composable

fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {

        }
        composable("testepage") {
            TestePage("parametro random", navController)
        }
    }
}



