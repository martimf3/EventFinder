package com.example.eventfinder.data.models

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
    val classifications: List<Classifications>,
    val attractions: List<Attraction>?,
    val venue: List<Venues>?,
    val info: String?,
    val images: List<EventImage>
) {
    fun printEventData(){
        println("ID: $id, name: $name,type: $type, url: $url, dates: $dates, venue: ${venue.toString()}, info: $info, ")
    }
}

data class Classifications(
    val primary: Boolean,
    val segment: Segment?,
    val genre: Genre?,
    val subGenre: SubGenre?
)

data class Segment(
    val id: String,
    val name: String
)

data class Genre(
    val id: String,
    val name: String
)

data class SubGenre(
    val id: String,
    val name: String
)

data class Attraction(
    val name: String,
    val type: String,
    val id: String,
    val test: Boolean,
    val locale: String,
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
    val locale: String,
    val postalCode: String,
    val timezone: String,
    val city: City,
    val state: State,
    val country: Country,
    val address: Address,
    val location: Location,
    val markets: List<Market>,
    val _links: LinksData
)

data class State(
    val name: String,
    val stateCode: String
)

data class Location(
    val longitude: String,
    val latitude: String
)

data class Market(
    val id: String
)

data class Address(
    val line1: String
)
data class City(
    val name: String
)
data class Country (
    val name: String,
    val countryCode: String
)

data class EventImage(
    val ratio: String,
    val url: String,
    val width: Int,
    val height: Int,
    val fallback: Boolean
)