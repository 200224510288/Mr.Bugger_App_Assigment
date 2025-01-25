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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mrbugger_app.ui.screen.homepage.homePage
import com.example.mrbugger_app.ui.theme.Darkgray
import com.example.mrbugger_app.ui.theme.Poppins
import com.example.mrbugger_app.ui.theme.PrimaryYellowLight

@Composable
fun SearchBar(
    search: String,
    onSearchChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .background(Color.White, RoundedCornerShape(60.dp))
            .height(55.dp)
            .shadow(4.dp, shape = RoundedCornerShape(60.dp), clip = false)
            .border(1.dp, Color.Gray, RoundedCornerShape(60.dp)),

        contentAlignment = Alignment.Center
    ) {
        TextField(
            value = search,
            onValueChange = onSearchChange,
            placeholder = { Text(text = "Search", fontFamily = Poppins, fontWeight = FontWeight.Medium, color = Darkgray) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFEDEDED),
                unfocusedContainerColor = Color(0xFFF0F0F0),
                focusedIndicatorColor = PrimaryYellowLight,
                unfocusedIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Darkgray,
                )
            },
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.Black,
                fontSize = 18.sp,
                fontFamily = Poppins,
                fontWeight = FontWeight.Medium
            ),
            shape = RoundedCornerShape(60.dp),
            visualTransformation = VisualTransformation.None
        )
    }
}


@Preview
@Composable
fun PreviewHomePage(){
    SearchBar(search = "", onSearchChange = {})
}