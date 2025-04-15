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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.mrbugger_app.CommonSections.BurgerCard
import com.example.mrbugger_app.CommonSections.PopularCategories
import com.example.mrbugger_app.CommonSections.ScreenWithBottonNavBar
import com.example.mrbugger_app.CommonSections.SectionsText
import com.example.mrbugger_app.CommonSections.TopBarSection
import com.example.mrbugger_app.Data.BeverageData
import com.example.mrbugger_app.Data.BurgerItems
import com.example.mrbugger_app.Data.Category
import com.example.mrbugger_app.Data.FastFoodData
import com.example.mrbugger_app.R
import com.example.mrbugger_app.model.CartViewModel
import com.example.mrbugger_app.model.CategoryPictuers
import com.example.mrbugger_app.model.Pictures
import com.example.mrbugger_app.ui.screen.homepage.Searchbar
import com.example.mrbugger_app.ui.theme.BackgroundColor
import com.example.mrbugger_app.ui.theme.PrimaryYellowLight
import org.checkerframework.checker.units.qual.C

@Composable
fun SearchScreen(navController: NavHostController, cartViewModel: CartViewModel) {
    var search by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        // Content Column
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(bottom = 1.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(10.dp),
                verticalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                //cart and back button
                val category = Category()
                item {
                    TopBarSection(navController = navController, cartViewModel = cartViewModel)                }
                item {
                    Searchbar(search = search, onSearchChange = { search = it })
                }
                item {
                    PopularCategories(category.loadCategory())
                }

                //starting food item categories
                item {
                    Spacer(modifier = Modifier.height(5.dp))
                    SectionsText(title = stringResource(R.string.popular_burgers), navController = navController)
                }
                item {
                    Spacer(modifier = Modifier.height(5.dp))
                    BurgerList(chickenBugerList = BurgerItems().loadBurgers(),navController=navController)
                }
                item {
                    Spacer(modifier = Modifier.height(5.dp))
                    SectionsText(title = stringResource(R.string.popular_fast_foods), navController = navController)
                }
                item {
                    Spacer(modifier = Modifier.height(5.dp))
                    FastFoodrList(FastFoodlist = FastFoodData().loadFastFood(),navController=navController)
                }

                item {
                    Spacer(modifier = Modifier.height(5.dp))
                    SectionsText(title = stringResource(R.string.beverages), navController = navController)
                }
                item {
                    Spacer(modifier = Modifier.height(5.dp))
                    BeveragerList(bevarageList = BeverageData().loadBeverages(),navController=navController)
                    Spacer(modifier = Modifier.height(100.dp))

                }



            }
        }
        //  Bottom Navigation Bar
        ScreenWithBottonNavBar(navController = navController, cartViewModel = cartViewModel)    }
}

//food item card
@Composable
fun BurgerCard(chickenBurgerPictures: Pictures, modifier: Modifier, navController: NavController) {
    BurgerCard(
        imageResourceId = chickenBurgerPictures.imageResourceId,
        title = stringResource(id = chickenBurgerPictures.stringResourceId),
        price = stringResource(id = chickenBurgerPictures.price),
        backgroundColor = BackgroundColor,
        onClick = {
            navController.navigate("detailedProductView/${chickenBurgerPictures.imageResourceId}/${chickenBurgerPictures.imageResourceId2}/${chickenBurgerPictures.stringResourceId}/${chickenBurgerPictures.stringResourceId2}/${chickenBurgerPictures.price}")
        }

    )
}
//retrive list to the cards
@Composable
fun BurgerList(chickenBugerList: List<Pictures>,navController: NavController){

    val limitedChickenBugers = chickenBugerList.take(5)
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(limitedChickenBugers){ chickenBugers ->
            BurgerCard(chickenBurgerPictures = chickenBugers, modifier = Modifier.padding(9.dp),navController = navController)
        }
    }
}


@Composable
fun BeverageCard(beveragePictures: Pictures, modifier: Modifier,navController: NavController) {
    com.example.mrbugger_app.CommonSections.BurgerCard(
        imageResourceId = beveragePictures.imageResourceId,
        title = stringResource(id = beveragePictures.stringResourceId),
        price = stringResource(id = beveragePictures.price),
        onClick = {
            navController.navigate("detailedProductView/${beveragePictures.imageResourceId}/${beveragePictures.imageResourceId2}/${beveragePictures.stringResourceId}/${beveragePictures.stringResourceId2}/${beveragePictures.price}")

        }
    )
}


@Composable
fun BeveragerList(bevarageList: List<Pictures> , navController: NavController){

    val limitedBeverage = bevarageList.take(5)
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(limitedBeverage){ beverage ->
            BeverageCard(beveragePictures = beverage, modifier = Modifier.padding(9.dp), navController = navController)
        }
    }
}




@Composable
fun FastFoodCard(FastFood: Pictures, modifier: Modifier, navController: NavController) {
    BurgerCard(
        imageResourceId = FastFood.imageResourceId,
        title = stringResource(id = FastFood.stringResourceId),
        price = stringResource(id = FastFood.price),
        backgroundColor = BackgroundColor,
        onClick = {
            navController.navigate("detailedProductView/${FastFood.imageResourceId}/${FastFood.imageResourceId2}/${FastFood.stringResourceId}/${FastFood.stringResourceId2}/${FastFood.price}")
        })
}

@Composable
fun FastFoodrList(FastFoodlist: List<Pictures>, navController: NavController) {

    val limitedBeefBugers = FastFoodlist.take(5)
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(limitedBeefBugers) { fastfoods ->
            FastFoodCard(
                FastFood = fastfoods,
                modifier = Modifier.padding(9.dp),
                navController = navController
            )
        }
    }
}











