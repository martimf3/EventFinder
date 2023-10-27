package com.example.eventfinder.data.models

data class EventResponse(
    val _embedded: EmbeddedData,
    val _links: LinksData
)

data class EmbeddedData(
    val events: List<EventData>
)

data class EventData(
    val name: String,
    val type: String,
    val id: String,
    val url: String,
    val images: List<EventImage>,
    // Outros campos de evento que você deseja incluir
)

data class EventImage(
    val ratio: String,
    val url: String,
    val width: Int,
    val height: Int,
    val fallback: Boolean
)

data class LinksData(
    val self: SelfLink
)

data class SelfLink(
    val href: String
)
