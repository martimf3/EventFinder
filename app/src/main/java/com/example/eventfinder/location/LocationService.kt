package com.example.eventfinder.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnCompleteListener
import java.io.IOException
import java.util.Locale

class LocationService(private val context: Context) {
    private val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    private val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION
    private val geocoder = Geocoder(context, Locale.getDefault())


    fun getLastLocation(callback: (Location?) -> Unit) {
        if (ContextCompat.checkSelfPermission(context, locationPermission) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (task.isSuccessful && task.result != null) {
                        callback(task.result)
                    } else {
                        callback(null)
                    }
                })
        } else {
            callback(null)
        }
    }

    fun getCitiesWithinRadius(location: Location?, radiusKm: Double, callback: (List<String>) -> Unit) {
        val cityNames = mutableListOf<String>()
        try {
            val addresses =
                location?.let { geocoder.getFromLocation(it.latitude, location.longitude, 1) }
            if (addresses != null) {
                if (addresses.isNotEmpty()) {
                    val currentAddress = addresses?.get(0)

                    val nearbyAddresses =
                        currentAddress?.let { geocoder.getFromLocationName(it.locality, 5) }
                    if (nearbyAddresses != null) {
                        for (address in nearbyAddresses) {
                            val distance = calculateDistance(
                                location.latitude, location.longitude,
                                address.latitude, address.longitude
                            )

                            if (distance <= radiusKm) {
                                cityNames.add(address.locality)
                            }
                        }
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        callback(cityNames)
    }

    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val R = 6371 // Radius of the Earth in kilometers
        val latDistance = Math.toRadians(lat2 - lat1)
        val lonDistance = Math.toRadians(lon2 - lon1)
        val a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                (Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2))
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        return (R * c).toDouble()
    }
}
