package com.example.mrbugger_app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.mrbugger_app.ui.theme.Poppins

// common search bar for all pages
@Composable
fun SearchBarForSearchScreen(
    search: String,
    onSearchChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .background(MaterialTheme.colorScheme.surfaceBright, RoundedCornerShape(60.dp))
            .height(55.dp)
            .shadow(4.dp, shape = RoundedCornerShape(60.dp), clip = false)
            .border(1.dp, MaterialTheme.colorScheme.onBackground, RoundedCornerShape(60.dp)),

        contentAlignment = Alignment.Center
    ) {
        TextField(
            value = search,
            onValueChange = onSearchChange,
            placeholder = { Text(text = "Search Any Food Recipe ...", fontFamily = Poppins, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onBackground) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            },
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground,
                fontFamily = Poppins,
                fontWeight = FontWeight.Medium
            ),
            shape = RoundedCornerShape(60.dp),
            visualTransformation = VisualTransformation.None
        )
    }
}
