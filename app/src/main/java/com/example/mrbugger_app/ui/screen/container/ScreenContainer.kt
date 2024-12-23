package com.example.mrbugger_app.ui.screen.container

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mrbugger_app.ui.screen.login.loginPage
import com.example.mrbugger_app.ui.screen.welcome.welcomePage

@Composable
fun ScreenContainer() {
    val navHost = rememberNavController()

    NavHost(
        navController = navHost,
        startDestination = NavGraph.Welcome.route
    ) {
        composable(NavGraph.Welcome.route) {
            welcomePage(navController = navHost)
        }

        composable(NavGraph.Login.route) {
            loginPage()
        }
    }
}