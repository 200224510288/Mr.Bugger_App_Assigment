package com.example.mrbugger_app.ui.screen.DetailedProductView

import androidx.benchmark.perfetto.Row
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Switch
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mrbugger_app.CommonSections.TopBarSection
import com.example.mrbugger_app.R
import com.example.mrbugger_app.ui.theme.BackgroundColor
import com.example.mrbugger_app.ui.theme.PrimaryYellowDark
import com.example.mrbugger_app.ui.theme.PrimaryYellowLight

@Composable
fun DetailedProductView(  navController: NavController,
                          imageResId: Int,
                          nameResId: Int,
                          priceResId: Int
) {
    var quantity by remember { mutableStateOf(1) }
    var isSpicy by remember { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        topBar = {
            TopBarSection(navController = navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)

        ) {

            Image(
                painter = painterResource(id = imageResId),
                contentDescription = stringResource(id = R.string.product_image),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
                elevation = CardDefaults.cardElevation(15.dp),
                colors = CardDefaults.cardColors(
                    containerColor = BackgroundColor
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            // Logo
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
                            Text(
                                text = stringResource(id = nameResId),
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        }
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.End
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.star),
                                    contentDescription = "Rating Star",
                                    tint = Color.Unspecified
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = "4.9", fontWeight = FontWeight.Bold, fontSize = 14.sp)

                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.nonveg),
                                    contentDescription = "non-veg",
                                    tint = Color.Unspecified
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Description
                    Text(
                        text = "Try a juicy, grilled beef patty nestled inside soft buns. Included fresh ripe tomatoes, crispy onions and a slice of cheddar cheese.",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Spicy Toggle and Portion Controls
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Spicy", fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Switch(checked = isSpicy, onCheckedChange = { isSpicy = it })
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = { if (quantity > 1) quantity-- }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.minus),
                                    contentDescription = "Minus"
                                )
                            }
                            Text(
                                text = "$quantity",
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.width(24.dp)
                            )
                            IconButton(onClick = { quantity++ }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.plus),
                                    contentDescription = "Plus"
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Price and Add to Cart Button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Rs.700", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Button(
                            onClick = { /* Handle Add to Cart */ },
                            colors = ButtonDefaults.buttonColors(Color.Gray),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "Add to Cart",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}