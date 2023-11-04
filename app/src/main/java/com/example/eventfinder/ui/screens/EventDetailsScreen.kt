package com.example.eventfinder.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.eventfinder.data.models.EventData


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailsPage(navController: NavController, event: EventData, onBack: () -> Unit) {
    var selectedImageIndex by remember { mutableStateOf(0) }
    var selectedEvent = event

    println("Event Details Page: ${selectedEvent.toString()}")
    // Function to handle the back navigation
    /*val onBack: () -> Unit = {
        // Update search results in the SearchViewModel when navigating back
        searchViewModel.updateSearchResults(searchViewModel.searchResults.value)
        navController.popBackStack()
    }*/

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = {
                Text(text = selectedEvent?.name ?: "Event Details")
            },
            modifier = Modifier.fillMaxWidth()
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                selectedEvent?.let { event ->
                    item {
                        Text(text = "Event Name: ${event.name}")
                    }
                    item {
                        val startDate = event.dates?.start?.localDate ?: "No available information"
                        Text(text = "Start Date: $startDate")
                    }
                    item {
                        val pricerange = event.pricerange ?: "No available information"
                        Text(text = "Price Range: $pricerange")
                    }
                    item {
                        val venue = event.venue
                        if (venue != null) {
                            Text(text = "Venue Address: ${venue.address ?: "No available information"}")
                            Text(text = "City: ${venue.city ?: "No available information"}")
                            Text(text = "Country: ${venue.country ?: "No available information"}")
                        } else {
                            Text(text = "Venue information not available")
                        }
                    }
                    item {
                        val info = event.info ?: "No available information"
                        Text(text = "About the event: $info")
                    }
                    item {
                        val attractions = event.attractions
                        if (!attractions.isNullOrEmpty()) {
                            Text("Attractions:")
                            attractions.forEach { attraction ->
                                Text("Attraction: ${attraction.name ?: "No available information"}")
                                // Display other attraction details as needed
                            }
                        } else {
                            Text("No attractions information available")
                        }
                    }
                    // Add more items for other details if needed
                }
            }
        }
    }
}