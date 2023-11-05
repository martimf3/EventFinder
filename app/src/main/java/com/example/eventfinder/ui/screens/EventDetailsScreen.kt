package com.example.eventfinder.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
                    fontSize = 24.sp // Larger font size
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        selectedEvent?.let { event ->
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                val startDate = event.dates?.start?.localDate ?: "No date available"
                val startTime = event.dates?.start?.localTime ?: "No starting time available"
                Text(
                    text = "Start Date: $startDate, $startTime",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                val classification = event.classifications.firstOrNull()
                val classificationText = classification?.let {
                    "${it.segment?.name ?: "N/A"}, ${it.genre?.name ?: "N/A"}"
                } ?: "No available information"

                Text(
                    text = "Event type: $classificationText",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                event._embedded?.venues?.let { venues ->
                    val venue = venues.firstOrNull()
                    val venueInfo = venue?.let {
                        "${venue.address?.line1 ?: "N/A"}, ${venue.city?.name ?: "N/A"}, ${venue.country?.name ?: "N/A"}"
                    } ?: "No venue information available"

                    Text(
                        text = "Venue Information: $venueInfo",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                val minPrice = event.priceRanges.minByOrNull { it.min }?.min ?: 0.0
                val maxPrice = event.priceRanges.maxByOrNull { it.max }?.max ?: 0.0
                val currency = event.priceRanges.firstOrNull()?.currency ?: "N/A"

                Text(
                    text = "Price Range: $minPrice - $maxPrice $currency",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                //val formattedDateTime = formatDateTime(event.sales.public.endDateTime)

                Text(
                    text = "Ticket Sales End in: ${event.sales.public.endDateTime}",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Text(
                    text = "Promotores: ${event.promoter.name}",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                ClickableText(text = annotatedString, onClick = {
                    val uri = Uri.parse(urlString)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    if (intent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(intent)
                    }
                })

                val firstImage = selectedEvent?.images?.firstOrNull()
                firstImage?.let { image ->
                    Image(
                        painter = rememberAsyncImagePainter(image.url),
                        contentDescription = null,
                        modifier = Modifier
                            .size(150.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .align(Alignment.CenterHorizontally)
                            .padding(vertical = 16.dp)
                    )
                }
            }
        }
    }
}

/*fun formatDateTime(dateTime: String): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
    val outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm:ss")

    val zonedDateTime = ZonedDateTime.parse(dateTime, inputFormatter)
    return zonedDateTime.format(outputFormatter)
}*/