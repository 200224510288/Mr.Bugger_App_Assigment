package com.example.mrbugger_app.Data

import androidx.annotation.DrawableRes
import com.example.mrbugger_app.APIService.NetworkClient
import com.example.mrbugger_app.R
import com.example.mrbugger_app.model.CategoryModel
import com.example.mrbugger_app.model.CategoryPictuers
import com.example.mrbugger_app.model.Pictures
import com.google.android.engage.common.datamodel.Price
import java.io.IOException

class FoodItem {
    fun loadPopularPictures(): List<Pictures> {
        return listOf(
            Pictures(
                R.drawable.chicken_burger,
                R.drawable.nonveg,
                R.string.ChickenBurger,
                R.string.zingerBurgerDesc,
                R.string.ChickenBurgerPrice,
            ),
            Pictures(
                R.drawable.beef_burger,
                R.drawable.nonveg,
                R.string.BeefBurger,
                R.string.bbqBaconBurgerDesc,
                R.string.BeefBurgerPrice,
            ),
            Pictures(
                R.drawable.beef_2,
                R.drawable.nonveg,
                R.string.whooperBuger,
                R.string.WesternWhopperBurgerDesc,
                R.string.whooperBugerPrice,
            ),
            Pictures(
                R.drawable.veg_burger,
                R.drawable.veg,
                R.string.VegBurger,
                R.string.classicVeggieBurgerDesc,
                R.string.VegBurgerPrice
            ),
        )
    }
}


class BurgerItems {
    fun loadBurgers(): List<Pictures> {
        return listOf(
            Pictures(
                R.drawable.zingerburger,
                R.drawable.nonveg,
                R.string.zingerBurger,
                R.string.zingerBurgerDesc,
                R.string.zingerBurgerPrice,
            ),
            Pictures(
                R.drawable.loadedcheeseburger,
                R.drawable.nonveg,
                R.string.loadedCheeseburger,
                R.string.loadedCheeseburgerDesc,
                R.string.loadedCheeseburgerPrice,
            ),
            Pictures(
                R.drawable.tandooriburger,
                R.drawable.nonveg,
                R.string.tandooriBurger,
                R.string.tandooriBurgerDesc,
                R.string.tandooriBurgerPrice,
            ),
            Pictures(
                R.drawable.nashvilleburger,
                R.drawable.nonveg,
                R.string.nashvilleBurger,
                R.string.nashvilleBurgerDesc,
                R.string.nashvilleBurgerPrice
            ),
            Pictures(
                R.drawable.bbqbeefburger,
                R.drawable.nonveg,
                R.string.bbqBaconBurger,
                R.string.bbqBaconBurgerDesc,
                R.string.bbqBaconBurgerPrice,
            ),
            Pictures(
                R.drawable.westernwhopper,
                R.drawable.nonveg,
                R.string.WesternWhopperBurger,
                R.string.WesternWhopperBurgerDesc,
                R.string.WesternWhopperBurgerPrice,
            ),
            Pictures(
                R.drawable.cheesybeefburger,
                R.drawable.nonveg,
                R.string.loadedCheeseBeefBurger,
                R.string.loadedCheeseBeefBurgerDesc,
                R.string.loadedCheeseBeefBurgerPrice,
            ),
            Pictures(
                R.drawable.mushrrombeefburger,
                R.drawable.nonveg,
                R.string.DoubleMushroomSwissBurger,
                R.string.DoubleMushroomSwissBurgerDesc,
                R.string.DoubleMushroomSwissBurgerPrice
            ),
            Pictures(
                R.drawable.vegburger1,
                R.drawable.veg,
                R.string.classicVeggieBurger,
                R.string.classicVeggieBurgerDesc,
                R.string.classicVeggieBurgerPrice,
            ),
            Pictures(
                R.drawable.vebburger2,
                R.drawable.veg,
                R.string.spicyVeggieBurger,
                R.string.spicyVeggieBurgerDesc,
                R.string.spicyVeggieBurgerPrice,
            ),
            Pictures(
                R.drawable.vegburger3,
                R.drawable.veg,
                R.string.gardenFreshBurger,
                R.string.gardenFreshBurgerDesc,
                R.string.gardenFreshBurgerPrice,
            ),
            Pictures(
                R.drawable.vegbuger4,
                R.drawable.veg,
                R.string.cheesyVegDelightBurger,
                R.string.cheesyVegDelightBurgerDesc,
                R.string.cheesyVegDelightBurgerPrice
            ),
        )
    }
}

