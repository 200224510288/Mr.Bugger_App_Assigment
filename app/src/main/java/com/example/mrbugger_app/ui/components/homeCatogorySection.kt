package com.example.mrbugger_app.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mrbugger_app.Data.Category
import com.example.mrbugger_app.R
import com.example.mrbugger_app.model.CategoryViewModel
import com.example.mrbugger_app.model.CategoryViewModelFactory
import com.example.mrbugger_app.state.CategoryUiState
import com.example.mrbugger_app.ui.theme.MrBurgerTheme
import com.example.mrbugger_app.ui.theme.PrimaryYellowLight
import com.example.mrbugger_app.ui.theme.Shapes

@Composable
fun CategoryChip(
    modifier: Modifier = Modifier,
    titleRes: Int? = null,
    title: String? = null,
    imageRes: Int? = null,
    imageUrl: String? = null,
    isSelect: Boolean = false,
    onSelected: () -> Unit
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelect) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
        label = "Category Background"
    )
    val textColor by animateColorAsState(
        targetValue = if (isSelect) PrimaryYellowLight else Color.Black,
        label = "Category Text Color"
    )
    val fontWeight = if (isSelect) FontWeight.Bold else FontWeight.Normal

    Column(
        modifier = modifier
            .clickable { onSelected() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(Shapes.small)
                .shadow(6.dp, ambientColor = MaterialTheme.colorScheme.onBackground)
                .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            if (imageRes != null) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            } else if (imageUrl != null) {
                CategoryCardImage(
                    modifier = Modifier.fillMaxSize(),
                    imageUrl = imageUrl
                )
            }
        }

        val displayText = titleRes?.let { stringResource(it) } ?: title.orEmpty()

        Text(
            text = displayText,
            color = textColor,
            fontWeight = fontWeight,
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 14.sp),
            maxLines = 1
        )
    }
}

@Composable
private fun CategoryCardImage(
    modifier: Modifier = Modifier,
    imageUrl: String
) {
    val context = LocalContext.current
    val imageRequest = ImageRequest
        .Builder(context)
        .data(imageUrl)
        .crossfade(true)
        .build()

    Box(modifier = modifier) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = imageRequest,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            placeholder = painterResource(R.drawable.placeholder),
            error = painterResource(R.drawable.placeholder)
        )
    }
}

@Composable
fun CategoryBar(
    modifier: Modifier = Modifier,
    viewModel: CategoryViewModel = viewModel(
        factory = CategoryViewModelFactory(LocalContext.current.applicationContext)
    )
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedCategory by rememberSaveable { mutableStateOf(0) }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (uiState) {
            is CategoryUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }
            is CategoryUiState.Error -> {
                val errorState = uiState as CategoryUiState.Error
                if (errorState.isOffline) {
                    OfflineBanner()
                }
                Text(
                    text = "Error: ${errorState.message}",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            }
            is CategoryUiState.Success -> {
                val successState = uiState as CategoryUiState.Success

                if (successState.isOffline) {
                    OfflineBanner()
                }

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.Start),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(successState.categories.size) { index ->
                        val category = successState.categories[index]
                        CategoryChip(
                            title = category.name,
                            imageUrl = category.imageUrl,
                            isSelect = selectedCategory == index,
                            onSelected = { selectedCategory = index }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun OfflineBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Red.copy(alpha = 0.1f))
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Offline Mode: Using cached data",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall
        )
    }
}
