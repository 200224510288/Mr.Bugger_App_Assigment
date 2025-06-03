package com.example.mrbugger_app.ui.screen.SearchScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.mrbugger_app.CommonSections.BurgerCard
import com.example.mrbugger_app.CommonSections.PopularCategories
import com.example.mrbugger_app.CommonSections.ScreenWithBottonNavBar
import com.example.mrbugger_app.CommonSections.SectionsText
import com.example.mrbugger_app.CommonSections.TopBarSection
import com.example.mrbugger_app.Data.BeverageData
import com.example.mrbugger_app.Data.BurgerItems
import com.example.mrbugger_app.Data.Category
import com.example.mrbugger_app.Data.FastFoodData
import com.example.mrbugger_app.R
import com.example.mrbugger_app.model.CartViewModel
import com.example.mrbugger_app.model.CategoryPictuers
import com.example.mrbugger_app.model.Meal
import com.example.mrbugger_app.model.Pictures
import com.example.mrbugger_app.model.RecipeViewModel
import com.example.mrbugger_app.ui.components.SearchBarForSearchScreen
import com.example.mrbugger_app.ui.theme.BackgroundColor
import com.example.mrbugger_app.ui.theme.PrimaryYellowLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    cartViewModel: CartViewModel,
    recipeViewModel: RecipeViewModel = viewModel()
) {
    val uiState by recipeViewModel.uiState.collectAsStateWithLifecycle()
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var selectedMeal by remember { mutableStateOf<Meal?>(null) }
    var showRecipeDialog by remember { mutableStateOf(false) }

    // State to track if we should show search results or default layout
    val isSearchActive = searchQuery.isNotBlank()

    Box(modifier = Modifier.fillMaxSize()) {
        // Main content with layout structure
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(bottom = 1.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(10.dp),
                verticalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                // Cart and back button (always visible)
                item {
                    TopBarSection(navController = navController, cartViewModel = cartViewModel)
                }

                // Search bar (always visible)
                item {
                    SearchBarForSearchScreen(
                        search = searchQuery,
                        onSearchChange = {
                            searchQuery = it
                            if (it.isNotBlank()) {
                                recipeViewModel.searchMeals(it)
                            }
                        }
                    )
                }

                // Conditional content based on search state
                if (isSearchActive) {
                    // Search Results Content
                    when {
                        uiState.isLoading -> {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(400.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.size(48.dp),
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        Spacer(modifier = Modifier.height(16.dp))
                                        Text(
                                            text = "Searching recipes...",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }

                        uiState.isOffline -> {
                            item {
                                OfflineErrorCard(
                                    searchQuery = searchQuery,
                                    onRetry = { recipeViewModel.retryLastSearch() }
                                )
                            }
                        }

                        uiState.error != null -> {
                            item {
                                GenericErrorCard(
                                    error = uiState.error!!,
                                    onRetry = { recipeViewModel.retryLastSearch() }
                                )
                            }
                        }

                        uiState.meals.isEmpty() -> {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(400.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("No recipes found for '$searchQuery'")
                                }
                            }
                        }

                        else -> {
                            items(uiState.meals) { meal ->
                                RecipeCard(
                                    meal = meal,
                                    onClick = {
                                        selectedMeal = meal
                                        showRecipeDialog = true
                                    }
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                            }
                        }
                    }
                } else {
                    // Default content (when not searching)
                    // Popular Categories
                    item {
                        val category = Category
                        //   PopularCategories(category.loadCategory())
                    }

                    // Popular Burgers Section
                    item {
                        Spacer(modifier = Modifier.height(5.dp))
                        SectionsText(
                            title = stringResource(R.string.popular_burgers),
                            onShowMoreClick = { navController.navigate("menuPage") }
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(5.dp))
                        BurgerList(
                            chickenBugerList = BurgerItems().loadBurgers(),
                            navController = navController
                        )
                    }

                    // Popular Fast Foods Section
                    item {
                        Spacer(modifier = Modifier.height(5.dp))
                        SectionsText(
                            title = stringResource(R.string.popular_fast_foods),
                            onShowMoreClick = { navController.navigate("fastFoodsMenu") }
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(5.dp))
                        FastFoodrList(
                            FastFoodlist = FastFoodData().loadFastFood(),
                            navController = navController
                        )
                    }

                    // Beverages Section
                    item {
                        Spacer(modifier = Modifier.height(5.dp))
                        SectionsText(
                            title = stringResource(R.string.beverages),
                            onShowMoreClick = { navController.navigate("beverageMenu") }
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(5.dp))
                        BeveragerList(
                            bevarageList = BeverageData().loadBeverages(),
                            navController = navController
                        )
                        Spacer(modifier = Modifier.height(100.dp))
                    }
                }
            }
        }

        // Bottom Navigation Bar
        ScreenWithBottonNavBar(navController = navController, cartViewModel = cartViewModel)
    }

    // Recipe Details Dialog
    if (showRecipeDialog && selectedMeal != null) {
        RecipeDetailsDialog(
            meal = selectedMeal!!,
            onDismiss = {
                showRecipeDialog = false
                selectedMeal = null
            },
        )
    }
}

@Composable
fun OfflineErrorCard(
    searchQuery: String,
    onRetry: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.WifiOff,
                contentDescription = "No Internet",
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.onErrorContainer
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "No Internet Connection",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onErrorContainer,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Unable to search for \"$searchQuery\". Please check your internet connection and try again.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onErrorContainer,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Retry",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Try Again")
            }
        }
    }
}