class BeverageData {
    fun loadBeverages(): List<Pictures> {
        return listOf(
            Pictures(
                R.drawable.pineapple,
                R.drawable.veg,
                R.string.Pinaplle,
                R.string.PinaplleDesc,
                R.string.PinapllePrice
            ),
            Pictures(
                R.drawable.watermelon,
                R.drawable.veg,
                R.string.watermellon,
                R.string.watermellonDesc,
                R.string.watermellonPrice
            ),
            Pictures(
                R.drawable.grapes,
                R.drawable.veg,
                R.string.grapes,
                R.string.grapesDesc,
                R.string.grapesPrice
            ),
            Pictures(
                R.drawable.lime,
                R.drawable.veg,
                R.string.Lime,
                R.string.LimeDesc,
                R.string.LimePrice
            ),
            Pictures(
                R.drawable.pasion,
                R.drawable.veg,
                R.string.Pasion,
                R.string.PasionDesc,
                R.string.PasionPrice
            ),
            Pictures(
                R.drawable.orange,
                R.drawable.veg,
                R.string.Orange,
                R.string.OrangeDesc,
                R.string.OrangePrice
            ),
            Pictures(
                R.drawable.apple,
                R.drawable.veg,
                R.string.Apple,
                R.string.AppleDesc,
                R.string.ApplePrice
            ),
        )
    }
}

class FastFoodData {
    fun loadFastFood(): List<Pictures> {
        return listOf(
            Pictures(
                R.drawable.fries,
                R.drawable.veg,
                R.string.Fries,
                R.string.FriesDesc,
                R.string.FriesPrice
            ),
            Pictures(
                R.drawable.rings,
                R.drawable.veg,
                R.string.rings,
                R.string.ringsDesc,
                R.string.ringsPrice
            ),
            Pictures(
                R.drawable.wegus,
                R.drawable.veg,
                R.string.Wegus,
                R.string.WegusDesc,
                R.string.WegusPrice
            ),
            Pictures(
                R.drawable.breads,
                R.drawable.veg,
                R.string.breads,
                R.string.breadsDesc,
                R.string.breadsPrice
            ),
            Pictures(
                R.drawable.taccos,
                R.drawable.nonveg,
                R.string.taccos,
                R.string.taccosDesc,
                R.string.taccosPrice
            ),
            Pictures(
                R.drawable.shawrma,
                R.drawable.nonveg,
                R.string.shawarma,
                R.string.shawarmaDesc,
                R.string.shawarmaPrice
            ),
        )
    }
}



object Category {
    // Local cache for categories
    private var cachedCategories: List<CategoryModel>? = null

    // Local loading (unchanged)
    fun loadCategoryLocal(): List<CategoryPictuers> {
        return listOf(
            CategoryPictuers(
                R.drawable.chicken_burger,
                R.string.Burgers,
            ),
            // ... other categories
        )
    }

    // Remote loading with offline detection
    suspend fun loadCategoryRemote(isNetworkAvailable: Boolean): Result<List<CategoryModel>> {
        return if (!isNetworkAvailable) {
            cachedCategories?.let { Result.success(it) }
                ?: Result.failure(Exception("Offline and no cached data available"))
        } else {
            try {
                val response = NetworkClient.apiService.getCategories()
                cachedCategories = response.categories // Cache the result
                Result.success(response.categories)
            } catch (e: Exception) {
                if (e is IOException || e.message?.contains("network", true) == true) {
                    cachedCategories?.let { Result.success(it) }
                        ?: Result.failure(Exception("Offline and no cached data available"))
                } else {
                    Result.failure(e)
                }
            }
        }
    }
}
