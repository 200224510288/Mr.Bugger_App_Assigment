package com.example.mrbugger_app.APIService

import com.example.mrbugger_app.model.CategoryResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// ApiService.kt
interface ApiService {
    @GET("categories.json")
    suspend fun getCategories(): CategoryResponse
}

// NetworkClient.kt
object NetworkClient {
    private const val BASE_URL = "https://raw.githubusercontent.com/200224510288/Mr.Burgger_Server/main/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}