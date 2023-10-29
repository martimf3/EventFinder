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

class LocationService(private val context: Context) {
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    private val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION
    private val apiKey =
        "**************************" // Replace with your Google Maps API Key

    interface LocationCallback {
        fun onLocationResult(location: Location?)
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

}


