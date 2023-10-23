package com.example.eventfinder.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.AsyncTask
import androidx.core.content.ContextCompat
import com.example.eventfinder.utils.LocationUtils
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.maps.GeoApiContext
import com.google.maps.PlacesApi
import com.google.maps.model.LatLng
import com.google.maps.model.PlacesSearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LocationService(private val context: Context) {
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    private val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION
    private val apiKey =
        "**************************" // Replace with your Google Maps API Key

    interface LocationCallback {
        fun onLocationResult(location: Location?)
        fun onCitiesWithinRadiusResult(cityNames: List<String>)
        fun onError(errorMessage: String)
    }

    inner class LocationAsyncTask(private val callback: LocationCallback) :
        AsyncTask<Void, Void, Location>() {
        override fun doInBackground(vararg params: Void?): Location? {
            if (ContextCompat.checkSelfPermission(
                    context,
                    locationPermission
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                if (LocationUtils.isLocationEnabled(context)) {
                    try {
                        // Retrieve location asynchronously
                        fusedLocationClient.lastLocation
                            .addOnSuccessListener { location ->
                                // Successfully retrieved the location
                                callback.onLocationResult(location)
                            }
                            .addOnFailureListener { exception ->
                                // Handle the exception and notify the callback
                                callback.onError("Error while retrieving location: ${exception.message}")
                            }
                        return null
                    } catch (exception: Exception) {
                        callback.onError("Error while retrieving location: ${exception.message}")
                        return null
                    }
                } else {
                    callback.onError("Location services are not enabled.")
                    LocationUtils.requestLocationSettings(context)
                    return null
                }
            }
            callback.onError("Location permission not granted.")
            return null
        }


        override fun onPostExecute(result: Location?) {
            callback.onLocationResult(result)
        }
    }

    fun getLastLocation(callback: LocationCallback) {
        val locationAsyncTask = LocationAsyncTask(callback)
        locationAsyncTask.execute()
    }

    fun getCitiesWithinRadius(location: Location, radiusKm: Double, callback: LocationCallback) {
        if (radiusKm > 50.0) {
            callback.onError("The radius exceeds the maximum allowed value of 50 kilometers.")
            println("hey baby ur here")
            return
        }
        println("it passed yey")
        // Convert location to a LatLng object for Google Maps
        val center = LatLng(location.latitude, location.longitude)
        val radiusMeters = radiusKm * 1000.0

        // Create a GeoApiContext with your API key
        val geoContext = GeoApiContext.Builder()
            .apiKey(apiKey)
            .build()

        // Create a Places request for nearby cities
        val placesRequest = PlacesApi.nearbySearchQuery(geoContext, center)
            .location(center)
            .radius(radiusMeters.toInt())

        // Use a coroutine to make an asynchronous call
        GlobalScope.launch(Dispatchers.IO) {
            //try {
                // Use await to get the Places search result
                val placesResult = placesRequest.await()
                val cityNames = mutableListOf<String>()
                for (result: PlacesSearchResult in placesResult.results) {
                    cityNames.add(result.name)
                }
                // Notify the callback with the list of city names
                callback.onCitiesWithinRadiusResult(cityNames)
           // } catch (e: Exception) {
                // Handle any errors and notify the callback
               // callback.onError("Error while looking up cities: ${e.message}")
            //}
        }
    }

}


