package com.example.mrbugger_app.ui.screen.DetailedProductView

import android.widget.Toast
import androidx.benchmark.perfetto.Row
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.example.mrbugger_app.CommonSections.PopularCategories
import com.example.mrbugger_app.CommonSections.TopBarSection
import com.example.mrbugger_app.Data.Category
import com.example.mrbugger_app.R
import com.example.mrbugger_app.model.CartItem
import com.example.mrbugger_app.model.CartViewModel
import com.example.mrbugger_app.ui.theme.BackgroundColor
import com.example.mrbugger_app.ui.theme.PrimaryYellowDark
import com.example.mrbugger_app.ui.theme.PrimaryYellowLight
import com.example.mrbugger_app.ui.theme.TextColor


//detailed view page
@Composable
fun DetailedProductView(
    navController: NavController,
    cartViewModel: CartViewModel,
    imageResId: Int,
    imageResId2: Int,
    nameResId: Int,
    descResId: Int,
    priceResId: Int
) {
    val context = LocalContext.current
    val category = Category()
    var quantity by remember { mutableStateOf(1) }
    var isSpicy by remember { mutableStateOf(false) }
    val basePrice = stringResource(id = priceResId).toFloat()
    var currentPrice by remember { mutableStateOf(basePrice) }

    val name = stringResource(id = nameResId)
    val description = stringResource(id = descResId)
    val price = stringResource(id = priceResId).toDouble()



    // Update current price when quantity changes
    val updatePrice = { newQuantity: Int ->
        currentPrice = basePrice * newQuantity
        quantity = newQuantity
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopBarSection(navController = navController, cartViewModel = cartViewModel)

        },
                bottomBar = {
                    Spacer(modifier = Modifier.height(8.dp))
                    // Fixed bottom bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    // .shadow(elevation = 8.dp, shape = RoundedCornerShape(topStart = 16.dp))
                    .padding(16.dp)
                    .height(85.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
            // food price section
                Box(
                    modifier = Modifier
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(30.dp)
                        )
                        .width(175.dp)
                        .height(50.dp)
                        .padding(8.dp)
                ) {

                    Text(
                        text = "Rs. ${"%.2f".format(currentPrice)}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                //add to cart button
                Button(
                    onClick = { val cartItem = CartItem(
                        imageRes = imageResId,
                        name = name,
                        price = price,
                        quantity = quantity
                    )
                        Toast.makeText(context,
                            context.getString(R.string.item_added_to_cart), Toast.LENGTH_SHORT).show()
                        cartViewModel.addItemToCart(cartItem)},

                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(25.dp),
                    modifier = Modifier
                        .width(175.dp)
                        .height(50.dp)
                ) {
                    Text(
                        text = "Add to Cart",
                        color = MaterialTheme.colorScheme.background,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
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
                    containerColor = MaterialTheme.colorScheme.surface
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
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.offset(y = (-4).dp)
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
                                    painter = painterResource(id = imageResId2),
                                    contentDescription = "non-veg",
                                    tint = Color.Unspecified
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(15.dp))
                //food discription section
                    Box(
                        modifier = Modifier
                            .border(
                                1.dp,
                                MaterialTheme.colorScheme.onBackground,
                                shape = RoundedCornerShape(13.dp)
                            )
                            .padding(10.dp)
                    ) {

                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "Description",
                                fontWeight = FontWeight.Medium,
                                fontSize = 20.sp
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(
                                text = stringResource(id = descResId),
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    // Spicy Toggle and Portion Controls
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Spacer(modifier = Modifier.width(15.dp))
                            Text(text = "Spicy", fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Switch(checked = isSpicy, onCheckedChange = { isSpicy = it })
                        }
                        //increasing the porting section
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Button(
                                onClick = { if (quantity > 1) updatePrice(quantity - 1) },
                                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                                modifier = Modifier.padding(4.dp),
                                contentPadding = PaddingValues(4.dp),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = "-",
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Center
                                )
                            }
                            Text(
                                text = "$quantity",
                                fontSize = 25.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.width(30.dp)
                            )
                            Button(
                                onClick =  { updatePrice(quantity + 1) },
                                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                                modifier = Modifier.padding(4.dp),
                                contentPadding = PaddingValues(4.dp),
                                shape = RoundedCornerShape(12.dp)  // Add rounded corners with a radius of 12.dp
                            ) {
                                Text(
                                    text = "+",
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                    //popular food category section
                    Spacer(modifier = Modifier.height(10.dp))
                    PopularCategories(category.loadCategory())
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}

