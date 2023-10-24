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
        }


    }
}
