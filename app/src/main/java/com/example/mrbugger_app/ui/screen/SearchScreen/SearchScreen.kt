package com.example.mrbugger_app.ui.screen.SearchScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.mrbugger_app.CommonSections.BurgerCard
import com.example.mrbugger_app.CommonSections.PopularCategories
import com.example.mrbugger_app.CommonSections.ScreenWithBottonNavBar
import com.example.mrbugger_app.CommonSections.SectionsText
import com.example.mrbugger_app.CommonSections.TopBarSection
import com.example.mrbugger_app.Data.BeefBurgerItems
import com.example.mrbugger_app.Data.BeverageData
import com.example.mrbugger_app.Data.Category
import com.example.mrbugger_app.Data.ChickenBurgerItems
import com.example.mrbugger_app.Data.VegBurgerItems
import com.example.mrbugger_app.R
import com.example.mrbugger_app.model.CategoryPictuers
import com.example.mrbugger_app.model.Pictures
import com.example.mrbugger_app.ui.screen.homepage.Searchbar
import com.example.mrbugger_app.ui.theme.BackgroundColor
import com.example.mrbugger_app.ui.theme.PrimaryYellowLight
import org.checkerframework.checker.units.qual.C

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
                val category = Category()
                item {
                    TopBarSection(navController = navController)
                }
                item {
                    Searchbar(search = search, onSearchChange = { search = it })
                }
                item {
                    PopularCategories(category.loadCategory())
                }
                item {
                    Spacer(modifier = Modifier.height(5.dp))
                    SectionsText(title = stringResource(R.string.popular_burgers))
                }
                item {
                    Spacer(modifier = Modifier.height(5.dp))
                    ChickenBurgerList(chickenBugerList = ChickenBurgerItems().loadChickenBurgers())
                }
                item {
                    Spacer(modifier = Modifier.height(5.dp))
                    SectionsText(title = stringResource(R.string.popular_fast_foods)   )
                }
                item {
                    Spacer(modifier = Modifier.height(5.dp))
                    BeefBurgerList(beefBugerList = BeefBurgerItems().loadBeefBurgers())
                }
                item {
                    Spacer(modifier = Modifier.height(5.dp))
                    SectionsText(title = "Veg Burgers")
                }
                item {
                    Spacer(modifier = Modifier.height(5.dp))
                    VegBurgerList(vegBugerList = VegBurgerItems().loadVegBurgers())
                }
                item {
                    Spacer(modifier = Modifier.height(5.dp))
                    SectionsText(title = "Beverages")
                }
                item {
                    Spacer(modifier = Modifier.height(5.dp))
                    BeveragerList(bevarageList = BeverageData().loadBeverages())
                    Spacer(modifier = Modifier.height(100.dp))

                }



            }
        }
        //  Bottom Navigation Bar
        ScreenWithBottonNavBar(navController = navController)
    }
}


@Composable
fun ChickenBurgerCard(chickenBurgerPictures: Pictures, modifier: Modifier) {
    BurgerCard(
        imageResourceId = chickenBurgerPictures.imageResourceId,
        title = stringResource(id = chickenBurgerPictures.stringResourceId),
        price = stringResource(id = chickenBurgerPictures.price),
        backgroundColor = BackgroundColor
    )
}

@Composable
fun ChickenBurgerList(chickenBugerList: List<Pictures>){

    val limitedChickenBugers = chickenBugerList.take(5)
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(limitedChickenBugers){ chickenBugers ->
            ChickenBurgerCard(chickenBurgerPictures = chickenBugers, modifier = Modifier.padding(9.dp))
        }
    }
}

@Composable
fun BeverageCard(beveragePictures: CategoryPictuers, modifier: Modifier) {
    com.example.mrbugger_app.CommonSections.BeverageCard(
        imageResourceId = beveragePictures.imageResourceId,
        title = stringResource(id = beveragePictures.stringResourceId),
        )
}
@Composable
fun BeveragerList(bevarageList: List<CategoryPictuers>){

    val limitedBeverage = bevarageList.take(5)
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(limitedBeverage){ beverage ->
            BeverageCard(beveragePictures = beverage, modifier = Modifier.padding(9.dp))
        }
    }
}

@Composable
fun BeefBurgerCard(beefBurgerPictures: Pictures, modifier: Modifier) {
    BurgerCard(
        imageResourceId = beefBurgerPictures.imageResourceId,
        title = stringResource(id = beefBurgerPictures.stringResourceId),
        price = stringResource(id = beefBurgerPictures.price),
        backgroundColor = BackgroundColor
    )
}

@Composable
fun VegBurgerCard(vegBurgerPictures: Pictures, modifier: Modifier) {
    BurgerCard(
        imageResourceId = vegBurgerPictures.imageResourceId,
        title = stringResource(id = vegBurgerPictures.stringResourceId),
        price = stringResource(id = vegBurgerPictures.price),
        backgroundColor = BackgroundColor
    )
}




@Composable
fun BeefBurgerList(beefBugerList: List<Pictures>){

    val limitedBeefBugers = beefBugerList.take(5)
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(limitedBeefBugers){ beefBugers ->
            BeefBurgerCard(beefBurgerPictures = beefBugers, modifier = Modifier.padding(9.dp))
        }
    }
}

@Composable
fun VegBurgerList(vegBugerList: List<Pictures>){

    val limitedBeefBugers = vegBugerList.take(5)
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(limitedBeefBugers){ vegBugers ->
            VegBurgerCard(vegBurgerPictures = vegBugers, modifier = Modifier.padding(9.dp))
        }
    }
}






