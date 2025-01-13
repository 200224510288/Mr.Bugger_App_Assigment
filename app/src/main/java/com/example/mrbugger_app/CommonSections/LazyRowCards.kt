package com.example.mrbugger_app.CommonSections

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mrbugger_app.Data.Category
import com.example.mrbugger_app.R
import com.example.mrbugger_app.model.CategoryPictuers
import com.example.mrbugger_app.ui.theme.BackgroundColor
import com.example.mrbugger_app.ui.theme.PrimaryYellowDark
import com.example.mrbugger_app.ui.theme.SecondaryColor

@Composable
fun BurgerCard(
    imageResourceId: Int,
    title: String,
    price: String,
    backgroundColor: Color = BackgroundColor,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .padding(horizontal = 8.dp)
            .width(170.dp)
            .height(220.dp)
            .clickable { onClick() }
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(8.dp),
                ambientColor = Color.Black,
                spotColor = Color.Black
            ),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column {
            Image(
                painter = painterResource(imageResourceId),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(135.dp)
                    .width(240.dp)
            )
            Text(
                text = title,
                fontSize = 18.sp,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Rs $price",
                fontSize = 18.sp,
                color = PrimaryYellowDark,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .padding(horizontal = 9.dp)
            )
        }
    }
}

@Composable
fun BeverageCard(
    imageResourceId: Int,
    title: String,
    backgroundColor: Color = Color.LightGray,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .padding(horizontal = 12.dp)
            .size(150.dp),
        shape = CircleShape,
    ) {
        Column(
            modifier = Modifier
                .clickable(onClick = onClick)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(imageResourceId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(8.dp)) // Add spacing between Image and Text

        }
    }
}

@Composable
fun PopularCategories(categories: List<CategoryPictuers>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.PopularCategories,),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(categories) { category ->
                CategoryItem(category)
            }
        }
    }
}

@Composable
fun CategoryItem(category: CategoryPictuers) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(80.dp)
            .clickable { }
    ) {
        // Circle with elevation
        Card(
            shape = CircleShape,
            elevation = CardDefaults.cardElevation(8.dp),
            modifier = Modifier.size(75.dp)
        ) {
            Image(
                painter = painterResource(id = category.imageResourceId),
                contentDescription = stringResource(id = category.stringResourceId),
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape).background(BackgroundColor)

            )
        }

        // Text below the circle
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = category.stringResourceId),
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
