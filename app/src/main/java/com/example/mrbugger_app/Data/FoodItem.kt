package com.example.mrbugger_app.Data

import androidx.annotation.DrawableRes
import com.example.mrbugger_app.R
import com.example.mrbugger_app.model.CategoryPictuers
import com.example.mrbugger_app.model.Pictures
import com.google.android.engage.common.datamodel.Price

class FoodItem {

    fun loadPopularPictures(): List<Pictures> {
        return listOf<Pictures>(
            Pictures(
                R.drawable.chicken_burger,
                R.string.ChickenBurger,
                R.string.ChickenBurgerPrice,
            ),
            Pictures(
                R.drawable.beef_burger,
                R.string.BeefBurger,
                R.string.BeefBurgerPrice,
            ),
            Pictures(
                R.drawable.beef_2,
                R.string.whooperBuger,
                R.string.whooperBugerPrice,
            ),
            Pictures(
                R.drawable.veg_burger,
                R.string.VegBurger,
                R.string.VegBurgerPrice
            ),

        )
    }
}

class ChickenBurgerItems {

    fun loadChickenBurgers(): List<Pictures> {
        return listOf<Pictures>(
            Pictures(
                R.drawable.zingerburger,
                R.string.zingerBurger,
                R.string.zingerBurgerPrice,
            ),
            Pictures(
                R.drawable.loadedcheeseburger,
                R.string.loadedCheeseburger,
                R.string.loadedCheeseburgerPrice,
            ),
            Pictures(
                R.drawable.tandooriburger,
                R.string.tandooriBurger,
                R.string.tandooriBurgerPrice,
            ),
            Pictures(
                R.drawable.nashvilleburger,
                R.string.nashvilleBurger,
                R.string.nashvilleBurgerPrice
            ),
            Pictures(
                R.drawable.bbqbeefburger,
                R.string.bbqBaconBurger,
                R.string.bbqBaconBurgerPrice,
            ),
            Pictures(
                R.drawable.westernwhopper,
                R.string.WesternWhopperBurger,
                R.string.WesternWhopperBurgerPrice,
            ),
            Pictures(
                R.drawable.cheesybeefburger,
                R.string.loadedCheeseBeefBurger,
                R.string.loadedCheeseBeefBurgerPrice,
            ),
            Pictures(
                R.drawable.mushrrombeefburger,
                R.string.DoubleMushroomSwissBurger,
                R.string.DoubleMushroomSwissBurgerPrice
            ),
            Pictures(
                R.drawable.vegburger1,
                R.string.classicVeggieBurger,
                R.string.classicVeggieBurgerPrice,
            ),
            Pictures(
                R.drawable.vebburger2,
                R.string.spicyVeggieBurger,
                R.string.spicyVeggieBurgerPrice,
            ),
            Pictures(
                R.drawable.vegburger3,
                R.string.gardenFreshBurger,
                R.string.gardenFreshBurgerPrice,
            ),
            Pictures(
                R.drawable.vegbuger4,
                R.string.cheesyVegDelightBurger,
                R.string.cheesyVegDelightBurgerPrice
            ),


            )
    }
}

class BeefBurgerItems {

    fun loadBeefBurgers(): List<Pictures> {
        return listOf<Pictures>(
            Pictures(
                R.drawable.bbqbeefburger,
                R.string.bbqBaconBurger,
                R.string.bbqBaconBurgerPrice,
            ),
            Pictures(
                R.drawable.westernwhopper,
                R.string.WesternWhopperBurger,
                R.string.WesternWhopperBurgerPrice,
            ),
            Pictures(
                R.drawable.cheesybeefburger,
                R.string.loadedCheeseBeefBurger,
                R.string.loadedCheeseBeefBurgerPrice,
            ),
            Pictures(
                R.drawable.mushrrombeefburger,
                R.string.DoubleMushroomSwissBurger,
                R.string.DoubleMushroomSwissBurgerPrice
            ),

            )
    }
}

class VegBurgerItems {

    fun loadVegBurgers(): List<Pictures> {
        return listOf<Pictures>(
            Pictures(
                R.drawable.vegburger1,
                R.string.classicVeggieBurger,
                R.string.classicVeggieBurgerPrice,
            ),
            Pictures(
                R.drawable.vebburger2,
                R.string.spicyVeggieBurger,
                R.string.spicyVeggieBurgerPrice,
            ),
            Pictures(
                R.drawable.vegburger3,
                R.string.gardenFreshBurger,
                R.string.gardenFreshBurgerPrice,
            ),
            Pictures(
                R.drawable.vegbuger4,
                R.string.cheesyVegDelightBurger,
                R.string.cheesyVegDelightBurgerPrice
            ),
            )
      }
}



class BeverageData {
    fun loadBeverages(): List<Pictures> {
        return listOf<Pictures>(
            Pictures(
                R.drawable.pineapple,
                R.string.Pinaplle,
                R.string.ringsPrice
            ),
            Pictures(
                R.drawable.watermelon,
                R.string.watermellon,
                R.string.ringsPrice

            ),
            Pictures(
                R.drawable.grapes,
                R.string.grapes,
                R.string.ringsPrice

            ),
            Pictures(
                R.drawable.lime,
                R.string.Lime,
                R.string.ringsPrice

            ),
            Pictures(
                R.drawable.pasion,
                R.string.Pasion,
                R.string.ringsPrice

            ),
            Pictures(
                R.drawable.orange,
                R.string.Orange,
                R.string.ringsPrice

            ),
            Pictures(
                R.drawable.apple,
                R.string.Apple,
                R.string.ringsPrice

            ),

        )
    }
}

class FastFoodData {
    fun loadFastFood(): List<Pictures> {
        return listOf<Pictures>(
            Pictures(
                R.drawable.fries,
                R.string.Fries,
                R.string.FriesPrice
            ), Pictures(
                R.drawable.rings,
                R.string.rings,
                R.string.ringsPrice

            ), Pictures(
                R.drawable.wegus,
                R.string.Wegus,
                R.string.WegusPrice

            ),
            Pictures(
                R.drawable.breads,
                R.string.breads,
                R.string.breadsPrice

            ),
            Pictures(
                R.drawable.taccos,
                R.string.taccos,
                R.string.taccosPrice

            ),
            Pictures(
                R.drawable.shawrma,
                R.string.shawarma,
                R.string.shawarmaPrice
            ),
            )
    }
}



class Category {
    fun loadCategory(): List<CategoryPictuers> {
        return listOf<CategoryPictuers>(
            CategoryPictuers(
                R.drawable.chicken_burger,
                R.string.Burgers,
            ),
            CategoryPictuers(
                R.drawable.bannerpic2,
                R.string.FastFood,
            ),
            CategoryPictuers(
                R.drawable.juices,
                R.string.Juices,
            ),
            CategoryPictuers(
                R.drawable.beverage,
                R.string.Beverages,
            ),
        )
    }
}
