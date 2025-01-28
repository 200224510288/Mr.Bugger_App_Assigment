package com.example.mrbugger_app.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mrbugger_app.Data.Category
import com.example.mrbugger_app.R
import com.example.mrbugger_app.model.CategoryPictuers
import com.example.mrbugger_app.ui.theme.MrBurgerTheme
import com.example.mrbugger_app.ui.theme.PrimaryYellowLight
import com.example.mrbugger_app.ui.theme.Shapes
import org.checkerframework.checker.units.qual.s


//creating  the categoryChip
@Composable
fun CategoryChip(
    titleRes : Int,
    imageRes: Int,
    isSelect: Boolean,
    onSelected: () -> Unit,
    modifier: Modifier = Modifier
){
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
        modifier = Modifier.clickable{ onSelected() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(shape = Shapes.small)
                .shadow(elevation = 6.dp, ambientColor = MaterialTheme.colorScheme.onBackground)
                .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = stringResource(id = titleRes),
                modifier = Modifier.size(72.dp),
                contentScale = ContentScale.Fit
            )
        }
        Text(
            text = stringResource(id = titleRes),
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = fontWeight,
            fontSize = 16.sp,

        )
    }
}


// creating a Category bar for hold chips
@Composable
fun CategoryBar(
    modifier: Modifier = Modifier,
    categories: List<CategoryPictuers> = Category().loadCategory()
) {
    var selectedCategory by remember { mutableStateOf(0) }

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(categories.size) { index ->
            val category = categories[index]
            CategoryChip(
                titleRes = category.stringResourceId,
                imageRes = category.imageResourceId,
                isSelect = selectedCategory == index,
                onSelected = { selectedCategory = index },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryBarPreview() {
    MrBurgerTheme {
        CategoryBar()
    }
}
