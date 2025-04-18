package com.example.mrbugger_app.CommonSections

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mrbugger_app.BottomNav.BottomNavDesign
import com.example.mrbugger_app.Screen
import com.example.mrbugger_app.TopNav.TopBar
import com.example.mrbugger_app.model.CartViewModel
import com.example.mrbugger_app.ui.theme.ExtraYellowLight

// Add bottom bar fixed into bottom
@Composable
fun ScreenWithBottonNavBar(navController: NavController, cartViewModel: CartViewModel) {

    Column(modifier = Modifier.fillMaxSize()) {
        // Main Content
        Box(
            modifier = Modifier
                .weight(1f)
                .background(MaterialTheme.colorScheme.tertiary)
        ) {}
        // Bottom Navigation Bar
        BottomNavSection(navController = navController, cartViewModel = cartViewModel)
    }
}

// Add bottom bar wrap into box
@Composable
fun BottomNavSection(navController: NavController, cartViewModel: CartViewModel) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 8.dp)
            .height(80.dp),
        contentAlignment = Alignment.Center
    ) {
        BottomNavDesign(modifier = Modifier, navController = navController, cartViewModel = cartViewModel)
    }
}

// bottom section the detailed view page
@Composable
fun cartBar(navController: NavController){

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 1.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Back Button
        Button(
            onClick = {navController.popBackStack()},
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(35),
            modifier = Modifier.padding(12.dp).height(50.dp).width(170.dp),
            elevation = ButtonDefaults.buttonElevation(2.dp)
        )  {
            Text("Back to Menu", color = MaterialTheme.colorScheme.onSecondary, fontSize = 17.sp ,fontWeight = FontWeight.SemiBold,modifier = Modifier.padding(horizontal = 2.dp))
        }

        // Confirm Order Button
        Button(
            onClick = {navController.navigate(Screen.OrderConfirmation.route)}, // Confirm order action
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(35),
            modifier = Modifier.padding(12.dp).height(50.dp).width(160.dp),
            elevation = ButtonDefaults.buttonElevation(2.dp)
        ) {
            Text("Confirm Order", color = MaterialTheme.colorScheme.onSecondary, fontSize = 17.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(horizontal = 2.dp))
        }
    }

    Spacer(modifier = Modifier.height(26.dp))

}



// Top bar with cart icon and back icon
@Composable
fun TopBarSection(navController: NavController, cartViewModel: CartViewModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        TopBar(navController = navController, cartViewModel = cartViewModel)
    }
}


