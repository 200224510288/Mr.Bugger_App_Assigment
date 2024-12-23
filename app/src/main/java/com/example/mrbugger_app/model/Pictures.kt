package com.example.mrbugger_app.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Pictures(
    @DrawableRes val imageResourceId: Int,
    @StringRes val stringResourceId: Int,
    val price: Double
)
