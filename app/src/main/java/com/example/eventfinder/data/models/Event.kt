package com.example.eventfinder.data.models
data class EventResponse(
    val _embedded: Embedded?
)

data class Embedded(
    val events: List<EventData>?,
    val venues: List<Venue>?
    // Add other potential embedded data here
)

data class EventData(
    val name: String,
    val type: String,
    val id: String,
    val test: Boolean,
    val url: String,
    val locale: String,
    val images: List<EventImage>?,
    val distance: Double,
    val units: String,
    val sales: SalesData?, // You can define a better structure according to the expected data
    val dates: Dates?,
    val classifications: List<Classification>?,
    val promoter: Promoter?,
    val promoters: List<Promoter>?,
    val priceRanges: List<PriceRange>?,
    val _links: Links,
    val _embedded: Embedded?
)

data class Venue(
    val name: String,
    val type: String,
    val id: String,
    val test: Boolean,
    val url: String,
    val locale: String,
    val distance: Double,
    val units: String,
    val postalCode: String,
    val timezone: String,
    val city: City,
    val state: State,
    val country: Country,
    val address: Address,
    val location: Coordinates,
    val upcomingEvents: UpcomingEvents,
    val _links: Links
)

data class City(
    val name: String
)

data class State(
    val name: String
)

data class Country(
    val name: String,
    val countryCode: String
)

data class Address(
    val line1: String
)

data class Coordinates(
    val longitude: String,
    val latitude: String
)

data class UpcomingEvents(
    val mfx_es: Int,
    val _total: Int,
    val _filtered: Int
)

data class EventImage(
    val ratio: String,
    val url: String,
    val width: Int,
    val height: Int,
    val fallback: Boolean
)

data class Dates(
    val start: Start,
    val timezone: String,
    val status: Status,
    val spanMultipleDays: Boolean
)

data class Start(
    val localDate: String,
    val localTime: String,
    val dateTime: String,
    val dateTBD: Boolean,
    val dateTBA: Boolean,
    val timeTBA: Boolean,
    val noSpecificTime: Boolean
)

data class Status(
    val code: String
)

data class Classification(
    val primary: Boolean,
    val segment: Segment,
    val genre: Genre,
    val subGenre: SubGenre,
    val type: Type,
    val subType: SubType,
    val family: Boolean
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

data class Type(
    val id: String,
    val name: String
)

data class SubType(
    val id: String,
    val name: String
)

data class SalesData(
    val public: PublicSales
)

data class PublicSales(
    val startDateTime: String,
    val startTBD: Boolean,
    val startTBA: Boolean,
    val endDateTime: String
)

data class Promoter(
    val id: String,
    val name: String
)

data class PriceRange(
    val type: String,
    val currency: String,
    val min: Double,
    val max: Double
)

data class Links(
    val self: Self,
    val attractions: List<AttractionsLink>?,
    val venues: List<VenuesLink>?
    // Other potential links
)

data class Self(
    val href: String
)

data class AttractionsLink(
    val href: String
)

data class VenuesLink(
    val href: String
)