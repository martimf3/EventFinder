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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.eventfinder.data.api.ticketMaster.getEvents
import com.example.eventfinder.data.models.EventData
import com.example.eventfinder.location.*
import com.example.eventfinder.ui.theme.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventsSearchPage(navController: NavController, context: Context) {
    var radius by remember { mutableStateOf(50.0) }
    var useDeviceLocation by remember { mutableStateOf(true) }
    var searchResults by remember { mutableStateOf<List<EventData>?>(null) }
    var key by remember { mutableStateOf(0) }
    var searching by remember { mutableStateOf(false) } // State for the search progress indicator
    var showNoEventsFound by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxSize()){
        // Top Section with search elements
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp) // Adjusted height for additional space
                .background(Color.White)
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
                    // Slider for search radius
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text("Search Radius: ${radius.toInt()} km")
                        Slider(
                            value = radius.toFloat(),
                            onValueChange = { radius = it.toDouble() },
                            valueRange = 50.0f..200.0f,
                            steps = 150,
                            modifier = Modifier.fillMaxWidth(),
                            colors = SliderDefaults.colors(
                                thumbColor = Color.Black,
                                activeTrackColor = Color.Black,
                                inactiveTrackColor = Color.Transparent
                            )

                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

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
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Color.Green.copy(alpha = 0.5f),
                                uncheckedThumbColor = Color.Black,
                                uncheckedTrackColor = Color.Red.copy(alpha = 0.3f),
                            )
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Button(
                        onClick = {
                            // Set a Boolean key to trigger the effect when it changes
                            key++
                        },
                        colors = ButtonDefaults.buttonColors(Color.Black.copy(alpha = 0.8f))
                    ) {
                        Icon(imageVector = Icons.Filled.Search,
                            contentDescription = "Search",
                            tint = Color.White,
                            modifier = Modifier
                                .scale(1.3f,1.3f),)
                    }
                    LaunchedEffect(key) {
                        println("antes if")
                        if (key > 0) {
                            println("1")
                            searching = true
                            println("2")
                            val results = performSearch(radius, useDeviceLocation, context)
                            println("3")
                            searchResults = results
                            println("4")
                            searching = false
                            println("5")
                            showNoEventsFound = results.isEmpty()

                        }
                    }
                }
            }
        }

        // Adding a progress indicator when searching is in progress
        if (searching) {
            CircularProgressIndicator(
                color = Color.Black,
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
            items(searchResults.orEmpty()) { event ->
                EventCard(context, event = event, navController, searchResults, false)
            }
        }

        if (searchResults != null && searchResults!!.isEmpty() && !searching && showNoEventsFound) {
            Text(
                text = "No events found in that area!",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .wrapContentSize(Alignment.Center)
            )
        }
    }
}


