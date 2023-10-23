package com.example.eventfinder.ui.screens

import android.location.Location
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.eventfinder.MyApplication
import com.example.eventfinder.location.LocationService

@Composable
fun HomeScreen(navController: NavController) {
    val context = MyApplication.applicationContext()
    val locationService = LocationService(context)
    val locationState = remember { mutableStateOf<Location?>(null) }
    val locationText = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        // Your HomeScreen content

        Button(
            onClick = {
                navController.navigate("testepage")
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Go to TestePage")
        }

        Button(
            onClick = {
                locationService.getLastLocation(object : LocationService.LocationCallback {
                    override fun onLocationResult(location: Location?) {
                        if (location != null) {
                            locationState.value = location
                            val latitude = location.latitude
                            val longitude = location.longitude
                            locationText.value = "Latitude: $latitude, Longitude: $longitude"
                        } else {
                            locationText.value = "Location not available"
                        }
                    }

                    override fun onCitiesWithinRadiusResult(cityNames: List<String>) {
                        // Not needed in this case, can be left empty
                    }

                    override fun onError(errorMessage: String) {
                        // Handle errors if necessary
                    }
                })
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Get Current Location")
        }

        Button(
            onClick = {
                if (locationState.value != null) {
                    locationService.getCitiesWithinRadius(
                        locationState.value!!,
                        50.0,
                        object : LocationService.LocationCallback {
                            override fun onLocationResult(location: Location?) {
                                // Not needed in this case, can be left empty
                            }

                            override fun onCitiesWithinRadiusResult(cityNames: List<String>) {
                                if (cityNames.isNotEmpty()) {
                                    locationText.value = "Cities within 100km:\n${cityNames.joinToString("\n")}"
                                } else {
                                    locationText.value = "No cities found within 100km."
                                }
                            }

                            override fun onError(errorMessage: String) {
                                // Handle errors if necessary
                            }
                        }
                    )
                } else {
                    locationText.value = "Location not available. Please get the current location first."
                }
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Get Cities Within 100km")
        }

        // Display the location text
        Text(
            text = locationText.value,
            modifier = Modifier.padding(16.dp)
        )
    }
}
