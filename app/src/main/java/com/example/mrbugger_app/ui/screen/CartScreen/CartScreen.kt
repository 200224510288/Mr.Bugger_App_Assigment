package com.example.mrbugger_app.ui.screen.CartScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.mrbugger_app.CommonSections.ScreenWithBottonNavBar
import com.example.mrbugger_app.CommonSections.TopBarSection
import com.example.mrbugger_app.CommonSections.cartBar
import com.example.mrbugger_app.R
import com.example.mrbugger_app.model.CartItem
import com.example.mrbugger_app.model.CartViewModel
import com.example.mrbugger_app.ui.theme.BackgroundColor
import com.example.mrbugger_app.ui.theme.PrimaryYellowDark
import com.example.mrbugger_app.ui.theme.TextColor
import java.text.DecimalFormat

@Composable
fun CartScreen(
    navController: NavHostController,
    cartViewModel: CartViewModel = viewModel(),
) {
    val cartItems = cartViewModel.cartItems
    val subTotal = cartViewModel.subTotal

    val decimalFormat = android.icu.text.DecimalFormat("#.##")
    val formattedSubTotal = decimalFormat.format(subTotal)
    val formattedShipping = decimalFormat.format(cartViewModel.deliveryCost)

    Scaffold(
        topBar = {
            TopBarSection(
                navController = navController,
            )


        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(1.dp)
                        .padding(bottom = 66.dp)
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                "Your Cart",
                                style = MaterialTheme.typography.headlineMedium,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            // CartItemList - Modularized Cart items display
                            CartItemList(cartItems = cartItems, cartViewModel = cartViewModel)
                        }

                        item {
                            Spacer(modifier = Modifier.height(36.dp))
                            Divider(modifier = Modifier.background(MaterialTheme.colorScheme.onBackground))
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        item {
                            Card(
                                shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
                                elevation = CardDefaults.cardElevation(15.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surface
                                ),
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 8.dp)
                                    .offset(y = 70.dp)
                                    .border(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                                    )

                            ) {


                                // Main calculation part for Delivery Charge and Sub Total
                                MainCalculation(formattedShipping = formattedShipping, formattedSubTotal = formattedSubTotal)
                                cartBar(navController = navController)
                                Spacer(modifier = Modifier.height(36.dp))

                            }
                        }

                    }
                }
            }

            // Bottom Navigation Bar
            ScreenWithBottonNavBar(navController = navController)

        }

    )
}


//calculation part
@Composable
fun MainCalculation(formattedShipping: String, formattedSubTotal: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)

    ) {

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Delivery Charge", style = MaterialTheme.typography.bodyLarge)
            Text("Rs.$formattedShipping", style = MaterialTheme.typography.bodyLarge)
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Discount", style = MaterialTheme.typography.bodyLarge)
            Text("Rs.40", style = MaterialTheme.typography.bodyLarge)
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Taxes", style = MaterialTheme.typography.bodyLarge)
            Text("Rs.150", style = MaterialTheme.typography.bodyLarge)
        }
        Spacer(modifier = Modifier.height(36.dp))

        Divider(modifier = Modifier.background(MaterialTheme.colorScheme.onSurfaceVariant))

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Sub Total",  fontSize = 19.sp, fontWeight = FontWeight.SemiBold)
            Text("Rs.$formattedSubTotal",  fontSize = 19.sp, fontWeight = FontWeight.SemiBold, color = PrimaryYellowDark)
        }
        Spacer(modifier = Modifier.height(16.dp))

        Divider(modifier = Modifier.background(MaterialTheme.colorScheme.background))
        Spacer(modifier = Modifier.height(36.dp))


    }
}


@Composable
fun CartItemList(
    cartItems: List<CartItem>,
    cartViewModel: CartViewModel
) {
    if (cartItems.isEmpty()) {
        // Display message when Cart is empty
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 22.dp, horizontal = 8.dp),


            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Your Cart is empty!",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
        }
    } else {
        cartItems.forEach { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 8.dp)
                    .height(100.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onBackground,
                        shape = RoundedCornerShape(12.dp)
                    ),
            ) {
                Spacer(modifier = Modifier.width(8.dp))

                Box(modifier = Modifier.size(60.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .align(alignment = Alignment.CenterVertically)
                    .background(MaterialTheme.colorScheme.surface)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(12.dp)
                    )){
                    Image(
                        painter = painterResource(id = item.imageRes),
                        contentDescription = item.name,
                        modifier = Modifier
                            .size(64.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f) .align(alignment = Alignment.CenterVertically)) {
                    Text(item.name, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Rs.${item.price}", style = MaterialTheme.typography.bodyMedium, color = PrimaryYellowDark)
                }
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier.padding(end = 4.dp)
                ) {
                    Spacer(modifier = Modifier.height(8.dp))
                    SmallCloseButton(
                        onClick = { cartViewModel.removeItem(item) } // Callback to remove the item
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("x ${item.quantity}", style = MaterialTheme.typography.bodyMedium, fontSize = 18.sp, modifier = Modifier.offset(x = -7.dp))

                }
            }
        }
    }
}



@Composable
fun SmallCloseButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(30.dp) // Outer box size for the red ring
            .clip(RoundedCornerShape(50))
            .background(MaterialTheme.colorScheme.onBackground),
        contentAlignment = Alignment.Center // Ensures the button is centered
    ) {
        Button(
            onClick = { onClick() },
            modifier = Modifier
                .size(25.dp) // Inner button size
                .clip(RoundedCornerShape(50))
                .offset(x = 0.dp, y = 0.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.background, // Background color for button
                contentColor = MaterialTheme.colorScheme.error // Text/Icon color
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 4.dp,
                pressedElevation = 6.dp
            ),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                modifier = Modifier
                    .padding(2.dp)
                    .align(Alignment.CenterVertically),
                text = "X",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}
