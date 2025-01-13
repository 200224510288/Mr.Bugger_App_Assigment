package com.example.mrbugger_app.Data

import androidx.annotation.DrawableRes
import com.example.mrbugger_app.R
import com.example.mrbugger_app.model.CategoryPictuers
import com.example.mrbugger_app.model.Pictures

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
    fun loadBeverages(): List<CategoryPictuers> {
        return listOf<CategoryPictuers>(
            CategoryPictuers(
                R.drawable.beef_burger,
                R.string.avacado,
            ),
            CategoryPictuers(
                R.drawable.beverage_1,
                R.string.avacado,
            ),
            CategoryPictuers(
                R.drawable.beverage_1,
                R.string.avacado,
            ),
            CategoryPictuers(
                R.drawable.beverage_1,
                R.string.avacado,
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
