package com.example.mrbugger_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.mrbugger_app.AuthViewModel.AuthViewModel
import com.example.mrbugger_app.NavController.AppNavigation
import com.example.mrbugger_app.model.CartViewModel
import com.example.mrbugger_app.model.ThemeViewModel
import com.example.mrbugger_app.model.UserProfileViewModel
import com.example.mrbugger_app.ui.theme.MrBurgerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    private val themeViewModel by viewModels<ThemeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Observe the theme state for real-time updates
            val isDarkTheme by themeViewModel.isDarkMode

            MrBurgerTheme(darkTheme = isDarkTheme) {
                val userProfileViewModel: UserProfileViewModel = viewModel()
                val cartViewModel: CartViewModel = viewModel()
                val authViewModel: AuthViewModel by viewModels()
                val navController = rememberNavController()

                AppNavigation(
                    userProfileViewModel = userProfileViewModel,
                    cartViewModel = cartViewModel,
                    authViewModel = authViewModel,
                    themeViewModel = themeViewModel,
                    navController = navController
                )
            }
        }
    }
}