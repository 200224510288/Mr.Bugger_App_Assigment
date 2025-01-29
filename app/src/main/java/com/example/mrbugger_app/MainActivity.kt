package com.example.mrbugger_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mrbugger_app.AuthViewModel.AuthViewModel
import com.example.mrbugger_app.NavController.AppNavigation
import com.example.mrbugger_app.model.CartViewModel
import com.example.mrbugger_app.model.UserProfileViewModel
import com.example.mrbugger_app.ui.screen.homepage.homePage
import com.example.mrbugger_app.ui.screen.welcome.welcomePage
import com.example.mrbugger_app.ui.theme.MrBurgerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val authViewModel : AuthViewModel by viewModels()
        setContent {
            MrBurgerTheme {
                val userProfileViewModel: UserProfileViewModel = viewModel()
                val cartViewModel: CartViewModel = viewModel()
                AppNavigation(userProfileViewModel = userProfileViewModel,cartViewModel = cartViewModel, authViewModel = authViewModel)
            }
        }
    }
}

