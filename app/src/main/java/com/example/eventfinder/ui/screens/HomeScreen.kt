package com.example.eventfinder.ui.screens

import androidx.compose.foundation.layout.Arrangement
import android.location.Location
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.eventfinder.MyApplication
import com.example.eventfinder.location.LocationService

@Composable

fun HomeScreen(
    navController: NavController
) {

fun HomeScreen(navController: NavController) {
    val context = MyApplication.applicationContext()
    val locationService = LocationService(context)
    val locationState = remember { mutableStateOf<Location?>(null) }
    val locationText = remember { mutableStateOf("") }


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )


    {
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("profile") }) {
            Text(text = "Profile")

        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("location") }) {
            Text(text = "Location ")

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
                locationService.getLastLocation { location ->
                    if (location != null) {
                        locationState.value = location
                        val latitude = location.latitude
                        val longitude = location.longitude
                        locationText.value = "Latitude: $latitude, Longitude: $longitude"
                    } else {
                        locationText.value = "Location not available"
                    }
                }
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Get Current Location")
        }

        Button(
            onClick = {
                locationService.getCitiesWithinRadius(locationState.value, 100.0) { cities ->
                    if (cities.isNotEmpty()) {
                        locationText.value = "Cities within 100km:\n${cities.joinToString("\n")}"
                    } else {
                        locationText.value = "No cities found within 100km."
                    }
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
