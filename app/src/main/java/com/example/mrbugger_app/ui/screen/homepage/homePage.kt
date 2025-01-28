package com.example.mrbugger_app.ui.screen.homepage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.mrbugger_app.Data.FoodItem
import com.example.mrbugger_app.R
import com.example.mrbugger_app.CommonSections.ScreenWithBottonNavBar
import com.example.mrbugger_app.model.Pictures
import com.example.mrbugger_app.ui.components.CategoryBar
import com.example.mrbugger_app.ui.components.LogoAndCard
import com.example.mrbugger_app.ui.components.PromoBanner
import com.example.mrbugger_app.ui.theme.PrimaryYellowDark
import com.example.mrbugger_app.ui.theme.PrimaryYellowLight
import com.example.mrbugger_app.ui.theme.Shapes


@Composable
fun homePage(navController: NavHostController) {
    var search by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        // Content Column
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(1.dp)
                .padding(bottom = 66.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
                    LogoAndCard()
                }
                item {
                    Searchbar(search = search, onSearchChange = { search = it })
                }

                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    CategoryBar()
                }
                item {
                    Spacer(modifier = Modifier.height(5.dp))
                    PopularBar(navController)
                }
                item {
                    Spacer(modifier = Modifier.height(5.dp))
                    PopularBurgerList(pictureList = FoodItem().loadPopularPictures(),navController = navController)
                }
                item {
                    Spacer(modifier = Modifier.height(5.dp))

                    PromoBanner()
                }
            }
        }
        //  Bottom Navigation Bar
        ScreenWithBottonNavBar(navController = navController)
    }
}
// Popular burger section
@Composable
fun  PopularBar(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Popular",
            fontSize = 20.sp,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.weight(1f))
        TextButton(
            onClick = { navController.navigate("menuPage") },
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Text(
                text = "Show more",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.inverseSurface,
                    fontWeight = FontWeight.Bold
                ),
                fontSize = 15.sp
            )
        }
    }
}

// Burger list
@Composable
fun PopularBurgerList(pictureList: List<Pictures>, navController: NavController) {
    LazyRow(modifier = Modifier) {
        items(pictureList) { picture ->
            PopularBurgerCard(picture = picture, modifier = Modifier.padding(0.dp), navController = navController)
        }
    }
}


// popular burger card
@Composable
fun PopularBurgerCard(picture: Pictures, modifier: Modifier = Modifier, navController: NavController) {
    Card(
        modifier = modifier
            .padding(horizontal = 12.dp)
            .width(180.dp)
            .shadow(
                elevation = 12.dp,
                shape = Shapes.medium,
                ambientColor = Color.Black,
                spotColor = Color.Black
            )
            .height(240.dp)
            .clickable {
                // Navigate to detailed product view, passing necessary parameters
                navController.navigate("detailedProductView/${picture.imageResourceId}/${picture.stringResourceId}/${picture.price}")
            },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            Image(
                painter = painterResource(picture.imageResourceId),
                contentDescription = stringResource(id = R.string.product_image),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(120.dp)
                    .width(190.dp)
            )
            Text(
                text = stringResource(id = picture.stringResourceId),
                fontSize = 18.sp,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(2.dp)
            )
            Text(
                text = "Rs ${stringResource(id = picture.price)}",
                fontSize = 18.sp,
                color = PrimaryYellowDark,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(7.dp)

            )
            Button(
                onClick = {
                    // Navigate to detailed product view when button is clicked
                    navController.navigate("detailedProductView/${picture.imageResourceId}/${picture.stringResourceId}/${picture.price}")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(text = "Buy", fontSize = 18.sp, color = Color.Black)
            }
        }
    }
}


@Composable
fun Searchbar(search: String, onSearchChange: (String) -> Unit) {
    com.example.mrbugger_app.ui.components.SearchBar(search = search, onSearchChange = onSearchChange)
}


