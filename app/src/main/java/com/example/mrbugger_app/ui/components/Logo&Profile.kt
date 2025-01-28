package com.example.mrbugger_app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mrbugger_app.Pictures.Pictures


@Composable
fun LogoAndCard(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Column for text items
        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = AnnotatedString.Builder().apply {
                    // Style for "Mr."
                    pushStyle(
                        SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontFamily = MaterialTheme.typography.displayLarge.fontFamily,
                            fontWeight = MaterialTheme.typography.displayLarge.fontWeight,
                            fontSize = 25.sp
                        )
                    )
                    append("Mr.")
                    pop()

                    // Style for "Burger"
                    pushStyle(
                        SpanStyle(
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                            fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
                            fontSize = 25.sp
                        )
                    )
                    append("Burger")
                    pop()
                }.toAnnotatedString(),
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
        // Circular Profile Image
        ProfileSection()
    }
}


@Composable
fun ProfileSection() {

    Box(modifier = Modifier
        .size(40.dp)
        .clip(CircleShape)
        .background(MaterialTheme.colorScheme.surface)){
        Image(painter = painterResource(id = Pictures.ProfileImage),
            contentDescription = "Profile Picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize())
    }
}


@Preview
@Composable
fun LogoPage(){
    LogoAndCard()
}