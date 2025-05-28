package com.example.mrbugger_app.ui.screen.OrderConfirmation

import android.content.Context
import android.location.Geocoder
import android.os.Build
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

fun getAddressFromLocation(
    context: Context,
    latitude: Double,
    longitude: Double,
    callback: (String) -> Unit
) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocoder.getFromLocation(latitude, longitude, 1) // modern version
            } else {
                @Suppress("DEPRECATION")
                geocoder.getFromLocation(latitude, longitude, 1)
            }

            val address = addresses?.firstOrNull()?.getAddressLine(0) ?: "Unknown location"
            CoroutineScope(Dispatchers.Main).launch {
                callback(address)
            }
        } catch (e: Exception) {
            CoroutineScope(Dispatchers.Main).launch {
                callback("Unable to get address")
            }
        }
    }
}