@Composable
fun GenericErrorCard(
    error: String,
    onRetry: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Something went wrong",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onErrorContainer,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = error,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onErrorContainer,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Retry",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Try Again")
            }
        }
    }
}

// Food item card components
@Composable
fun BurgerCard(chickenBurgerPictures: Pictures, modifier: Modifier, navController: NavController) {
    BurgerCard(
        imageResourceId = chickenBurgerPictures.imageResourceId,
        title = stringResource(id = chickenBurgerPictures.stringResourceId),
        price = stringResource(id = chickenBurgerPictures.price),
        backgroundColor = BackgroundColor,
        onClick = {
            navController.navigate("detailedProductView/${chickenBurgerPictures.imageResourceId}/${chickenBurgerPictures.imageResourceId2}/${chickenBurgerPictures.stringResourceId}/${chickenBurgerPictures.stringResourceId2}/${chickenBurgerPictures.price}")
        }
    )
}

// Retrieve list to the cards
@Composable
fun BurgerList(chickenBugerList: List<Pictures>, navController: NavController) {
    val limitedChickenBugers = chickenBugerList.take(5)
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(limitedChickenBugers) { chickenBugers ->
            BurgerCard(
                chickenBurgerPictures = chickenBugers,
                modifier = Modifier.padding(9.dp),
                navController = navController
            )
        }
    }
}

@Composable
fun BeverageCard(beveragePictures: Pictures, modifier: Modifier, navController: NavController) {
    BurgerCard(
        imageResourceId = beveragePictures.imageResourceId,
        title = stringResource(id = beveragePictures.stringResourceId),
        price = stringResource(id = beveragePictures.price),
        onClick = {
            navController.navigate("detailedProductView/${beveragePictures.imageResourceId}/${beveragePictures.imageResourceId2}/${beveragePictures.stringResourceId}/${beveragePictures.stringResourceId2}/${beveragePictures.price}")
        }
    )
}

@Composable
fun BeveragerList(bevarageList: List<Pictures>, navController: NavController) {
    val limitedBeverage = bevarageList.take(5)
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(limitedBeverage) { beverage ->
            BeverageCard(
                beveragePictures = beverage,
                modifier = Modifier.padding(9.dp),
                navController = navController
            )
        }
    }
}

@Composable
fun FastFoodCard(FastFood: Pictures, modifier: Modifier, navController: NavController) {
    BurgerCard(
        imageResourceId = FastFood.imageResourceId,
        title = stringResource(id = FastFood.stringResourceId),
        price = stringResource(id = FastFood.price),
        backgroundColor = BackgroundColor,
        onClick = {
            navController.navigate("detailedProductView/${FastFood.imageResourceId}/${FastFood.imageResourceId2}/${FastFood.stringResourceId}/${FastFood.stringResourceId2}/${FastFood.price}")
        }
    )
}

@Composable
fun FastFoodrList(FastFoodlist: List<Pictures>, navController: NavController) {
    val limitedBeefBugers = FastFoodlist.take(5)
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(limitedBeefBugers) { fastfoods ->
            FastFoodCard(
                FastFood = fastfoods,
                modifier = Modifier.padding(9.dp),
                navController = navController
            )
        }
    }
}

// Recipe search components
@Composable
fun RecipeCard(
    meal: Meal,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(12.dp)
        ) {
            AsyncImage(
                model = meal.strMealThumb,
                contentDescription = meal.strMeal,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .weight(1f)
            ) {
                Text(
                    text = meal.strMeal,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                meal.strCategory?.let { category ->
                    Text(
                        text = category,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                meal.strArea?.let { area ->
                    Text(
                        text = area,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailsDialog(
    meal: Meal,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier
            .fillMaxWidth()


    ) {
        Card(
            modifier = Modifier.fillMaxWidth(0.95f),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header with image and title
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = meal.strMeal,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        meal.strCategory?.let { category ->
                            Text(
                                text = "Category: $category",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        meal.strArea?.let { area ->
                            Text(
                                text = "Cuisine: $area",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",

                            )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Recipe Image
                AsyncImage(
                    model = meal.strMealThumb,
                    contentDescription = meal.strMeal,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Instructions
                meal.strInstructions?.let { instructions ->
                    Text(
                        text = "Instructions",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                        text = instructions,
                        style = MaterialTheme.typography.bodyMedium,
                        lineHeight = 20.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {


                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Close")
                    }
                }
            }
        }
    }
}