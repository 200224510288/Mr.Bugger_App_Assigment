package com.example.mrbugger_app.ui.screen.SearchScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mrbugger_app.CommonSections.ScreenWithBottonNavBar
import com.example.mrbugger_app.CommonSections.TopBarSection
import com.example.mrbugger_app.Data.DataSource
import com.example.mrbugger_app.ui.components.CategoryBar
import com.example.mrbugger_app.ui.components.LogoAndCard
import com.example.mrbugger_app.ui.components.PromoBanner
import com.example.mrbugger_app.ui.screen.homepage.PopularBar
import com.example.mrbugger_app.ui.screen.homepage.PopularBurgerList
import com.example.mrbugger_app.ui.screen.homepage.Searchbar

@Composable
fun SearchScreen(navController: NavHostController) {
    var search by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        // Content Column
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(1.dp)
                .padding(bottom = 1.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(10.dp),
                verticalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                item {
                    TopBarSection()
                }
                item {
                    Searchbar(search = search, onSearchChange = { search = it })
                }

            }
        }
        //  Bottom Navigation Bar
        ScreenWithBottonNavBar(navController = navController)
    }
}