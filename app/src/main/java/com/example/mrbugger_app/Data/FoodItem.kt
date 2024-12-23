package com.example.mrbugger_app.Data

import androidx.compose.runtime.Immutable
import com.example.mrbugger_app.R
import com.example.mrbugger_app.model.Pictures

class DataSource {

    fun loadPictures(): List<Pictures> {
        return listOf(
            Pictures(R.drawable.chicken_burger, R.string.product1_name, 800.00),
            Pictures(R.drawable.beef_burger, R.string.product2_name, 900.00),
            Pictures(R.drawable.veg_burger, R.string.product3_name, 450.00),
            Pictures(R.drawable.whooper_buger, R.string.product4_name, 1150.00)

        )
    }


}
