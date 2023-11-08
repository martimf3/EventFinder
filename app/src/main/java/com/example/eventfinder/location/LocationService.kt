package com.example.eventfinder.location

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LocationService(private val context: Context) {
    private val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    var isPermissionGranted = false
        private set

    private val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION
    val LOCATION_PERMISSION_REQUEST_CODE = 1001

    fun getLastLocation(callback: (Location?) -> Unit) {
        try {
            if (isLocationPermissionGranted()) {
                fusedLocationClient.lastLocation
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful && task.result != null) {
                            callback(task.result)
                        } else {
                            callback(null)
                        }
                    }
            } else {
                requestLocationPermission()
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    private fun isLocationPermissionGranted(): Boolean {
        val isGranted = ContextCompat.checkSelfPermission(context, locationPermission) == PackageManager.PERMISSION_GRANTED
        isPermissionGranted = isGranted
        return isGranted
    }

    private fun requestLocationPermission() {
        if (context is Activity) {
            ActivityCompat.requestPermissions(
                context,
                arrayOf(locationPermission),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            Log.e("LocationPermission", "Context is not an Activity")
            // Handle the scenario where the context is not an Activity
        }
    }

    fun updatePermissionStatus(grantResults: IntArray) {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            isPermissionGranted = true
        }
    }
}

