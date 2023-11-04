package com.example.eventfinder.ui.screens

import android.content.Context
import android.location.Location
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.eventfinder.data.api.ticketMaster.getEvents
import com.example.eventfinder.data.models.EventData
import com.example.eventfinder.location.*
import com.example.eventfinder.ui.theme.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventsSearchPage(navController: NavController, context: Context) {
    var selectedEventType by remember { mutableStateOf("All") }
    //var selectedLocation : String = ""
    var radius by remember { mutableStateOf(10.0) }
    var useDeviceLocation by remember { mutableStateOf(true) }
    var searchResults by remember { mutableStateOf<List<EventData>?>(null) }
    var key by remember { mutableStateOf(0) }
    var searching by remember { mutableStateOf(false) } // State for the search progress indicator

    /*LaunchedEffect(searchViewModel.searchResults) {
        searchResults = searchViewModel.searchResults.value ?: emptyList()
    }*/

    Column(modifier = Modifier.fillMaxSize()) {
        // Top Section with search elements
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp) // Adjusted height for additional space
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

                    Button(
                        onClick = {
                            // Set a Boolean key to trigger the effect when it changes
                            key++
                        }
                    ) {
                        Icon(Icons.Filled.Search, contentDescription = "Search")
                    }
                    LaunchedEffect(key) {
                        if (key > 0) {
                            searching = true
                            val results = performSearch(radius, useDeviceLocation, context)
                            searchResults = results
                            searching = false
                            //searchViewModel.updateSearchResults(searchResults!!)
                        }
                    }
                }

                /*Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {
                }*/
            }
        }

        // Adding a progress indicator when searching is in progress
        if (searching) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }

        // Bottom Section to display search results
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 100.dp)
                .weight(1f)
        ) {
            searchResults?.let { events ->
                items(events) { event ->
                    event.printEventData()
                    EventCard(event = event, navController, searchResults)
                }
            }
        }
    }
}


@Composable
fun EventCard(event: EventData, navController: NavController, searchResults: List<EventData>?) {
    val firstImage = event.images.firstOrNull() // Retrieve the first image if available
    var showEventDetails by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                /*// Set the selected event in the ViewModel
                if (searchResults != null) {
                    searchViewModel.updateSearchResults(searchResults)
                }
                // Save the selected event in the EventDetailsViewModel
                eventDetailsViewModel.selectedEvent = event*/
                // Show the EventDetailsPage
                showEventDetails = true
            }
            .height(150.dp), // Height can be adjusted as needed
        elevation =CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ), // Set the elevation (adjust as needed)
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = BlueLitgh
        )) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(100.dp) // Adjust width of the image column
                    .fillMaxHeight()
            ) {
                // Conditional content for the image
                if (firstImage != null) {
                    Image(
                        painter = rememberAsyncImagePainter(firstImage.url),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    // Placeholder or default image if the list is empty
                    // You can replace this with a default image or another placeholder
                    // Image, remember to provide a placeholder content
                    // or suitable fallback image
                    // Example: Image(...)
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Rows for text elements
                Text(
                    text = event.name,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f) // Adjust the weights for text rows
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = event.type,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )

                Spacer(modifier = Modifier.height(8.dp))

                event.venue?.address?.let {
                    Text(
                        text = it,
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                if (showEventDetails) {
                    AlertDialog(
                        onDismissRequest = { showEventDetails = false },
                        title = { Text(text = "Event Details") },
                        confirmButton = {
                            Button(onClick = { showEventDetails = false }) {
                                Text(text = "Close")
                            }
                        },
                        text = {
                            EventDetailsPage(navController, event) {
                                // Callback to dismiss the EventDetailsPage
                                showEventDetails = false
                            }
                        }
                    )
                }
            }
        }
    }
}

suspend fun performSearch(radius: Double, useDeviceLocation: Boolean, context: Context): List<EventData> {
    return suspendCoroutine { continuation ->
        var loc = Location("empty")
        val locationService = LocationService(context)
        if (useDeviceLocation){
            locationService.getLastLocation(context) { location ->
                if (location != null) {
                    loc = location
                }
            }
        }else{
            //loc = selectedLocation  TODO
        }
        getEvents(loc, radius.toInt()) { eventList ->
            // Handle the list of events here
            if (eventList != null) {
                continuation.resume(eventList)
            } else {
                continuation.resume(emptyList())
            }
        }
    }
}



