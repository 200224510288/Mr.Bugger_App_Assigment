package com.example.mrbugger_app.Repository

import android.content.Context
import android.util.Log
import com.example.mrbugger_app.APIService.NetworkClient
import com.example.mrbugger_app.model.FastFoodModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader

class FastFoodsRepository(private val context: Context) {

    companion object {
        private const val TAG = "FastFoodsRepository"
    }

    suspend fun loadFastFoodsRemote(isOnline: Boolean): Result<List<FastFoodModel>> {
        return try {
            if (isOnline) {
                Log.d(TAG, "Attempting to load fast foods from remote API")
                val response = NetworkClient.apiService.getFastFoods()
                Log.d(TAG, "Successfully loaded ${response.fastFoods.size} fast foods from API")
                Result.success(response.fastFoods)
            } else {
                Log.d(TAG, "Device is offline, skipping remote call")
                Result.failure(Exception("No internet connection"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error loading remote fast foods: ${e.message}", e)
            Result.failure(e)
        }
    }

    fun loadLocalFastFoods(): List<FastFoodModel> {
        return try {
            Log.d(TAG, "Attempting to load local fast foods from assets/fastfoods.json")

            // Check if the asset file exists
            val assetFiles = context.assets.list("") ?: emptyArray()
            Log.d(TAG, "Available asset files: ${assetFiles.joinToString(", ")}")

            val inputStream = context.assets.open("fastfoods.json")
            val reader = InputStreamReader(inputStream)
            val jsonContent = reader.readText()

            Log.d(TAG, "JSON content loaded, length: ${jsonContent.length}")
            Log.d(TAG, "JSON content preview: ${jsonContent.take(100)}...")

            val gson = Gson()
            val result = try {
                // First, try to parse as a direct array
                val arrayType = object : TypeToken<List<FastFoodModel>>() {}.type
                gson.fromJson<List<FastFoodModel>>(jsonContent, arrayType)
            } catch (e: Exception) {
                Log.d(TAG, "Failed to parse as array, trying as object with 'fastFoods' property")

                // If that fails, try to parse as an object with a "fastFoods" property
                val jsonObject = gson.fromJson(jsonContent, JsonObject::class.java)

                // Check if the object has a "fastFoods" array property
                if (jsonObject.has("fastFoods") && jsonObject.get("fastFoods").isJsonArray) {
                    val fastFoodsArray = jsonObject.getAsJsonArray("fastFoods")
                    val arrayType = object : TypeToken<List<FastFoodModel>>() {}.type
                    gson.fromJson<List<FastFoodModel>>(fastFoodsArray, arrayType)
                } else {
                    Log.e(TAG, "JSON structure not recognized. Expected either an array or object with 'fastFoods' property")
                    emptyList()
                }
            }

            // Close streams
            reader.close()
            inputStream.close()

            val finalResult = result ?: emptyList()
            Log.d(TAG, "Successfully loaded ${finalResult.size} fast foods from assets")
            finalResult
        } catch (e: Exception) {
            Log.e(TAG, "Error loading local fast foods from assets: ${e.message}", e)
            Log.e(TAG, "Stack trace: ", e)
            emptyList()
        }
    }
}