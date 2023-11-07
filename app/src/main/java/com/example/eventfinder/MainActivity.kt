package com.example.eventfinder

import android.os.Bundle
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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eventfinder.auth.googleauth.sign_in.GoogleAuthUiClient
import com.example.eventfinder.auth.googleauth.sign_in.SignInViewModel
import com.example.eventfinder.ui.screens.ProfileScreen
import com.example.eventfinder.ui.screens.SignInScreen
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch
import com.example.eventfinder.data.api.ticketMaster.*
import com.example.eventfinder.data.models.EventData
import com.example.eventfinder.ui.screens.EventWalletPage
import com.example.eventfinder.ui.screens.EventsSearchPage
import com.example.eventfinder.ui.screens.SignUpScreen
import com.example.eventfinder.ui.screens.UpdateProfile
import com.example.eventfinder.ui.theme.BlueLitgh
import com.example.eventfinder.ui.theme.WhiteLigth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


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
    val context = this
    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        val context = MyApplication.applicationContext()
        val eventViewModel = ViewModelProvider(this).get(EventViewModel::class.java)

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
                    title = "Events",
                    route = "eventSearch",
                    selectedIcon = Icons.Filled.Search,
                    unselectedIcon = Icons.Outlined.Search,
                    hasNews = false,
                ),
                BottomNavigationItem(
                    title = "WishList",
                    route = "wishList",
                    selectedIcon = Icons.Filled.Favorite,
                    unselectedIcon = Icons.Outlined.FavoriteBorder,
                    hasNews = false,
                    badgeCount = 2,
                ),
                BottomNavigationItem(
                    title = "Profile",
                    route = "Profile",
                    selectedIcon = Icons.Filled.Person,
                    unselectedIcon = Icons.Outlined.Person,
                    hasNews = false,
                ),
                )

            if (isLoggedIn) {
                Scaffold(
                    bottomBar = {

                        NavigationBar(
                            containerColor =  WhiteLigth,
                            contentColor = BlueLitgh,


                        ) {
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
                        navController.navigate("eventSearch")
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
                            navController.navigate("eventSearch")

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
                    val db = FirebaseFirestore.getInstance()

                    googleAuthUiClient.getSignedInUser()?.let { it1 ->
                        ProfileScreen(
                            navController,
                            userData = it1,
                            db,
                            onSignOut = {
                                lifecycleScope.launch {
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
                }

                //Update Profile
                composable("update"){
                    val db = FirebaseFirestore.getInstance()
                    val userData = googleAuthUiClient.getSignedInUser()

                    val onProfileUpdated: (Boolean) -> Unit = { result->
                        if (result){
                            Toast.makeText(applicationContext, "Profile Successfuly Updated", Toast.LENGTH_LONG).show()
                        }else{
                            Toast.makeText(applicationContext,"Error Updating Profile", Toast.LENGTH_LONG).show()
                        }
                    }


                    if (userData != null) {
                        UpdateProfile(navController, db, userData , onProfileUpdated  )
                    }
                }
                // Teste Navegacao da Aplicacao
                composable("location") {

                }

                // Teste Navegacao da Aplicacao
                composable("eventSearch") {
                    EventsSearchPage(navController, context)
                }

                composable("wishList") {
                    LaunchedEffect(key1 = true) {
                        eventViewModel.fetchEvents(googleAuthUiClient)
                    }

                    val eventList by eventViewModel.events.collectAsState(initial = emptyList())
                    println("aqui: ${eventList.count()}")
                    EventWalletPage(navController, context,eventList)

                }

                // Sing Up With Email
                composable("sign_up")
                {
                    SignUpScreen(navController)
                }

            }


        }
    }
}


class EventViewModel : ViewModel() {
    private val _events = MutableStateFlow<List<EventData>>(emptyList())
    val events: StateFlow<List<EventData>> = _events

    suspend fun fetchEvents(googleAuthUiClient: GoogleAuthUiClient) {
        withContext(Dispatchers.IO) {
            val userId = googleAuthUiClient.getSignedInUser()?.userId.toString()
            val existingDoc = Firebase.firestore.collection("users").document(userId).collection("wishlist").get().await()
            val eventsList = existingDoc.toObjects(EventData::class.java)
            _events.value = eventsList
        }
    }
}





