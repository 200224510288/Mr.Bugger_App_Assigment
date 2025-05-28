package com.example.mrbugger_app.ui.screen.OrderConfirmation

import android.content.Context
import android.location.Location
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationPickerDialog(
    isVisible: Boolean,
    currentLocation: Location?,
    onLocationSelected: (LatLng, String) -> Unit,
    onDismiss: () -> Unit,
    onGetCurrentLocation: () -> Unit
) {
    val context = LocalContext.current

    if (isVisible) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnBackPress = true,
                dismissOnClickOutside = false
            )
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface
            ) {
                LocationPickerContent(
                    currentLocation = currentLocation,
                    onLocationSelected = onLocationSelected,
                    onDismiss = onDismiss,
                    onGetCurrentLocation = onGetCurrentLocation,
                    context = context
                )
            }
        }
    }
}

@Composable
private fun LocationPickerContent(
    currentLocation: Location?,
    onLocationSelected: (LatLng, String) -> Unit,
    onDismiss: () -> Unit,
    onGetCurrentLocation: () -> Unit,
    context: Context
) {
    // Default location (Colombo, Sri Lanka)
    val defaultLocation = LatLng(6.9271, 79.8612)
    val initialLocation = currentLocation?.let {
        LatLng(it.latitude, it.longitude)
    } ?: defaultLocation

    var selectedLocation by remember { mutableStateOf(initialLocation) }
    var selectedAddress by remember { mutableStateOf("Selected Location") }
    var cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(initialLocation, 15f)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Header
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Select Delivery Location",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }

        // Map
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                onMapClick = { latLng ->
                    selectedLocation = latLng
                    // Get address for the selected location
                    getAddressFromLocation(
                        context = context,
                        latitude = latLng.latitude,
                        longitude = latLng.longitude
                    ) { address ->
                        selectedAddress = address
                    }
                }
            ) {
                // Marker at selected location
                Marker(
                    state = MarkerState(position = selectedLocation),
                    title = "Delivery Location"
                )
            }

            // Current location button
            FloatingActionButton(
                onClick = onGetCurrentLocation,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.MyLocation,
                    contentDescription = "Current Location",
                    tint = Color.White
                )
            }
        }

        // Address display and confirm button
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Selected Address:",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = selectedAddress,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Coordinates: ${String.format("%.4f", selectedLocation.latitude)}, ${String.format("%.4f", selectedLocation.longitude)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancel")
                    }

                    Button(
                        onClick = {
                            onLocationSelected(selectedLocation, selectedAddress)
                            onDismiss()
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(
                            text = "Confirm Location",
                            color = Color.White
                        )
                    }
                }
            }
        }
    }

    // Update camera when current location changes
    LaunchedEffect(currentLocation) {
        currentLocation?.let { location ->
            val newLatLng = LatLng(location.latitude, location.longitude)
            selectedLocation = newLatLng
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngZoom(newLatLng, 15f),
                1000
            )
        }
    }
}