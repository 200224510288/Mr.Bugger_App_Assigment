package com.example.mrbugger_app.Repository

import android.content.Context
import android.util.Log
import com.example.mrbugger_app.APIService.NetworkClient
import com.example.mrbugger_app.model.BeverageModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader

class BeverageRepository(private val context: Context) {

    companion object {
        private const val TAG = "BeverageRepository"
    }

    suspend fun loadBeveragesRemote(isOnline: Boolean): Result<List<BeverageModel>> {
        return try {
            if (isOnline) {
                Log.d(TAG, "Attempting to load beverages from remote API")
                val response = NetworkClient.apiService.getBeverages()
                Log.d(TAG, "Successfully loaded ${response.beverages.size} beverages from API")
                Result.success(response.beverages)
            } else {
                Log.d(TAG, "Device is offline, skipping remote call")
                Result.failure(Exception("No internet connection"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error loading remote beverages: ${e.message}", e)
            Result.failure(e)
        }
    }

    fun loadLocalBeverages(): List<BeverageModel> {
        return try {
            Log.d(TAG, "Attempting to load local beverages from assets/beverages.json")

            // Check if the asset file exists
            val assetFiles = context.assets.list("") ?: emptyArray()
            Log.d(TAG, "Available asset files: ${assetFiles.joinToString(", ")}")

            val inputStream = context.assets.open("beverages.json")
            val reader = InputStreamReader(inputStream)
            val jsonContent = reader.readText()

            Log.d(TAG, "JSON content loaded, length: ${jsonContent.length}")
            Log.d(TAG, "JSON content preview: ${jsonContent.take(100)}...")

            val gson = Gson()
            val result = try {
                // First, try to parse as a direct array
                val arrayType = object : TypeToken<List<BeverageModel>>() {}.type
                gson.fromJson<List<BeverageModel>>(jsonContent, arrayType)
            } catch (e: Exception) {
                Log.d(TAG, "Failed to parse as array, trying as object with 'beverages' property")

                // If that fails, try to parse as an object with a "beverages" property
                val jsonObject = gson.fromJson(jsonContent, JsonObject::class.java)

                // Check if the object has a "beverages" array property
                if (jsonObject.has("beverages") && jsonObject.get("beverages").isJsonArray) {
                    val beveragesArray = jsonObject.getAsJsonArray("beverages")
                    val arrayType = object : TypeToken<List<BeverageModel>>() {}.type
                    gson.fromJson<List<BeverageModel>>(beveragesArray, arrayType)
                } else {
                    Log.e(TAG, "JSON structure not recognized. Expected either an array or object with 'beverages' property")
                    emptyList()
                }
            }

            // Close streams
            reader.close()
            inputStream.close()

            val finalResult = result ?: emptyList()
            Log.d(TAG, "Successfully loaded ${finalResult.size} beverages from assets")
            finalResult
        } catch (e: Exception) {
            Log.e(TAG, "Error loading local beverages from assets: ${e.message}", e)
            Log.e(TAG, "Stack trace: ", e)
            emptyList()
        }
    }
}