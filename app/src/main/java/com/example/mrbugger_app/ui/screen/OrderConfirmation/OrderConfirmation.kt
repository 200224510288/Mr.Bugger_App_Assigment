package com.example.mrbugger_app.ui.screen.OrderConfirmation

import android.content.res.Configuration
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mrbugger_app.CommonSections.ScreenWithBottonNavBar
import com.example.mrbugger_app.CommonSections.TopBarSection
import com.example.mrbugger_app.R
import com.example.mrbugger_app.Screen
import com.example.mrbugger_app.model.CartViewModel
import com.example.mrbugger_app.model.UserProfileViewModel
import com.example.mrbugger_app.ui.screen.ProfileScreen.UserInfoField
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderConfirmation(navController: NavController,cartViewModel: CartViewModel) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopBarSection(navController = navController)
        },

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
                        color = Color.LightGray.copy(alpha = 1f), // transparent white
                        shape = RoundedCornerShape(24.dp) // Rounded corners
                    )
                    .shadow(elevation = 8.dp, RoundedCornerShape(24.dp))
                    .clip(RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.Center
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.background,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(12.dp)
                ) {
                    Text(
                        text = AnnotatedString.Builder().apply {
                            // Style for "Mr."
                            pushStyle(
                                SpanStyle(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontFamily = MaterialTheme.typography.displayLarge.fontFamily,
                                    fontWeight = MaterialTheme.typography.displayLarge.fontWeight,
                                    fontSize = 14.sp
                                )
                            )
                            append("Mr.")
                            pop()

                            // Style for "Burger"
                            pushStyle(
                                SpanStyle(
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                                    fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
                                    fontSize = 14.sp
                                )
                            )
                            append("Burger")
                            pop()
                        }.toAnnotatedString(),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    OrderPlacedCard()

                    Spacer(modifier = Modifier.height(30.dp))

                }

            }

            Spacer(modifier = Modifier.height(16.dp))

            // Row with menu icon outside the button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { navController.navigate(Screen.Menu.route)},
                    modifier = Modifier
                        .width(150.dp),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                ) {
                    Text(text = "Menu", fontSize = 20.sp, color = MaterialTheme.colorScheme.background)
                }

                Spacer(modifier = Modifier.width(40.dp))

                Button(
                    onClick = { isSheetOpen = true },
                    modifier = Modifier.width(150.dp),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                ) {
                    Text(text = "View Order", fontSize = 20.sp, color = MaterialTheme.colorScheme.background)
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
    }
}

@Composable
fun OrderDetailsContent(cartViewModel: CartViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Order Details", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        Divider()
        Spacer(modifier = Modifier.height(8.dp))

        // Display each item in the cart
        cartViewModel.cartItems.forEach { item ->
            Text(text = "${item.name}: ${item.quantity}", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Display total price
        Text(text = "Total Price: Rs. ${cartViewModel.subTotal}", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Add order tracking logic here */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
        ) {
            Text(text = "Track Order", fontSize = 18.sp, color = Color.White)
        }
    }
}

@Composable
fun OrderPlacedCard() {
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
                contentDescription = "Order Placed",
                tint = Color(0xFF00C853), // Green color
                modifier = Modifier.size(110.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Order Placed Text
            Text(
                text = "Order has been placed",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Subtext
            Text(
                text = "We have received your order!",
                fontSize = 16.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Divider()
        }
    }
}
