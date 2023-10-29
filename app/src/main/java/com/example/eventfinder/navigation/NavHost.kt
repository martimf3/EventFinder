package com.example.eventfinder.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eventfinder.auth.*
import com.example.eventfinder.auth.googleauth.sign_in.SignInState
import com.example.eventfinder.ui.screens.HomeScreen
import com.example.eventfinder.ui.screens.SignInScreen
import com.example.eventfinder.ui.screens.TestePage


@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "signinPage") {
        composable("homePage") {
            HomeScreen(navController)
        }
        composable("testePage") {
            TestePage("parametro random", navController)
        }
        composable("signinPage"){
            SignInScreen(navController = navController, state = SignInState()) {
            }
        }
    }
}



