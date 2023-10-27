package com.example.eventfinder.ui.screens

import androidx.compose.foundation.layout.Arrangement
import android.location.Location
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.eventfinder.MyApplication
import com.example.eventfinder.location.LocationService


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
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Menu")
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

