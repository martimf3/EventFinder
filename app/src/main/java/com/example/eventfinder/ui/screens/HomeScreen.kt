package com.example.eventfinder.ui.screens

import androidx.compose.foundation.layout.Arrangement
import android.location.Location
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.eventfinder.ui.theme.WhiteLigth


@Composable

fun HomeScreen(
    navController: NavController
) {



    }


























    /*
        Button(onClick = {
            locationService.getLastLocation(object : LocationService.LocationCallback {
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
            })
                }) {
                    Text(text = "Search Events")
                }
*/







