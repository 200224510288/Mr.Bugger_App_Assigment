package com.example.mrbugger_app.CommonSections

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mrbugger_app.BottomNav.BottomNavDesign
import com.example.mrbugger_app.R
import com.example.mrbugger_app.Screen
import com.example.mrbugger_app.TopNav.TopBar
import com.example.mrbugger_app.model.CartItem
import com.example.mrbugger_app.model.CartViewModel
import com.example.mrbugger_app.ui.theme.ExtraYellowLight
import java.text.DecimalFormat
import android.Manifest
import android.util.Log
import kotlin.collections.joinToString

// Add bottom bar fixed into bottom
@Composable
fun ScreenWithBottonNavBar(navController: NavController, cartViewModel: CartViewModel) {

    Column(modifier = Modifier.fillMaxSize()) {
        // Main Content
        Box(
            modifier = Modifier
                .weight(1f)
                .background(MaterialTheme.colorScheme.tertiary)
        ) {}
        // Bottom Navigation Bar
        BottomNavSection(navController = navController, cartViewModel = cartViewModel)
    }
}

// Add bottom bar wrap into box
@Composable
fun BottomNavSection(navController: NavController, cartViewModel: CartViewModel) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 8.dp)
            .height(80.dp),
        contentAlignment = Alignment.Center
    ) {
        BottomNavDesign(modifier = Modifier, navController = navController, cartViewModel = cartViewModel)
    }
}

// bottom section of the detailed view page
@Composable
fun cartBar(
    navController: NavController,
    cartViewModel: CartViewModel,
    onPermission: Boolean,
    context: Context
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 1.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Back Button
        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(35),
            modifier = Modifier
                .padding(12.dp)
                .height(50.dp)
                .width(170.dp),
            elevation = ButtonDefaults.buttonElevation(2.dp)
        ) {
            Text(
                "Back to Menu",
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        // Confirm Order Button
        Button(
            onClick = {
                if (isNetworkAvailable(context)) {
                    val success = cartViewModel.placeOrder()
                    if (success) {
                        if (onPermission && hasNotificationPermission(context)) {
                            showNotification(
                                context = context,
                                cartItems = cartViewModel.cartItems,
                                subTotal = cartViewModel.subTotal
                            )
                        }
                        navController.navigate(Screen.OrderConfirmation.route)
                    }
                } else {
                    Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(35),
            modifier = Modifier
                .padding(12.dp)
                .height(50.dp)
                .width(160.dp),
            elevation = ButtonDefaults.buttonElevation(2.dp)
        ) {
            Text(
                "Confirm Order",
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }

    Spacer(modifier = Modifier.height(26.dp))
}


fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}

// Top bar with cart icon and back icon
@Composable
fun TopBarSection(navController: NavController, cartViewModel: CartViewModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        TopBar(navController = navController, cartViewModel = cartViewModel)
    }
}


fun showNotification(context: Context, cartItems: List<CartItem>, subTotal: Double) {
    if (!hasNotificationPermission(context)) {
        Toast.makeText(context, "Notification permission not granted", Toast.LENGTH_SHORT).show()
        return
    }

    val channelId = "order_confirmation_channel"
    val notificationId = 1

    // Create notification channel (for Android O+)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            channelId,
            "Order Confirmation",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Notifications for order confirmations"
        }

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    // Build cart item details
    val itemsText = cartItems.joinToString("\n") { item ->
        "${item.name} (x${item.quantity}): Rs.${item.price * item.quantity}"
    }
    val formattedTotal = DecimalFormat("#.##").format(subTotal)

    val notificationText = """
        Your order has been confirmed!
        Items:
        $itemsText
        Total: Rs.$formattedTotal
    """.trimIndent()

    // Build and show notification
    try {
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.logo2)
            .setContentTitle("Order Confirmed")
            .setContentText("Your order has been placed successfully!")
            .setStyle(NotificationCompat.BigTextStyle().bigText(notificationText))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context).notify(notificationId, notification)

    } catch (e: SecurityException) {
        Log.e("NotificationError", "Permission issue: ${e.message}")
        Toast.makeText(context, "Unable to show notification", Toast.LENGTH_SHORT).show()
    }
}


fun hasNotificationPermission(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    } else {
        true // Notification permission is auto-granted below Android 13
    }
}