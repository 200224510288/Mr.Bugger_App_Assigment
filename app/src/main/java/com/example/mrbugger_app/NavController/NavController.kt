package com.example.mrbugger_app.NavController

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mrbugger_app.Screen
import com.example.mrbugger_app.model.CartViewModel
import com.example.mrbugger_app.model.UserProfileViewModel
import com.example.mrbugger_app.ui.screen.CartScreen.CartScreen
import com.example.mrbugger_app.ui.screen.DetailedProductView.DetailedProductView
import com.example.mrbugger_app.ui.screen.ProfileScreen.ProfileScreen
import com.example.mrbugger_app.ui.screen.SearchScreen.SearchScreen
import com.example.mrbugger_app.ui.screen.homepage.homePage
import com.example.mrbugger_app.ui.screen.login.LoginScreen
import com.example.mrbugger_app.ui.screen.signup.signupPage

@Composable
fun AppNavigation(userProfileViewModel: UserProfileViewModel, cartViewModel: CartViewModel) {
   Surface(
       modifier = Modifier.fillMaxSize(),
       color = MaterialTheme.colorScheme.background
   ){
       val navController = rememberNavController()

       NavHost(
           navController = navController,
           startDestination = Screen.Cart.route,
                       ){
           composable(Screen.Home.route){
               homePage(navController = navController)
           }
           composable(Screen.Profile.route){
               ProfileScreen(navController = navController, userProfileViewModel = userProfileViewModel )
           }
           composable(Screen.Search.route){
               SearchScreen(navController = navController)
           }
           composable(Screen.Cart.route){
               CartScreen(navController = navController,cartViewModel)
           }
           composable(Screen.Login.route){
               LoginScreen(navController = navController)
           }
           composable(Screen.Singup.route){
               signupPage(navController = navController)
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

