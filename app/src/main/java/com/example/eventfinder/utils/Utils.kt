package com.example.eventfinder.utils

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings

class LocationUtils {
    companion object {
        fun isLocationEnabled(context: Context): Boolean {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                    || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        }

        fun requestLocationSettings(context: Context) {
            val locationRequest = LocationManager.GPS_PROVIDER
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            intent.putExtra("locationRequest", locationRequest)
            context.startActivity(intent)
        }
    }
}
