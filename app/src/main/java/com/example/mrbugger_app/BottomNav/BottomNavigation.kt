package com.example.mrbugger_app.BottomNav

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.mrbugger_app.Screen
import com.example.mrbugger_app.ui.theme.ExtraYellowLight
import com.example.mrbugger_app.ui.theme.PrimaryYellowLight


@Composable
fun BottomNavDesign(modifier: Modifier = Modifier, navController: NavController) {

    Row(
        modifier = Modifier
            .shadow(elevation = 8.dp)
            .fillMaxWidth()
            .height(66.dp)
            .background(ExtraYellowLight),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        // Home Icon
        IconButton(onClick = { navController.navigate(Screen.Home.route) }) {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = "Home",
                tint = Color.Black,
                modifier = Modifier.size(28.dp)
            )
        }

        Spacer(modifier = Modifier.size(25.dp))

        // Explore Icon
        IconButton(onClick = {navController.navigate(Screen.Search.route)}) {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Explore",
                tint = Color.Black,
                modifier = Modifier.size(28.dp)
            )
        }

        Spacer(modifier = Modifier.size(25.dp))

        // Shopping cart
        IconButton(onClick = {navController.navigate(Screen.Cart.route)}) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(Color.Transparent)
            ) {
                Icon(
                    imageVector = Icons.Outlined.ShoppingCart,
                    contentDescription = "Saved",
                    tint = Color.Black,
                    modifier = Modifier.size(28.dp)
                )

                // Badge
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .clip(CircleShape)
                        .background(PrimaryYellowLight)
                        .align(Alignment.TopEnd)
                ) {
                    Text(
                        text = "5", // badge count
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        Spacer(modifier = Modifier.size(25.dp))

        // Profile Icon
        IconButton(onClick = {navController.navigate(Screen.Profile.route) }) {
            Icon(
                imageVector = Icons.Outlined.Person,
                contentDescription = "Profile",
                tint = Color.Black,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}