@Composable
fun EventCard(context: Context, event: EventData, navController: NavController, searchResults: List<EventData>?, isWalletList: Boolean) {
    val firstImage = event.images?.firstOrNull() // Retrieve the first image if available
    var showEventDetails by remember { mutableStateOf(false) }
    var eventInWishList by remember { mutableStateOf(false) }
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    val eventId = event.id

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                // Show the EventDetailsPage
                showEventDetails = true
            }
            .height(150.dp), // Height can be adjusted as needed
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ), // Set the elevation (adjust as needed)
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = BlueLitgh
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(130.dp) // Adjust width of the image column
                    .fillMaxHeight()
            ) {
                // Conditional content for the image
                if (firstImage != null) {
                    Image(
                        painter = rememberAsyncImagePainter(firstImage.url),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                } else {
                   
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            
            Column(
                modifier = Modifier.fillMaxSize(),

            ) {
                //Button Card

                Text(
                    text = event.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )


                event.classifications?.firstOrNull()?.genre?.let {
                    Text(
                        text = "${event.classifications.firstOrNull()!!.genre.name} ${it.name}",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }

                event._embedded?.venues?.let { venues ->
                    if (venues.isNotEmpty()) {
                        val venueInfo = buildString {
                            venues.forEach { venue ->
                                appendLine("${venue.address?.line1 ?: "N/A"}, ${venue.city?.name ?: "N/A"}, ${venue.country?.name ?: "N/A"}")
                            }
                        }
                        Text(
                            text = venueInfo,
                            fontSize = 12.sp,
                        )
                    } else {
                        Text(
                            text = "No venue information available",
                            fontSize = 12.sp,
                        )
                    }
                }

                Row {
                    Text(
                        text = "${event.dates?.start?.localDate}, ${event.dates?.start?.localTime}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )


                    IconButton(onClick = {
                        eventInWishList = !eventInWishList
                        val currentUser = FirebaseAuth.getInstance().currentUser
                        val eventId = event.id
                        val userId = currentUser?.uid.toString()
                        val addUserEvent = Firebase.firestore.collection("users").document(userId).collection("wishlist")

                        addUserEvent.document(eventId).get().addOnSuccessListener { documentSnapshot ->
                            if(documentSnapshot.exists()){
                                addUserEvent.document(eventId).delete()
                                if (isWalletList){
                                    navController.navigate("wishlist")
                                }

                            }else {
                                addUserEvent.document(eventId)
                                    .set(
                                        mapOf(
                                            "name" to event.name,
                                            "type" to event.type,
                                            "id" to event.id,
                                            "test" to event.test,
                                            "url" to event.url,
                                            "locale" to event.locale,
                                            "distance" to event.distance,
                                            "units" to event.units,
                                            // Mapear as imagens do evento se houver
                                            "images" to event.images?.map { image ->
                                                mapOf(
                                                    "ratio" to image.ratio,
                                                    "url" to image.url,
                                                    "width" to image.width,
                                                    "height" to image.height,
                                                    "fallback" to image.fallback
                                                )
                                            },
                                            "sales" to mapOf(
                                                "public" to mapOf(
                                                    "startDateTime" to event.sales?.public?.startDateTime,
                                                    "startTBD" to event.sales?.public?.startTBD,
                                                    "startTBA" to event.sales?.public?.startTBA,
                                                    "endDateTime" to event.sales?.public?.endDateTime
                                                )
                                            ),
                                            "dates" to mapOf(
                                                "start" to mapOf(
                                                    "localDate" to event.dates?.start?.localDate,
                                                    "localTime" to event.dates?.start?.localTime,
                                                    "dateTime" to event.dates?.start?.dateTime,
                                                    "dateTBD" to event.dates?.start?.dateTBD,
                                                    "dateTBA" to event.dates?.start?.dateTBA,
                                                    "timeTBA" to event.dates?.start?.timeTBA,
                                                    "noSpecificTime" to event.dates?.start?.noSpecificTime
                                                ),
                                                "timezone" to event.dates?.timezone,
                                                "status" to mapOf(
                                                    "code" to event.dates?.status?.code
                                                ),
                                                "spanMultipleDays" to event.dates?.spanMultipleDays
                                            ),
                                            "classifications" to event.classifications?.map { classification ->
                                                mapOf(
                                                    "primary" to classification.primary,
                                                    "segment" to mapOf(
                                                        "id" to classification.segment.id,
                                                        "name" to classification.segment.name
                                                    ),
                                                    "genre" to mapOf(
                                                        "id" to classification.genre.id,
                                                        "name" to classification.genre.name
                                                    ),
                                                    "subGenre" to mapOf(
                                                        "id" to classification.subGenre.id,
                                                        "name" to classification.subGenre.name
                                                    ),
                                                    "type" to mapOf(
                                                        "id" to classification.type.id,
                                                        "name" to classification.type.name
                                                    ),
                                                    "subType" to mapOf(
                                                        "id" to classification.subType.id,
                                                        "name" to classification.subType.name
                                                    ),
                                                    "family" to classification.family
                                                )
                                            },

                                            )
                                    )
                            }
                        }


                    },
                        modifier = Modifier
                            .scale(1.5f, 1.5f)
                            .offset(x = 30.dp, y = 0.dp),)
                    {



                        if (userId != null){
                            val docRef = Firebase.firestore.collection("users").document(userId).collection("wishlist").document(eventId)
                            docRef.get()
                                .addOnSuccessListener { documentSnapshot ->
                                    eventInWishList = documentSnapshot.exists()
                                }
                        }
                           val icon = if(eventInWishList){
                               Icons.Filled.Delete

                            }else{
                               Icons.Filled.Favorite
                            }

                        Icon(imageVector = icon,
                            contentDescription = "",
                            tint = WhiteLigth.copy(alpha = 0.4f))


                    }

                }


                if (showEventDetails) {
                    AlertDialog(
                        properties = DialogProperties(usePlatformDefaultWidth = false), // Ensure a custom width
                        onDismissRequest = { showEventDetails = false },
                        title = { Text(text = "Event Details") },
                        confirmButton = {
                            Button(onClick = { showEventDetails = false }) {
                                Text(text = "Close")
                            }
                        },
                        text = {
                            Box(
                                modifier = Modifier.fillMaxWidth() // Take full width of the dialog
                            ) {
                                EventDetailsPage(context, event) {
                                    showEventDetails = false
                                }
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
                    //println("lat: ${location.latitude}, long: ${location.longitude}")
                    loc = location
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
        }else{
            //loc = selectedLocation  TODO
        }
    }
}




