package com.example.eventfinder.data.api.ticketMaster
import android.location.Location
import com.example.eventfinder.data.models.EventData


fun getEvents(location : Location, radius: Int, callback: (List<EventData>?) -> Unit)  {
    val apiKey = "AQ7VrBIVkBcZzZDfGo2KiYjsVMib6pRi"
    val eventSearch = TicketmasterEventSearch(apiKey)
    val latitude = location.latitude
    println(latitude)
    val longitude = location.longitude
    println(longitude)
    val eventList: MutableList<EventData> = mutableListOf()

    eventSearch.searchEvents(latitude, longitude, radius) { eventData ->
        if (eventData != null) {
            eventList.addAll(eventData)
            callback(eventList)
        } else {
            callback(eventList)
            println("Failed to retrieve event data.")
        }

    }
}