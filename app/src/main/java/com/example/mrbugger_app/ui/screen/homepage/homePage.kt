package com.example.mrbugger_app.ui.screen.homepage

import android.content.res.Configuration
import androidx.compose.animation.Crossfade
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.mrbugger_app.AuthViewModel.AuthState
import com.example.mrbugger_app.AuthViewModel.AuthViewModel
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
import kotlinx.coroutines.delay


@Composable
fun homePage(navController: NavHostController,authViewModel: AuthViewModel) {
    var search by remember { mutableStateOf("") }
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val coroutineScope = rememberCoroutineScope()

    val authState = authViewModel.authState.observeAsState()

    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.unauthenticated -> navController.navigate("login")
            else -> Unit

        }

    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Content Column
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(bottom = 66.dp)
                .padding(
                    horizontal = if (isLandscape) 50.dp else 2.dp,
                    vertical = if (isLandscape) 15.dp else 10.dp
                )
                .padding(top = if (isLandscape) 2.dp else 5.dp)
                .padding(bottom = if (isLandscape) 2.dp else 20.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(6.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                //top bar
                item {
                    Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
                    LogoAndCard()
                }
                //search bar
                item {
                    Searchbar(search = search, onSearchChange = { search = it })
                }
               //small chips for the navigation
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    CategoryBar()
                }
                //Section headers
                item {
                    Spacer(modifier = Modifier.height(5.dp))
                    SectionHeader(title = stringResource(R.string.popular), link = "menuPage", navController = navController)
                }
                //popular item cards
                item {
                    Spacer(modifier = Modifier.height(5.dp))
                    PopularBurgerList(pictureList = FoodItem().loadPopularPictures(),navController = navController)
                }
                //promotion section
                item {
                    Spacer(modifier = Modifier.height(5.dp))
                    SectionHeader(title = stringResource(R.string.exclusive_promotions), link = "menuPage", navController = navController)
                }
                item {
                    Spacer(modifier = Modifier.height(5.dp))

                    PromoBanner()
                }
                //upcomeing events
                item {
                    Spacer(modifier = Modifier.height(5.dp))
                    SectionHeader(title = stringResource(R.string.future_deals), link = "menuPage", navController = navController)
                }

                item {
                    Spacer(modifier = Modifier.height(5.dp))

                    Slideshow()
                }
            }
        }
        //  Bottom Navigation Bar
        ScreenWithBottonNavBar(navController = navController)
    }
}

// Section bar
@Composable
fun SectionHeader(
    title: String,
    link: String,
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 9.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold
            )
        )
        Spacer(modifier = Modifier.weight(1f))
        TextButton(
            onClick = { navController.navigate(link) },
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Text(
                text = stringResource(R.string.show_more),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.inverseSurface,
                    fontWeight = FontWeight.Bold
                ),
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
            .padding(horizontal = 7.dp)
            .width(170.dp)
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
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(2.dp)
            )
            Text(
                text = "Rs ${stringResource(id = picture.price)}",
                color = PrimaryYellowDark,
                style = MaterialTheme.typography.titleMedium,
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
                Text(text = "Buy", style = MaterialTheme.typography.titleMedium, color = Color.Black)
            }
        }
    }
}

//search bar
@Composable
fun Searchbar(search: String, onSearchChange: (String) -> Unit) {
    com.example.mrbugger_app.ui.components.SearchBar(search = search, onSearchChange = onSearchChange)
}


//Slideshow Composable
@Composable
fun Slideshow(slideInterval: Long = 3000) {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    val imageHeight = if (isPortrait) 200.dp else 350.dp

    val imageResources = listOf(
        R.drawable.slide3,
        R.drawable.slide2,
        R.drawable.slide1
    )

    var currentIndex by remember { mutableStateOf(0) }

    // Update the current index after every slideInterval duration
    LaunchedEffect(currentIndex) {
        while (true) {
            delay(slideInterval)
            currentIndex = (currentIndex + 1) % imageResources.size
        }
    }

    // Crossfade for smooth transitions
    Crossfade(targetState = currentIndex, modifier = Modifier.fillMaxWidth()) { index ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(imageHeight)
                .padding(horizontal = 8.dp)
                .clip(Shapes.medium) // Added border radius
        ) {
            Image(
                painter = painterResource(id = imageResources[index]),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
