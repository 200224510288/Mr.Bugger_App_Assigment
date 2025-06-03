package com.example.mrbugger_app.ui.screen.OrderConfirmation

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.location.Location
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.mrbugger_app.CommonSections.TopBarSection
import com.example.mrbugger_app.R
import com.example.mrbugger_app.Screen
import com.example.mrbugger_app.model.CartViewModel
import android.location.Geocoder
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import java.io.IOException
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderConfirmation(navController: NavController, cartViewModel: CartViewModel) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val coroutineScope = rememberCoroutineScope()

    // Location states
    var userLocation by remember { mutableStateOf<Location?>(null) }
    var locationAddress by remember { mutableStateOf("Getting your location...") }
    var locationPermissionGranted by remember { mutableStateOf(false) }
    var isLoadingLocation by remember { mutableStateOf(false) }

    var showLocationPicker by remember { mutableStateOf(false) }
    var selectedLatLng by remember { mutableStateOf<LatLng?>(null) }
    var isUsingCustomLocation by remember { mutableStateOf(false) }

    @Composable
    fun UserLocationCard(
        locationAddress: String,
        isLoadingLocation: Boolean,
        locationPermissionGranted: Boolean,
        userLocation: Location?,
        isUsingCustomLocation: Boolean,
        onRequestLocation: () -> Unit,
        onChooseLocation: () -> Unit
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Delivery Location",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    // Location type indicator
                    if (isUsingCustomLocation) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "(Custom)",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                when {
                    !locationPermissionGranted -> {
                        Text(
                            text = "Location permission needed for delivery",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedButton(
                                onClick = onRequestLocation,
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.LocationOn,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Enable Location")
                            }
                            Button(
                                onClick = onChooseLocation,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Choose on Map")
                            }
                        }
                    }
                    isLoadingLocation -> {
                        Text(
                            text = "Getting your location...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    userLocation != null || selectedLatLng != null -> {
                        Text(
                            text = locationAddress,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        val displayLatLng = selectedLatLng ?: userLocation?.let {
                            LatLng(it.latitude, it.longitude)
                        }

                        displayLatLng?.let { latLng ->
                            Text(
                                text = "Coordinates: ${String.format("%.4f", latLng.latitude)}, ${String.format("%.4f", latLng.longitude)}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Buttons to change location
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            if (!isUsingCustomLocation) {
                                OutlinedButton(
                                    onClick = onChooseLocation,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Choose Different Location")
                                }
                            } else {
                                OutlinedButton(
                                    onClick = onRequestLocation,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Use Current Location")
                                }
                                OutlinedButton(
                                    onClick = onChooseLocation,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Change Location")
                                }
                            }
                        }
                    }
                    else -> {
                        Text(
                            text = "Unable to get location",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedButton(
                                onClick = onRequestLocation,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Retry Location")
                            }
                            Button(
                                onClick = onChooseLocation,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Choose on Map")
                            }
                        }
                    }
                }
            }
        }
    }

    fun getCurrentLocation(
        context: Context,
        onLocationReceived: (Location?) -> Unit
    ) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        // Check permissions
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            onLocationReceived(null)
            return
        }

        val cancellationToken = CancellationTokenSource()

        fusedLocationClient
            .getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                cancellationToken.token
            )
            .addOnSuccessListener { location ->
                onLocationReceived(location)
            }
            .addOnFailureListener {
                onLocationReceived(null)
            }
    }

    fun getAddressFromLocation(
        context: Context,
        latitude: Double,
        longitude: Double,
        onAddressReceived: (String) -> Unit
    ) {
        try {
            val geocoder = Geocoder(context, Locale.getDefault())

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                // For Android 13+ (API 33+)
                geocoder.getFromLocation(latitude, longitude, 1) { addresses ->
                    if (addresses.isNotEmpty()) {
                        val address = addresses[0]
                        val addressText = buildString {
                            address.getAddressLine(0)?.let { append(it) }
                        }
                        onAddressReceived(addressText.ifEmpty { "Location found" })
                    } else {
                        onAddressReceived("Lat: ${String.format("%.4f", latitude)}, Lng: ${String.format("%.4f", longitude)}")
                    }
                }
            } else {
                // For older Android versions
                @Suppress("DEPRECATION")
                val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                if (!addresses.isNullOrEmpty()) {
                    val address = addresses[0]
                    val addressText = address.getAddressLine(0) ?: "Location found"
                    onAddressReceived(addressText)
                } else {
                    onAddressReceived("Lat: ${String.format("%.4f", latitude)}, Lng: ${String.format("%.4f", longitude)}")
                }
            }
        } catch (e: IOException) {
            onAddressReceived("Lat: ${String.format("%.4f", latitude)}, Lng: ${String.format("%.4f", longitude)}")
        } catch (e: Exception) {
            onAddressReceived("Unable to get address")
        }
    }

    // Permission launcher
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        locationPermissionGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        if (locationPermissionGranted) {
            isLoadingLocation = true
            getCurrentLocation(context) { location ->
                userLocation = location
                location?.let {
                    getAddressFromLocation(context, it.latitude, it.longitude) { address ->
                        locationAddress = address
                        isLoadingLocation = false
                    }
                } ?: run {
                    locationAddress = "Unable to get location"
                    isLoadingLocation = false
                }
            }
        }
    }

    // Check permissions on launch
    LaunchedEffect(Unit) {
        val hasLocationPermission = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (hasLocationPermission) {
            locationPermissionGranted = true
            isLoadingLocation = true
            getCurrentLocation(context) { location ->
                userLocation = location
                location?.let {
                    getAddressFromLocation(context, it.latitude, it.longitude) { address ->
                        locationAddress = address
                        isLoadingLocation = false
                    }
                } ?: run {
                    locationAddress = "Unable to get location"
                    isLoadingLocation = false
                }
            }
        }
    }

    // Bottom sheet state
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopBarSection(navController = navController, cartViewModel = cartViewModel)
        },
        floatingActionButton = {
            QuickCallFAB(
                merchantPhone = "+94741248950",
                context = context,
                modifier = Modifier.offset(y = (-80).dp)
            )
        }
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(110.dp))
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = if (isLandscape) 220.dp else 11.dp,
                        vertical = if (isLandscape) 15.dp else 10.dp
                    )
                    .padding(top = if (isLandscape) 2.dp else 10.dp)
                    .padding(bottom = if (isLandscape) 2.dp else 20.dp)
                    .background(
                        color = Color.LightGray.copy(alpha = 1f),
                        shape = RoundedCornerShape(24.dp)
                    )
                    .shadow(elevation = 8.dp, RoundedCornerShape(24.dp))
                    .clip(RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(12.dp)
                ) {
                    Text(
                        text = AnnotatedString.Builder().apply {
                            pushStyle(
                                SpanStyle(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontFamily = MaterialTheme.typography.displayLarge.fontFamily,
                                    fontWeight = MaterialTheme.typography.titleSmall.fontWeight,
                                )
                            )
                            append("Mr.")
                            pop()
                            pushStyle(
                                SpanStyle(
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                                    fontFamily = MaterialTheme.typography.titleSmall.fontFamily,
                                )
                            )
                            append("Burger")
                            pop()
                        }.toAnnotatedString(),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    OrderPlacedCard()

                    Spacer(modifier = Modifier.height(20.dp))

                    // Location display card
                    UserLocationCard(
                        locationAddress = locationAddress,
                        isLoadingLocation = isLoadingLocation,
                        locationPermissionGranted = locationPermissionGranted,
                        userLocation = userLocation,
                        isUsingCustomLocation = isUsingCustomLocation,
                        onRequestLocation = {
                            isUsingCustomLocation = false
                            selectedLatLng = null
                            locationPermissionLauncher.launch(
                                arrayOf(
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                )
                            )
                        },
                        onChooseLocation = {
                            showLocationPicker = true
                        }
                    )

                    Spacer(modifier = Modifier.height(30.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Main action buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { navController.navigate(Screen.Menu.route) },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .padding(horizontal = 4.dp),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        text = stringResource(R.string.menu),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Button(
                    onClick = { isSheetOpen = true },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .padding(horizontal = 4.dp),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        text = stringResource(R.string.view_order),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            Spacer(modifier = Modifier.height(65.dp))
        }

        // Bottom Sheet for Order Details
        if (isSheetOpen) {
            ModalBottomSheet(
                onDismissRequest = { isSheetOpen = false },
                sheetState = sheetState
            ) {
                OrderDetailsContent(cartViewModel = cartViewModel)
            }
        }

        // Location Picker Dialog
        LocationPickerDialog(
            isVisible = showLocationPicker,
            currentLocation = userLocation,
            onLocationSelected = { latLng, address ->
                selectedLatLng = latLng
                locationAddress = address
                isUsingCustomLocation = true
            },
            onDismiss = {
                showLocationPicker = false
            },
            onGetCurrentLocation = {
                // Request current location when user clicks current location button in map
                if (locationPermissionGranted) {
                    isLoadingLocation = true
                    getCurrentLocation(context) { location ->
                        userLocation = location
                        location?.let {
                            getAddressFromLocation(context, it.latitude, it.longitude) { address ->
                                locationAddress = address
                                isLoadingLocation = false
                            }
                        } ?: run {
                            locationAddress = "Unable to get location"
                            isLoadingLocation = false
                        }
                    }
                } else {
                    locationPermissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                }
            }
        )
    }
}

//  Call Merchant Button Component
@Composable
fun CallMerchantButton(
    merchantPhone: String,
    context: Context,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = {
            try {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:$merchantPhone")
                }
                context.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        },
        modifier = modifier,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
            contentColor = Color(0xFF00C853)
        ),
        border = androidx.compose.foundation.BorderStroke(
            2.dp,
            Color(0xFF00C853)
        )
    ) {
        Icon(
            imageVector = Icons.Default.Phone,
            contentDescription = "Call Merchant",
            modifier = Modifier.size(20.dp),
            tint = Color(0xFF00C853)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Call Merchant",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold
        )
    }
}

