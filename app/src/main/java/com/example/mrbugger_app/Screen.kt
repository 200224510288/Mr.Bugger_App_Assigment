package com.example.mrbugger_app

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Search : Screen("search")
    object Cart : Screen("cart")
    object Profile : Screen("profile")
    object Login : Screen("LoginScreen")
    object Singup : Screen("signupPage")


}