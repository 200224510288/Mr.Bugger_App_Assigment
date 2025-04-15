package com.example.mrbugger_app.ui.screen.MenuScreen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
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
import com.example.mrbugger_app.Data.BurgerItems
import com.example.mrbugger_app.Data.FoodItem
import com.example.mrbugger_app.model.CartViewModel
import com.example.mrbugger_app.model.Pictures
import com.example.mrbugger_app.ui.components.ShimmerListItem
import com.example.mrbugger_app.ui.screen.CartScreen.CartItemList
import com.example.mrbugger_app.ui.screen.CartScreen.MainCalculation
import com.example.mrbugger_app.ui.screen.homepage.Searchbar
import com.example.mrbugger_app.ui.theme.BackgroundColor
import com.example.mrbugger_app.ui.theme.PrimaryYellowDark
import com.example.mrbugger_app.ui.theme.Shapes

@Composable
fun MenuPage(navController: NavHostController, cartViewModel: CartViewModel = viewModel()) {
    var search by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) } // Loading state
    val cartViewModel: CartViewModel = viewModel()

    // Get screen orientation
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE


    // Simulating data loading
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(2000) // Simulate network delay
        isLoading = false
    }

    Scaffold(
        topBar = {
            TopBarSection(navController = navController, cartViewModel = cartViewModel)
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (isLandscape) {
                    // Landscape Layout
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 66.dp)
                    ) {
                        Searchbar(search = search, onSearchChange = { search = it })
                        Spacer(modifier = Modifier.height(5.dp))

                        ShimmerListItem(
                            isLoading = isLoading,
                            contentAfterLoading = {
                                ProductsGrid(
                                    pictureList = BurgerItems().loadBurgers(),
                                    navController = navController,
                                    columns = 4 // Default columns in portrait mode
                                )
                            }
                        )
                    }
                } else {
                    // Portrait Layout
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 66.dp)
                    ) {
                        Searchbar(search = search, onSearchChange = { search = it })
                        Spacer(modifier = Modifier.height(5.dp))

                        ShimmerListItem(
                            isLoading = isLoading,
                            contentAfterLoading = {
                                ProductsGrid(
                                    pictureList = BurgerItems().loadBurgers(),
                                    navController = navController,
                                    columns = 2 // Default columns in portrait mode
                                )
                            }
                        )
                    }
                }
            }

            // Bottom Navigation Bar
            ScreenWithBottonNavBar(navController = navController, cartViewModel = cartViewModel)
        }
    )
}

@Composable
fun ProductCards(picture: Pictures, modifier: Modifier = Modifier, navController: NavController)
{
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                //passing fooditem arguments
                navController.navigate("detailedProductView/${picture.imageResourceId}/${picture.imageResourceId2}/${picture.stringResourceId}/${picture.stringResourceId2}/${picture.price}")
            }
            .padding(8.dp),
        shape = Shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(4.dp)
        ) {
            Image(
                painter = painterResource(id = picture.imageResourceId),
                contentDescription = stringResource(id = picture.stringResourceId),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            //food item name
            Text(
                text = stringResource(id = picture.stringResourceId),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(4.dp))
            //price
            Text(
                text = "Rs. ${stringResource(id = picture.price)}",
                style = MaterialTheme.typography.bodyMedium,
                color = PrimaryYellowDark
            )
            Spacer(modifier = Modifier.height(4.dp))
            Button(
                onClick = {    navController.navigate("detailedProductView/${picture.imageResourceId}/${picture.stringResourceId}/${picture.price}")},
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(17.dp)

            ) {
                Text(text = "Buy", color = MaterialTheme.colorScheme.background, fontWeight = FontWeight.SemiBold)
            }
        }
    }

}

//load the product cards with picture list.
@Composable
fun ProductsGrid(pictureList: List<Pictures>, navController: NavController, columns: Int) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(pictureList) { picture ->
            ProductCards(picture = picture, modifier = Modifier.padding(10.dp), navController = navController)
        }
    }
}