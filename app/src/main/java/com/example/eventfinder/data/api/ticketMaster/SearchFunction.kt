package com.example.eventfinder.data.api.ticketMaster

import com.example.eventfinder.data.models.EventData

fun GetEvents( callback: (List<EventData>?) -> Unit)  {

    val apiKey = "AQ7VrBIVkBcZzZDfGo2KiYjsVMib6pRi"
    val eventSearch = TicketmasterEventSearch(apiKey)
    val latitude = 37.7749
    val longitude = -122.4194
    val radius = 10
    var eventList: List<EventData> = listOf()

    eventSearch.searchEvents(latitude, longitude, radius) { eventData ->
        if (eventData != null) {
            for (event in eventData) {
                println("Event ID: ${event.id}, Event Name: ${event.name}",)
                eventList+event

            }
            callback(eventList)

        } else {
            println("Failed to retrieve event data.")
        }

    }
}