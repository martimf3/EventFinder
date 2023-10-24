package com.example.eventfinder

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eventfinder.auth.googleauth.sign_in.GoogleAuthUiClient
import com.example.eventfinder.auth.googleauth.sign_in.SignInViewModel
import com.example.eventfinder.navigation.MainNavigation
import com.example.eventfinder.ui.screens.HomeScreen
import com.example.eventfinder.ui.screens.ProfileScreen
import com.example.eventfinder.ui.screens.SignInScreen
import com.example.eventfinder.ui.screens.TestePage
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch
import com.example.eventfinder.navigation.MainNavigation

class MainActivity : ComponentActivity() {
    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        val context = MyApplication.applicationContext()
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "sign_in" ){

                // SIGN IN NAVBAR

                composable("sign_in") {

                    val viewModel = viewModel<SignInViewModel>()
                    val state by viewModel.state.collectAsStateWithLifecycle()
                    val launcher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.StartIntentSenderForResult(),
                        onResult = { result ->
                            if (result.resultCode == ComponentActivity.RESULT_OK) {
                                lifecycleScope.launch {
                                    val signInResult = googleAuthUiClient.signInWithIntent(
                                        intent = result.data ?: return@launch
                                    )
                                    viewModel.onSignInResult(signInResult)
                                }
                            }
                        }

                    )
                    LaunchedEffect(key1 = state.isSignInSuccessful ){
                        if (state.isSignInSuccessful){
                            Toast.makeText(
                                applicationContext,
                                "Sign In Successful",
                                Toast.LENGTH_LONG
                            ).show()
                            navController.navigate("home")

                        }
                    }
                    SignInScreen(
                        navController,
                        state = state,
                        onSignInClick = {
                            lifecycleScope.launch {
                                val signInIntentSender = googleAuthUiClient.signIn()
                                launcher.launch(
                                    IntentSenderRequest.Builder(
                                        signInIntentSender ?: return@launch
                                    ).build()
                                )
                            }
                        }
                    )


                }

                //SIGN OUT NAVBAR
                composable("profile"){
                    ProfileScreen(
                        navController,
                        userData = googleAuthUiClient.getSignedInUser(),
                        onSignOut = {
                            lifecycleScope.launch {
                                Log.d("app", "Click")
                                googleAuthUiClient.signOut()
                                Toast.makeText(
                                    applicationContext,
                                    "Signed Out",
                                    Toast.LENGTH_LONG
                                    ).show()
                                navController.navigate("sign_in")

                           }

                        }
                   )

                }

                //HOME NAVBAR -> Should Be Placed a Navbar here, with our homepage, and the navBar on Bottom
                composable("home"){
                  HomeScreen(navController)


                }
                // Teste Navegacao da Aplicacao
                composable("location"){
                    TestePage("Random",navController)
                }

                // Sing Up With Email
                composable("sign_up")
                {

                }
            }
            MainNavigation()
        }
    }
}

