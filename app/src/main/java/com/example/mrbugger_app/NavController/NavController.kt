package com.example.mrbugger_app.NavController

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mrbugger_app.Screen
import com.example.mrbugger_app.ui.screen.CartScreen.CartScreen
import com.example.mrbugger_app.ui.screen.ProfileScreen.ProfileScreen
import com.example.mrbugger_app.ui.screen.SearchScreen.SearchScreen
import com.example.mrbugger_app.ui.screen.homepage.homePage

@Composable
fun AppNavigation() {
   Surface(
       modifier = Modifier.fillMaxSize(),
       color = MaterialTheme.colorScheme.background
   ){
       val navController = rememberNavController()

       NavHost(
           navController = navController,
           startDestination = Screen.Home.route
                       ){
           composable(Screen.Home.route){
               homePage(navController = navController)
           }
           composable(Screen.Profile.route){
               ProfileScreen(navController = navController)
           }
           composable(Screen.Search.route){
               SearchScreen(navController = navController)
           }
           composable(Screen.Cart.route){
               CartScreen(navController = navController)
           }

       }

   }
}