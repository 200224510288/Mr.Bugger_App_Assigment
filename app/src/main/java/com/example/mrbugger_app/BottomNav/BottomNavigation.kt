package com.example.mrbugger_app.BottomNav

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.mrbugger_app.Screen
import com.example.mrbugger_app.model.CartViewModel
import com.example.mrbugger_app.model.NavItemState
import com.example.mrbugger_app.ui.theme.ExtraYellowLight
import com.example.mrbugger_app.ui.theme.PrimaryYellowLight

/**bottom navigation bar*/

@Composable
fun BottomNavDesign(
    modifier: Modifier = Modifier,
    navController: NavController,
    cartViewModel: CartViewModel
) {
    val items = listOf(
        NavItemState(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        NavItemState(
            title = "Search",
            selectedIcon = Icons.Filled.Search,
            unselectedIcon = Icons.Outlined.Search
        ),
        NavItemState(
            title = "Cart",
            selectedIcon = Icons.Filled.ShoppingCart,
            unselectedIcon = Icons.Outlined.ShoppingCart
        ),
        NavItemState(
            title = "Profile",
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person
        )
    )

    // Observe the current back stack entry to determine the selected tab
    val currentBackStackEntry by navController.currentBackStackEntryFlow.collectAsStateWithLifecycle(null)

    // Determine the currently selected tab based on the navigation route
    val navBarState = when (currentBackStackEntry?.destination?.route) {
        Screen.Home.route -> 0
        Screen.Search.route -> 1
        Screen.Cart.route -> 2
        Screen.Profile.route -> 3
        else -> -1
    }

    // Observe the cart count to update the cart badge dynamically
    val cartCount by remember { derivedStateOf { cartViewModel.cartSize } }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(MaterialTheme.colorScheme.tertiary),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        items.forEachIndexed { index, item ->
            Box(
                modifier = Modifier.size(50.dp),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = {
                        if (navBarState != index) {
                            navController.navigate(
                                when (index) {
                                    0 -> Screen.Home.route
                                    1 -> Screen.Search.route
                                    2 -> Screen.Cart.route
                                    3 -> Screen.Profile.route
                                    else -> ""
                                }
                            )
                        }
                    }
                ) {
                    Icon(
                        imageVector = if (navBarState == index) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.title,
                        tint = if (navBarState == index) Color.Black else Color.Gray,
                        modifier = Modifier.size(28.dp)
                    )
                }

                // Show badge on Cart icon only when there are items in the cart
                if (index == 2 && cartCount > 0) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.error)
                            .align(Alignment.TopEnd)
                    ) {
                        Text(
                            text = cartCount.toString(),
                            color = Color.White,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
            // Add spacing between navigation items
            if (index < items.size - 1) {
                Spacer(modifier = Modifier.width(7.dp))
            }
        }
    }
}
