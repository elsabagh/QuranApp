package com.example.quranapp.util


import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

@SuppressLint("MissingPermission")
fun fetchLocation(
    fusedLocationClient: FusedLocationProviderClient,
    context: Context,
    onLocationFetched: (Double, Double) -> Unit
) {
    val locationRequest = LocationRequest.create().apply {
        priority = Priority.PRIORITY_HIGH_ACCURACY
        interval = 5000
    }

    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult.lastLocation?.let { location ->
                onLocationFetched(location.latitude, location.longitude)
                fusedLocationClient.removeLocationUpdates(this) // Stop updates after getting location
            }
        }
    }

    fusedLocationClient.requestLocationUpdates(
        locationRequest,
        locationCallback,
        Looper.getMainLooper()
    )
}

// Reverse Geocode (Runs in Background)
suspend fun getCityAndCountryFromCoordinates(
    context: Context,
    latitude: Double,
    longitude: Double,
    onResult: (String, String) -> Unit
) {
    withContext(Dispatchers.IO) {
        try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses: List<Address>? = geocoder.getFromLocation(latitude, longitude, 1)

            if (!addresses.isNullOrEmpty()) {
                val city = addresses[0].locality ?: "Unknown City"
                val country = addresses[0].countryName ?: "Unknown Country"

                withContext(Dispatchers.Main) {
                    onResult(city, country)
                }
            } else {
                Log.e("Geocoder", "No address found!")
                withContext(Dispatchers.Main) {
                    onResult("Unknown City", "Unknown Country")
                }
            }
        } catch (e: Exception) {
            Log.e("Geocoder Error", e.message ?: "Unknown error")
            withContext(Dispatchers.Main) {
                onResult("Unknown City", "Unknown Country")
            }
        }
    }
}


fun checkIfGpsEnabled(context: Context): Boolean {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
}

@SuppressLint("MissingPermission")
fun updateLocation(
    fusedLocationClient: FusedLocationProviderClient,
    context: Context,
    onLocationUpdated: (String) -> Unit
) {
    fetchLocation(fusedLocationClient, context) { lat, lon ->
        CoroutineScope(Dispatchers.IO).launch {
            getCityAndCountryFromCoordinates(context, lat, lon) { city, country ->
                onLocationUpdated("$city, $country")
            }
        }
    }
}