// Quick Call Floating Action Button
@Composable
fun QuickCallFAB(
    merchantPhone: String,
    context: Context,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = {
            try {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:$merchantPhone")
                }
                context.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        },
        modifier = modifier,
        containerColor = Color(0xFF00C853),
        contentColor = Color.White
    ) {
        Icon(
            imageVector = Icons.Default.Phone,
            contentDescription = "Call Merchant",
            modifier = Modifier.size(24.dp)
        )
    }
}

// bottom bar part
@Composable
fun OrderDetailsContent(cartViewModel: CartViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = stringResource(R.string.order_details), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
        Divider()
        Spacer(modifier = Modifier.height(8.dp))

        // Display each item in the cart
        cartViewModel.cartItems.forEach { item ->
            Text(text = "${item.name}: ${item.quantity}", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Display total price
        Text(text = "Total Price: Rs. ${cartViewModel.subTotal}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Add order tracking logic here */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
        ) {
            Text(text = stringResource(R.string.track_order), style = MaterialTheme.typography.titleMedium, color = Color.White)
        }
    }
}

// Order Placed Card with Call Merchant Button
@Composable
fun OrderPlacedCard() {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Green Checkmark Icon
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = stringResource(R.string.order_placed),
                tint = Color(0xFF00C853),
                modifier = Modifier.size(110.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Order Placed Text
            Text(
                text = stringResource(R.string.order_has_been_placed),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Subtext
            Text(
                text = stringResource(R.string.we_have_received_your_order),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.outline,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Call Merchant Button - Prominently placed after order confirmation
            CallMerchantButton(
                merchantPhone = "+94741248950",
                context = context,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(48.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Helper text
            Text(
                text = "Need help with your order? Call us directly!",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))
            Divider()
        }
    }
}