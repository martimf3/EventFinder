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
import androidx.compose.ui.Modifier
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
        }
    }
}


