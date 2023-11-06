package com.example.eventfinder.location

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnCompleteListener

class LocationService(context: Context) {

    private val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    private val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION
    private val LOCATION_PERMISSION_REQUEST_CODE = 1001

    fun getLastLocation(context: Context, callback: (Location?) -> Unit) {
        try {
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
                // Permission is not granted at the moment, request permission
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(locationPermission),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }
        } catch (e: Exception) {
            println("Error: $e")
        }
    }
}

