package com.example.mrbugger_app.Data

import com.example.mrbugger_app.R

data class Category(
    val name: String,
    val imageRes: Int
)

val categoryList = listOf(
    Category("All", R.drawable.whooper_buger),
    Category("Chicken", R.drawable.chicken_burger),
    Category("Beef", R.drawable.beef_burger),
    Category("Veg", R.drawable.veg_burger)
)