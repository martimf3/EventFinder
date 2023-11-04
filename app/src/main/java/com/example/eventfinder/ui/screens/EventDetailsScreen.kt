package com.example.eventfinder.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.eventfinder.data.models.EventData



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailsPage(context: Context, event: EventData, onBack: () -> Unit) {
    val selectedEvent = event
    val urlString = event.url ?: ""
    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.Blue, fontSize = 16.sp)) {
            append("$urlString")
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = {
                Text(
                    text = selectedEvent?.name ?: "Event Details",
                    fontSize = 20.sp // Larger font size
                )
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
                        val startDate = event.dates?.start?.localDate ?: "No available information"
                        Text(
                            text = "Start Date: $startDate",
                            fontSize = 16.sp, // Larger font size
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                    item {
                        val venue = event.venue?.firstOrNull()
                        val venueText = venue?.let {
                            val venueName = it.name ?: "No available information"
                            val venueAddress = it.address?.line1 ?: ""
                            val venueCountry = it.country?.name ?: ""
                            "Venue Information: $venueName, $venueAddress, $venueCountry"
                        } ?: "No available information"

                        Text(
                            text = venueText,
                            fontSize = 16.sp, // Larger font size
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    item {
                        val classification = event.classifications.firstOrNull()
                        val classificationText = classification?.let {
                            "${it.segment?.name ?: "N/A"}, ${it.genre?.name ?: "N/A"}, ${it.subGenre?.name ?: "N/A"}"
                        } ?: "No available information"

                        Text(
                            text = "Event type: $classificationText",
                            fontSize = 16.sp, // Larger font size
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                    // Add other details in a similar manner

                    item {
                        println(event.attractions?.count())
                        val attraction = event.attractions?.firstOrNull()
                        val attractionText = attraction?.let {
                            "Attraction: ${it.name ?: "No available information"}"
                        } ?: "No attractions information available"

                        Text(
                            text = attractionText,
                            fontSize = 16.sp, // Larger font size
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    // Combine images from event and attraction into a single list
                    val allImages = event.images + (event.attractions?.firstOrNull()?.images.orEmpty())

                    // Carousel for combined images
                    item {
                        if (allImages.isNotEmpty()) {
                            LazyRow {
                                items(allImages) { image ->
                                    Image(
                                        painter = rememberAsyncImagePainter(image.url),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(150.dp) // Adjust size as needed
                                            .padding(8.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                    )
                                }
                            }
                        } else {
                            Text(
                                text = "No images available",
                                fontSize = 16.sp, // Larger font size
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                    }

                    item {
                        ClickableText(text = annotatedString, onClick = {
                            val uri = Uri.parse(urlString)
                            val intent = Intent(Intent.ACTION_VIEW, uri)
                            // Check if there's an activity that can handle this intent
                            if (intent.resolveActivity(context.packageManager) != null) {
                                context.startActivity(intent)
                            }
                        })
                    }
                }
            }
        }
    }
}