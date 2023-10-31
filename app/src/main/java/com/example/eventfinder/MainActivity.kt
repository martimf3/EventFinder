package com.example.eventfinder

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch
import com.example.eventfinder.data.api.ticketMaster.*
import com.example.eventfinder.ui.screens.EventListScreen
import com.example.eventfinder.ui.screens.SignUpScreen

data class BottomNavigationItem(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)

@OptIn(ExperimentalMaterial3Api::class)
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

            // VARIAVEIS
            val navController = rememberNavController()
            var selectedItemIndex by rememberSaveable {
                mutableStateOf(0)
            }
            var isLoggedIn by rememberSaveable {
                mutableStateOf(false)
            }

            val items = listOf(
                BottomNavigationItem(
                    title = "Home",
                    route = "home",
                    selectedIcon = Icons.Filled.Home,
                    unselectedIcon = Icons.Outlined.Home,
                    hasNews = false,
                ),
                BottomNavigationItem(
                    title = "Location",
                    route = "location",
                    selectedIcon = Icons.Filled.LocationOn,
                    unselectedIcon = Icons.Outlined.LocationOn,
                    hasNews = false,
                ),
                BottomNavigationItem(
                    title = "WishList",
                    route = "wishList",
                    selectedIcon = Icons.Filled.Favorite,
                    unselectedIcon = Icons.Outlined.FavoriteBorder,
                    hasNews = false,
                    badgeCount = 3,
                ),
                BottomNavigationItem(
                    title = "Settings",
                    route = "Profile",
                    selectedIcon = Icons.Filled.Settings,
                    unselectedIcon = Icons.Outlined.Settings,
                    hasNews = true,
                ),


                )



            if (isLoggedIn) {
                Scaffold(
                    bottomBar = {

                        NavigationBar() {
                            items.forEachIndexed { index, item ->
                                NavigationBarItem(
                                    selected = selectedItemIndex == index,
                                    onClick = {
                                        selectedItemIndex = index
                                        navController.navigate(item.route)
                                    },
                                    label = {
                                        Text(text = item.title)
                                    },
                                    icon = {
                                        BadgedBox(
                                            badge = {
                                                if (item.badgeCount != null) {

                                                    Badge {
                                                        Text(text = item.badgeCount.toString())
                                                    }
                                                } else if (item.hasNews) {
                                                    Badge()
                                                }

                                            }
                                        ) {
                                            Icon(
                                                imageVector =
                                                if (index == selectedItemIndex) {
                                                    item.selectedIcon
                                                } else item.unselectedIcon,
                                                contentDescription = item.title

                                            )
                                        }
                                    }
                                )
                            }
                        }

                    }

                ) { paddingValues ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .verticalScroll(rememberScrollState())
                    ) {

                    }
                }
            }



            NavHost(navController = navController, startDestination = "distinction") {


                composable("distinction") {

                    if (isLoggedIn) {
                        navController.navigate("home")
                    }

                    if(!isLoggedIn){
                        navController.navigate("sign_in")
                    }

                }

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
                    LaunchedEffect(key1 = state.isSignInSuccessful) {
                        if (state.isSignInSuccessful) {
                            isLoggedIn = true
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
                composable("profile") {
                    val viewModel = viewModel<SignInViewModel>()
                    val state by viewModel.state.collectAsStateWithLifecycle()

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
                                if(!state.isSignInSuccessful){
                                    isLoggedIn = false
                                }
                                navController.navigate("sign_in")

                            }

                        }
                    )
                }
                //HOME NAVBAR -> Should Be Placed a Navbar here, with our homepage, and the navBar on Bottom
                composable("home") {
                    HomeScreen(navController)

                }
                // Teste Navegacao da Aplicacao
                composable("location") {

                }

                composable("wishList") {

                }

                // Sing Up With Email
                composable("sign_up")
                {
                    SignUpScreen(navController)
                }

            }

            MainNavigation()
        }
    }
}



