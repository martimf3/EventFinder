package com.example.eventfinder.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        // Your HomeScreen content

        Button(
            onClick = {
                navController.navigate("testepage")
            },
            modifier = Modifier
                .padding(16.dp) // Add padding to the button
                .fillMaxWidth() // Make the button take up the full width of its parent
        ) {
            Text(text = "Go to TestePage")
        }
    }
}

