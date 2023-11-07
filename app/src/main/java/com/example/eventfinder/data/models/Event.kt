package com.example.eventfinder.data.models
data class EventResponse(
    val _embedded: Embedded?
)

data class Embedded(
    val events: List<EventData>?,
    val venues: List<Venue>?
    // Outros dados incorporados
)

data class EventData(
    val name: String = "",
    val type: String = "",
    val id: String = "",
    val test: Boolean = false,
    val url: String = "",
    val locale: String = "",
    val images: List<EventImage>? = null,
    val distance: Double = 0.0,
    val units: String = "",
    val sales: SalesData? = null,
    val dates: Dates? = null,
    val classifications: List<Classification>? = null,
    val promoter: Promoter? = null,
    val promoters: List<Promoter>? = null,
    val priceRanges: List<PriceRange>? = null,
    val _links: Links? = null,
    val _embedded: Embedded? = null
)

data class Venue(
    val name: String = "",
    val type: String = "",
    val id: String = "",
    val test: Boolean = false,
    val url: String = "",
    val locale: String = "",
    val distance: Double = 0.0,
    val units: String = "",
    val postalCode: String = "",
    val timezone: String = "",
    val city: City = City(),
    val state: State = State(),
    val country: Country = Country(),
    val address: Address = Address(),
    val location: Coordinates = Coordinates(),
    val upcomingEvents: UpcomingEvents = UpcomingEvents(),
    val _links: Links = Links()
)

data class City(
    val name: String = ""
)

data class State(
    val name: String = ""
)

data class Country(
    val name: String = "",
    val countryCode: String = ""
)

data class Address(
    val line1: String = ""
)

data class Coordinates(
    val longitude: String = "",
    val latitude: String = ""
)

data class UpcomingEvents(
    val mfx_es: Int = 0,
    val _total: Int = 0,
    val _filtered: Int = 0
)

data class EventImage(
    val ratio: String = "",
    val url: String = "",
    val width: Int = 0,
    val height: Int = 0,
    val fallback: Boolean = false
)

data class Dates(
    val start: Start = Start(),
    val timezone: String = "",
    val status: Status = Status(),
    val spanMultipleDays: Boolean = false
)

data class Start(
    val localDate: String = "",
    val localTime: String = "",
    val dateTime: String = "",
    val dateTBD: Boolean = false,
    val dateTBA: Boolean = false,
    val timeTBA: Boolean = false,
    val noSpecificTime: Boolean = false
)

data class Status(
    val code: String = ""
)

data class Classification(
    val primary: Boolean = false,
    val segment: Segment = Segment(),
    val genre: Genre = Genre(),
    val subGenre: SubGenre = SubGenre(),
    val type: Type = Type(),
    val subType: SubType = SubType(),
    val family: Boolean = false
)

data class Segment(
    val id: String = "",
    val name: String = ""
)

data class Genre(
    val id: String = "",
    val name: String = ""
)

data class SubGenre(
    val id: String = "",
    val name: String = ""
)

data class Type(
    val id: String = "",
    val name: String = ""
)

data class SubType(
    val id: String = "",
    val name: String = ""
)

data class SalesData(
    val public: PublicSales = PublicSales()
)

data class PublicSales(
    val startDateTime: String = "",
    val startTBD: Boolean = false,
    val startTBA: Boolean = false,
    val endDateTime: String = ""
)

data class Promoter(
    val id: String = "",
    val name: String = ""
)

data class PriceRange(
    val type: String = "",
    val currency: String = "",
    val min: Double = 0.0,
    val max: Double = 0.0
)

data class Links(
    val self: Self = Self(),
    val attractions: List<AttractionsLink> = listOf(),
    val venues: List<VenuesLink> = listOf()
    // Outros links potenciais
)

data class Self(
    val href: String = ""
)

data class AttractionsLink(
    val href: String = ""
)

data class VenuesLink(
    val href: String = ""
)
