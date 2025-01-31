package com.example.mrbugger_app.NavController

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.example.mrbugger_app.AuthViewModel.AuthViewModel
import com.example.mrbugger_app.Screen
import com.example.mrbugger_app.model.CartViewModel
import com.example.mrbugger_app.model.UserProfileViewModel
import com.example.mrbugger_app.ui.screen.*
import com.example.mrbugger_app.ui.screen.CartScreen.CartScreen
import com.example.mrbugger_app.ui.screen.DetailedProductView.DetailedProductView
import com.example.mrbugger_app.ui.screen.MenuScreen.MenuPage
import com.example.mrbugger_app.ui.screen.OrderConfirmation.OrderConfirmation
import com.example.mrbugger_app.ui.screen.ProfileScreen.ProfileScreen
import com.example.mrbugger_app.ui.screen.SearchScreen.SearchScreen
import com.example.mrbugger_app.ui.screen.homepage.homePage
import com.example.mrbugger_app.ui.screen.login.LoginScreen
import com.example.mrbugger_app.ui.screen.signup.signupPage

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation(userProfileViewModel: UserProfileViewModel, cartViewModel: CartViewModel, authViewModel: AuthViewModel) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val navController = rememberNavController()


        // Fade effect for smooth transition.
        AnimatedNavHost(
            navController = navController,
            startDestination = Screen.Login.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(500)) + fadeIn() },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(500)) + fadeOut() },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(500)) + fadeIn() },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(500)) + fadeOut() }
        ) {
            composable(Screen.Home.route) {
                homePage(navController = navController, authViewModel)
            }
            composable(Screen.Profile.route) {
                ProfileScreen(navController = navController, userProfileViewModel, authViewModel)
            }
            composable(Screen.Search.route) {
                SearchScreen(navController = navController)
            }
            composable(Screen.Cart.route) {
                CartScreen(navController = navController, cartViewModel)
            }
            composable(Screen.Login.route) {
                LoginScreen(navController = navController, authViewModel)
            }
            composable(Screen.Singup.route) {
                signupPage(navController = navController, authViewModel)
            }
            composable(Screen.Menu.route) {
                MenuPage(navController = navController)
            }
            composable(Screen.OrderConfirmation.route) {
                OrderConfirmation(navController = navController, cartViewModel)
            }
            composable(
                route = "detailedProductView/{imageResId}/{nameResId}/{priceResId}",
                arguments = listOf(
                    navArgument("imageResId") { type = NavType.IntType },
                    navArgument("nameResId") { type = NavType.IntType },
                    navArgument("priceResId") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val imageResId = backStackEntry.arguments?.getInt("imageResId") ?: 0
                val nameResId = backStackEntry.arguments?.getInt("nameResId") ?: 0
                val priceResId = backStackEntry.arguments?.getInt("priceResId") ?: 0
                DetailedProductView(
                    navController = navController,
                    cartViewModel = cartViewModel,
                    imageResId = imageResId,
                    nameResId = nameResId,
                    priceResId = priceResId
                )
            }
        }
    }
}
