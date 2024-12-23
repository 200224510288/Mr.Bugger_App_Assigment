package com.example.mrbugger_app.CommonSections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mrbugger_app.BottomNav.BottomNavDesign
import com.example.mrbugger_app.ui.theme.ExtraYellowLight
import com.google.ai.client.generativeai.type.content


@Composable
fun ScreenWithBottonNavBar(){

    Column(modifier = Modifier.fillMaxSize()) {
        // Main Content
        Box(modifier = Modifier.weight(1f).background(Color.White)) {
        }
        // Bottom Navigation Bar
        BottomNavSection()
    }
}


@Composable
fun BottomNavSection(){

    Box(modifier = Modifier
        .fillMaxWidth()
        .background(ExtraYellowLight)
        .shadow(elevation = 8.dp)
        .height(80.dp),
        contentAlignment = Alignment.Center){

        BottomNavDesign(modifier = Modifier)
    }
}