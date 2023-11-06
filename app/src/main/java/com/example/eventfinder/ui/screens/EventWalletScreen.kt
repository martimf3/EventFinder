package com.example.eventfinder.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.eventfinder.data.models.EventData

@Composable
fun EventWalletPage(
    navController: NavController,
    context: Context,
    events: List<EventData>
) {
    var selectedEventType by remember { mutableStateOf<String?>(null) }
    var filteredEvents by remember { mutableStateOf<List<EventData>?>(events) }

    // Extract event types from the list of events
    val eventTypes = events.map { it.classifications?.firstOrNull()?.segment?.name }.distinct()

    Column(modifier = Modifier.fillMaxSize()) {
        // Top Section with search elements including the Spinner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp) // Adjusted height for additional space
                .background(Color.LightGray)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Title for search section
                Text(
                    text = "Filter Events by Type",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Dropdown-like Text for event type selection
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedEventType = selectedEventType?.let { null }
                        }
                ) {
                    Text(
                        text = selectedEventType ?: "Select Event Type",
                        modifier = Modifier.padding(8.dp)
                    )

                    if (selectedEventType != null) {
                        DropdownMenu(
                            expanded = true,
                            onDismissRequest = { selectedEventType = null }
                        ) {
                            eventTypes.forEach { eventType ->
                                DropdownMenuItem(
                                    { Text(text = eventType ?: "All Events") },
                                    onClick = {
                                        selectedEventType = eventType
                                        filteredEvents = events.filter {
                                            it.classifications?.firstOrNull()?.segment?.name == selectedEventType
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        // Display filtered or all events based on selection
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 100.dp)
                .weight(1f)
        ) {
            items(filteredEvents ?: events) { event ->
                EventCard(context, event, navController, events)
            }
        }
    }
}
