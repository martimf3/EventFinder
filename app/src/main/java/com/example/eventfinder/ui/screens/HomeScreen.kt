package com.example.eventfinder.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable

fun HomeScreen(
    navController: NavController
) {

    Column(modifier = Modifier
        .padding(50.dp),

    ) {
        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceEvenly

        ) {
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Menu")

            }
        }
        Column {
            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxSize(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    navController.navigate("profile")
                }) {
                    Text(text = "Profile")
                }

                Button(onClick = {
                    /*locationService.getLastLocation(object : LocationService.LocationCallback {
                        override fun onLocationResult(location: Location?) {
                            if (location != null) {
                                // Handle the retrieved location her
                                GetEvents(location, 100) { eventData ->
                                    if (eventData != null) {
                                        print(eventData.count())
                                    } else {
                                        // Handle the case where event data retrieval fails
                                        println("Error retrieving events list")
                                    }
                                }
                            } else {
                                println("Localização não disponivel")
                            }
                        }
                        override fun onError(errorMessage: String) {
                            println("Erro ao ir buscar a localização atual")
                        }
                    })*/
                }) {
                    Text(text = "Search Events")
                }

                Button(onClick = {
                    // TODO: Implement WishList functionality
                }) {
                    Text(text = "WishList")
                }

                Button(onClick = {
                    // TODO: Implement Wallet functionality
                }) {
                    Text(text = "Wallet")
                }
            }

            Button(onClick = { navController.navigate("location") }) {
                Text(text = "Location ")
            }

            Button(onClick = { /*TODO*/ }) {
                Text(text = "WishList")

            }

            Button(onClick = { /*TODO*/ }) {
                Text(text = "Wallet")

            }


        }

    }

}

