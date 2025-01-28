package com.example.mrbugger_app.ui.screen.MenuScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.mrbugger_app.Data.ChickenBurgerItems
import com.example.mrbugger_app.Data.FoodItem
import com.example.mrbugger_app.model.CartViewModel
import com.example.mrbugger_app.model.Pictures
import com.example.mrbugger_app.ui.screen.CartScreen.CartItemList
import com.example.mrbugger_app.ui.screen.CartScreen.MainCalculation
import com.example.mrbugger_app.ui.screen.homepage.Searchbar
import com.example.mrbugger_app.ui.theme.BackgroundColor
import com.example.mrbugger_app.ui.theme.PrimaryYellowDark

@Composable
fun MenuPage (navController: NavHostController,
                        cartViewModel: CartViewModel = viewModel(), )

{
    var search by remember { mutableStateOf("") }
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
                    .padding(paddingValues) // Padding for the content
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 66.dp)
                ) {
                    Spacer(modifier = Modifier.height(5.dp))
                    Searchbar(search = search, onSearchChange = { search = it })
                    Spacer(modifier = Modifier.height(5.dp))
                    ProductsGrid(pictureList = ChickenBurgerItems().loadChickenBurgers(), navController = navController)
                }
            }

            // Bottom Navigation Bar
            ScreenWithBottonNavBar(navController = navController)

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
                navController.navigate("detailedProductView/${picture.imageResourceId}/${picture.stringResourceId}/${picture.price}")
            }
            .padding(8.dp),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
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
            Text(
                text = stringResource(id = picture.stringResourceId),
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Rs. ${stringResource(id = picture.price)}",
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 15.sp),
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

@Composable
fun ProductsGrid(pictureList: List<Pictures>, navController: NavController){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
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