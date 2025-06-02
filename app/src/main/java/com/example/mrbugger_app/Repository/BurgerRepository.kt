package com.example.mrbugger_app.Repository

import android.content.Context
import android.util.Log
import com.example.mrbugger_app.APIService.NetworkClient
import com.example.mrbugger_app.model.BurgerModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader

class BurgerRepository(private val context: Context) {

    companion object {
        private const val TAG = "BurgerRepository"
    }

    suspend fun loadBurgersRemote(isOnline: Boolean): Result<List<BurgerModel>> {
        return try {
            if (isOnline) {
                Log.d(TAG, "Attempting to load burgers from remote API")
                val response = NetworkClient.apiService.getBurgers()
                Log.d(TAG, "Successfully loaded ${response.burgers.size} burgers from API")
                Result.success(response.burgers)
            } else {
                Log.d(TAG, "Device is offline, skipping remote call")
                Result.failure(Exception("No internet connection"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error loading remote burgers: ${e.message}", e)
            Result.failure(e)
        }
    }

    fun loadLocalBurgers(): List<BurgerModel> {
        return try {
            Log.d(TAG, "Attempting to load local burgers from assets/burgers.json")

            // Check if the asset file exists
            val assetFiles = context.assets.list("") ?: emptyArray()
            Log.d(TAG, "Available asset files: ${assetFiles.joinToString(", ")}")

            val inputStream = context.assets.open("burgers.json")
            val reader = InputStreamReader(inputStream)
            val jsonContent = reader.readText()

            Log.d(TAG, "JSON content loaded, length: ${jsonContent.length}")
            Log.d(TAG, "JSON content preview: ${jsonContent.take(100)}...")

            val gson = Gson()
            val result = try {
                // First, try to parse as a direct array
                val arrayType = object : TypeToken<List<BurgerModel>>() {}.type
                gson.fromJson<List<BurgerModel>>(jsonContent, arrayType)
            } catch (e: Exception) {
                Log.d(TAG, "Failed to parse as array, trying as object with 'burgers' property")

                // If that fails, try to parse as an object with a "burgers" property
                val jsonObject = gson.fromJson(jsonContent, JsonObject::class.java)

                // Check if the object has a "burgers" array property
                if (jsonObject.has("burgers") && jsonObject.get("burgers").isJsonArray) {
                    val burgersArray = jsonObject.getAsJsonArray("burgers")
                    val arrayType = object : TypeToken<List<BurgerModel>>() {}.type
                    gson.fromJson<List<BurgerModel>>(burgersArray, arrayType)
                } else {
                    Log.e(TAG, "JSON structure not recognized. Expected either an array or object with 'burgers' property")
                    emptyList()
                }
            }

            // Close streams
            reader.close()
            inputStream.close()

            val finalResult = result ?: emptyList()
            Log.d(TAG, "Successfully loaded ${finalResult.size} burgers from assets")
            finalResult
        } catch (e: Exception) {
            Log.e(TAG, "Error loading local burgers from assets: ${e.message}", e)
            Log.e(TAG, "Stack trace: ", e)
            emptyList()
        }
    }
}
