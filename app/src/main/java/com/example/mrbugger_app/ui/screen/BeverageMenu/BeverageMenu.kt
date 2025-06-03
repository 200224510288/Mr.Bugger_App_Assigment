package com.example.mrbugger_app.ui.screen.BeverageMenu

import android.app.Application
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mrbugger_app.CommonSections.ScreenWithBottonNavBar
import com.example.mrbugger_app.CommonSections.TopBarSection
import com.example.mrbugger_app.R
import com.example.mrbugger_app.model.BeverageModel
import com.example.mrbugger_app.model.BeverageViewModel
import com.example.mrbugger_app.model.BeverageViewModelFactory
import com.example.mrbugger_app.model.CartViewModel
import com.example.mrbugger_app.state.BeverageUiState
import com.example.mrbugger_app.ui.components.ShimmerListItem
import com.example.mrbugger_app.ui.screen.homepage.Searchbar
import com.example.mrbugger_app.ui.theme.PrimaryYellowDark
import com.example.mrbugger_app.ui.theme.Shapes
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun BeveragesMenuPage(navController: NavHostController, cartViewModel: CartViewModel = viewModel()) {
    var search by remember { mutableStateOf("") }
    val beverageViewModel: BeverageViewModel = viewModel(
        factory = BeverageViewModelFactory(LocalContext.current.applicationContext as Application)
    )
    val beverageUiState by beverageViewModel.uiState.collectAsState()
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

                        BeverageContent(
                            uiState = beverageUiState,
                            navController = navController,
                            columns = 4,
                            searchQuery = search,
                            onRetry = { beverageViewModel.refreshData() }
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

                        BeverageContent(
                            uiState = beverageUiState,
                            navController = navController,
                            columns = 2,
                            searchQuery = search,
                            onRetry = { beverageViewModel.refreshData() }
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
fun BeverageContent(
    uiState: BeverageUiState,
    navController: NavController,
    columns: Int,
    searchQuery: String,
    onRetry: () -> Unit
) {
    when (uiState) {
        is BeverageUiState.Loading -> {
            ShimmerListItem(
                isLoading = true,
                contentAfterLoading = { /* Empty content */ }
            )
        }
        is BeverageUiState.Error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (uiState.isOffline) {
                    OfflineErrorState(onRetry = onRetry)
                } else {
                    // Regular error state
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Error",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Something went wrong",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = uiState.message,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 32.dp)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = onRetry,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = RoundedCornerShape(17.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Try Again")
                    }
                }
            }
        }
        is BeverageUiState.Success -> {
            Column {
                if (uiState.isOffline) {
                    OfflineBanner(onRetry = onRetry)
                }

                // Filter beverages based on search query
                val filteredBeverages = if (searchQuery.isBlank()) {
                    uiState.beverages
                } else {
                    uiState.beverages.filter { beverage ->
                        beverage.name.contains(searchQuery, ignoreCase = true) ||
                                beverage.description.contains(searchQuery, ignoreCase = true) ||
                                beverage.category.contains(searchQuery, ignoreCase = true)
                    }
                }

                BeverageGrid(
                    beverages = filteredBeverages,
                    navController = navController,
                    columns = columns
                )
            }
        }
    }
}

@Composable
fun BeverageGrid(
    beverages: List<BeverageModel>,
    navController: NavController,
    columns: Int
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(beverages) { beverage ->
            BeverageCard(
                beverage = beverage,
                navController = navController,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}

@Composable
fun BeverageCard(
    beverage: BeverageModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                // Navigate with beverage details using URL encoding for special characters
                val encodedName = URLEncoder.encode(beverage.name, StandardCharsets.UTF_8.toString())
                val encodedDescription = URLEncoder.encode(beverage.description, StandardCharsets.UTF_8.toString())
                val encodedImageUrl = URLEncoder.encode(beverage.imageUrl, StandardCharsets.UTF_8.toString())
                val encodedCategory = URLEncoder.encode(beverage.category, StandardCharsets.UTF_8.toString())

                navController.navigate("detailedBurgerView/$encodedName/$encodedDescription/${beverage.price}/$encodedImageUrl/$encodedCategory")
            },
        shape = Shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(4.dp)
        ) {
            // Beverage Image
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(beverage.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = beverage.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                placeholder = painterResource(R.drawable.placeholder),
                error = painterResource(R.drawable.placeholder)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Beverage name
            Text(
                text = beverage.name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))


            Spacer(modifier = Modifier.height(4.dp))

            // Price
            Text(
                text = "Rs. ${beverage.price}",
                style = MaterialTheme.typography.bodyMedium,
                color = PrimaryYellowDark,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    // Navigate using the new route
                    val encodedName = URLEncoder.encode(beverage.name, StandardCharsets.UTF_8.toString())
                    val encodedDescription = URLEncoder.encode(beverage.description, StandardCharsets.UTF_8.toString())
                    val encodedImageUrl = URLEncoder.encode(beverage.imageUrl, StandardCharsets.UTF_8.toString())
                    val encodedCategory = URLEncoder.encode(beverage.category, StandardCharsets.UTF_8.toString())

                    navController.navigate("detailedBurgerView/$encodedName/$encodedDescription/${beverage.price}/$encodedImageUrl/$encodedCategory")
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(17.dp),
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text(
                    text = "Add to Cart",
                    color = MaterialTheme.colorScheme.background,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
private fun OfflineBanner(onRetry: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Offline",
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Offline Mode: Using cached data",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium
                )
            }

            TextButton(
                onClick = onRetry,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Retry",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun OfflineErrorState(onRetry: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(32.dp)
    ) {
        // Offline icon with background
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(
                    color = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(40.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "No Connection",
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.size(40.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "No Internet Connection",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Please check your internet connection and try again",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Primary retry button
        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(17.dp),
            modifier = Modifier.fillMaxWidth(0.6f)
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Try Again",
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}