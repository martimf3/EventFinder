package com.example.eventfinder.data.api.ticketMaster
import com.example.eventfinder.data.models.EventData
import com.example.eventfinder.data.models.EventResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TicketmasterApiService {
    @GET("events.json")
    fun searchEvents(
        @Query("apikey") apiKey: String,
        @Query("geoPoint") geoPoint: String,
        @Query("radius") radius: Int
    ): Call<EventResponse>
}

class TicketmasterEventSearch(private val apiKey: String) {
    private val baseUrl = "https://app.ticketmaster.com/discovery/v2/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(TicketmasterApiService::class.java)

    fun searchEvents(latitude: Double, longitude: Double, radius: Int, callback: (List<EventData>?) -> Unit) {
        val geoPoint = "$latitude,$longitude"

        val call = apiService.searchEvents(apiKey, geoPoint, radius)

        call.enqueue(object : retrofit2.Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if (response.isSuccessful) {
                    val eventResponse = response.body()
                    val eventData = eventResponse?._embedded?.events?.map { event ->
                        EventData(
                            event.id,
                            event.name,
                            event.type,
                            event.url,
                            event.dates,
                            event.pricerange,
                            event.attractions,
                            event.venue,
                            event.info,
                            event.images
                            // Other event information as needed
                        )
                    }
                    callback(eventData)
                } else {
                    println("Request failed with status code ${response.code()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                println("Request error: $t")
                callback(null)
            }
        })
    }
}
