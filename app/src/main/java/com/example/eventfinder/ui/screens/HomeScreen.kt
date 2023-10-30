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

