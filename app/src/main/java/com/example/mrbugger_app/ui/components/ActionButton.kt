package com.example.mrbugger_app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mrbugger_app.R

@Composable
fun ActionButton(
    modifier: Modifier = Modifier, // Accept modifier as a parameter
    text: String,
    isNavigationArrowVisible: Boolean,
    onClicked: () -> Unit,
    colors: ButtonColors,
    shadowColor: Color,
    textSize: TextUnit = 16.sp
) {
    Button(
        modifier = modifier // Use the passed modifier
            .fillMaxWidth()
            .height(62.dp)
            .shadow(
                elevation = 28.dp,
                shape = RoundedCornerShape(percent = 50),
                spotColor = shadowColor
            ),
        onClick = onClicked,
        colors = colors
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = textSize, // Use the provided text size
                fontWeight = FontWeight.Bold
            )


        }
    }
}
