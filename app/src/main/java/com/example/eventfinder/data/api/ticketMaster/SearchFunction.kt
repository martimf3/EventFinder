package com.example.eventfinder.data.api.ticketMaster

import android.location.Location
import com.example.eventfinder.MyApplication
import com.example.eventfinder.data.models.EventData
import com.example.eventfinder.location.LocationService

fun GetEvents(location: Location, radius: Int, callback: (List<EventData>?) -> Unit)  {
    val context = MyApplication.applicationContext()
    val locationService = LocationService(context)

    val apiKey = "Querias"
    val eventSearch = TicketmasterEventSearch(apiKey)
    val latitude = location.latitude
    val longitude = location.longitude
    val radius = radius
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