package com.example.eventfinder.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventsSearchPage(navController: NavController) {
    var selectedEventType by remember { mutableStateOf("All") }
    var selectedLocation : String = ""
    var radius by remember { mutableStateOf(10.0) }
    var useDeviceLocation by remember { mutableStateOf(true) }
    var searchResults by remember { mutableStateOf(listOf("Event 1", "Event 2", "Event 3", "Event 4")) }

    Column(modifier = Modifier.fillMaxSize()) {
        // Top Section with search elements
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp) // Adjusted height for additional space
                .background(Color.LightGray)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Title for search section
                Text(
                    text = "Search for events",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Dropdown list for event types
                    OutlinedTextField(
                        value = selectedEventType,
                        onValueChange = { selectedEventType = it },
                        label = { Text("Event Type") },
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    // Slider for search radius
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Search Radius: ${radius.toInt()} km")
                        Slider(
                            value = radius.toFloat(),
                            onValueChange = { radius = it.toDouble() },
                            valueRange = 50.0f..200.0f,
                            steps = 150,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Slide button to choose location source
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Use Device Location")
                        Switch(
                            checked = useDeviceLocation,
                            onCheckedChange = {
                                useDeviceLocation = it
                            }
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    // Dropdown list for location
                    OutlinedTextField(
                        value = selectedLocation,
                        onValueChange = { selectedLocation = it },
                        label = { Text("Location") },
                        enabled = !useDeviceLocation, // Enable only if not using device location
                        modifier = Modifier.weight(1f)
                    )

                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically){
                    Button(
                        onClick = { performSearch(selectedEventType, radius, useDeviceLocation) }
                    ) {
                        Icon(Icons.Filled.Search, contentDescription = "Search")
                    }
                }
            }
        }

        // Bottom Section to display search results
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(searchResults) { event ->
                EventCard(event = event)
            }
        }
    }
}

@Composable
fun EventCard(event: String) {
    Text(text = event, fontSize = 16.sp, modifier = Modifier.padding(16.dp))
}

fun performSearch(eventType: String, radius: Double, useDeviceLocation: Boolean) {
    // Logic for performing the event search based on provided parameters
    // Update the searchResults with the retrieved events
}


