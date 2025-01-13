package com.example.mrbugger_app.CommonSections

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mrbugger_app.BottomNav.BottomNavDesign
import com.example.mrbugger_app.TopNav.TopBar
import com.example.mrbugger_app.ui.theme.ExtraYellowLight


@Composable
fun ScreenWithBottonNavBar(navController: NavController){
    Column(modifier = Modifier.fillMaxSize()) {
        // Main Content
        Box(modifier = Modifier.weight(1f).background(Color.White)) {
        }
        // Bottom Navigation Bar
        BottomNavSection(navController = navController)
    }
}


@Composable
fun BottomNavSection(navController: NavController){

    Box(modifier = Modifier
        .fillMaxWidth()
        .background(ExtraYellowLight)
        .shadow(elevation = 8.dp)
        .height(80.dp),
        contentAlignment = Alignment.Center){

        BottomNavDesign(modifier = Modifier, navController = navController)
    }
}

@Composable
fun TopBarSection(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        TopBar(modifier = Modifier,navController = navController)
    }
}



