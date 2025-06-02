package com.example.mrbugger_app.APIService

import com.example.mrbugger_app.model.MealResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApiService {
    @GET("search.php")
    suspend fun searchMealsByName(@Query("s") mealName: String): Response<MealResponse>

    @GET("lookup.php")
    suspend fun getMealById(@Query("i") id: String): Response<MealResponse>
}