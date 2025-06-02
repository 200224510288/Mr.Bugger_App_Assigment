package com.example.mrbugger_app.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mrbugger_app.APIService.MealApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor() : ViewModel() {

    private val apiService: MealApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MealApiService::class.java)
    }

    private val _uiState = MutableStateFlow(RecipeUiState())
    val uiState: StateFlow<RecipeUiState> = _uiState.asStateFlow()

    private val _selectedMeal = MutableStateFlow<Meal?>(null)
    val selectedMeal: StateFlow<Meal?> = _selectedMeal.asStateFlow()

    // Store the last search query for retry functionality
    private var lastSearchQuery: String = ""

    fun searchMeals(query: String) {
        if (query.isBlank()) {
            _uiState.value = _uiState.value.copy(
                meals = emptyList(),
                isLoading = false,
                error = null,
                isOffline = false
            )
            return
        }

        lastSearchQuery = query
        performSearch(query)
    }

    fun retryLastSearch() {
        if (lastSearchQuery.isNotBlank()) {
            performSearch(lastSearchQuery)
        }
    }

    private fun performSearch(query: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null,
                isOffline = false
            )

            try {
                val response = apiService.searchMealsByName(query)
                if (response.isSuccessful) {
                    val meals = response.body()?.meals ?: emptyList()
                    _uiState.value = _uiState.value.copy(
                        meals = meals,
                        isLoading = false,
                        error = null,
                        isOffline = false
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        meals = emptyList(),
                        isLoading = false,
                        error = "Failed to fetch meals: ${response.message()}",
                        isOffline = false
                    )
                }
            } catch (e: Exception) {
                val isNetworkError = e is UnknownHostException ||
                        e.message?.contains("Unable to resolve host") == true ||
                        e.message?.contains("No address associated with hostname") == true

                _uiState.value = _uiState.value.copy(
                    meals = emptyList(),
                    isLoading = false,
                    error = if (isNetworkError) "No internet connection" else e.message,
                    isOffline = isNetworkError
                )
            }
        }
    }
}

data class RecipeUiState(
    val meals: List<Meal> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isOffline: Boolean = false
)