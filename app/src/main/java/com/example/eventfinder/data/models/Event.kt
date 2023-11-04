package com.example.eventfinder.data.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

data class EventResponse(
    val _embedded: EmbeddedData,
    val _links: LinksData
)

data class EmbeddedData(
    val events: List<EventData>
)

data class LinksData(
    val self: SelfLink
)

data class SelfLink(
    val href: String
)

data class EventData(
    val id: String,
    val name: String,
    val type: String,
    val url: String,
    val dates: EventDates,
    val pricerange: String?,
    val attractions: List<Attraction>?,
    val venue: Venues?,
    val info: String?,
    val images: List<EventImage>
) {
    fun printEventData(){
        println("ID: $id, name: $name,type: $type, url: $url, dates: $dates, pricerange: $pricerange, venue: ${venue.toString()}, info: $info, ")
    }
}

data class Attraction(
    val id: String,
    val name: String,
    val type: String,
    val images: List<EventImage>
)

data class EventDates(
    val start: EventStartDate,
    val timezone: String,
    val status: EventStatus
)

data class EventStatus(
    val code: String
)

data class EventStartDate(
    val localDate: String,
    val dateTBD: Boolean,
    val dateTBA: Boolean,
    val timeTBA: Boolean,
    val noSpecificTime: Boolean
)

data class Venues(
    val id: String,
    val name: String,
    val type: String,
    val locate: String,
    val url: String,
    val location: String,
    val address: String,
    val city: String,
    val country: String
)

data class EventImage(
    val ratio: String,
    val url: String,
    val width: Int,
    val height: Int,
    val fallback: Boolean
)

class EventDetailsViewModel : ViewModel() {
    var selectedEvent: EventData? = null
}
class SearchViewModel : ViewModel() {
    val searchResults: MutableState<List<EventData>> = mutableStateOf(emptyList())

    fun updateSearchResults(results: List<EventData>) {
        searchResults.value = results
    }
}