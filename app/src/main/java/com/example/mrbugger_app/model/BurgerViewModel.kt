package com.example.mrbugger_app.model

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mrbugger_app.Repository.BurgerRepository
import com.example.mrbugger_app.state.BurgerUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BurgerViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow<BurgerUiState>(BurgerUiState.Loading)
    val uiState: StateFlow<BurgerUiState> = _uiState

    private val repository = BurgerRepository(application)

    init {
        loadBurgers()
    }
    fun refreshData() {
        loadBurgers()
    }

    fun loadBurgers() {
        viewModelScope.launch {
            _uiState.value = BurgerUiState.Loading
            delay(2000)
            val isOnline = isNetworkAvailable()

            if (isOnline) {
                // Try to load from remote first when online
                try {
                    val result = repository.loadBurgersRemote(true)
                    val burgers = result.getOrNull()

                    if (burgers != null && burgers.isNotEmpty()) {
                        _uiState.value = BurgerUiState.Success(
                            burgers = burgers,
                            isOffline = false
                        )
                        return@launch
                    }
                } catch (e: Exception) {
                    // Log error but continue to try local data
                    android.util.Log.w("BurgerViewModel", "Remote loading failed, trying local", e)
                }

                // If remote fails while online, fallback to local
                loadLocalBurgers(isOffline = false)
            } else {
                // Device is offline, load from local assets directly
                loadLocalBurgers(isOffline = true)
            }
        }
    }

    private fun loadLocalBurgers(isOffline: Boolean) {
        try {
            val localBurgers = repository.loadLocalBurgers()
            if (localBurgers.isNotEmpty()) {
                _uiState.value = BurgerUiState.Success(
                    burgers = localBurgers,
                    isOffline = isOffline
                )
            } else {
                _uiState.value = BurgerUiState.Error(
                    message = "No burgers available. Please check your assets folder.",
                    isOffline = isOffline
                )
            }
        } catch (e: Exception) {
            _uiState.value = BurgerUiState.Error(
                message = "Failed to load local data: ${e.localizedMessage ?: "Unknown error"}",
                isOffline = isOffline
            )
        }
    }

    private fun isNetworkAvailable(): Boolean {
        return try {
            val connectivityManager = getApplication<Application>()
                .getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            val network = connectivityManager?.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            val hasInternet = capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)

            android.util.Log.d("BurgerViewModel", "Network available: $hasInternet")
            hasInternet
        } catch (e: Exception) {
            android.util.Log.e("BurgerViewModel", "Error checking network availability", e)
            false
        }
    }
}

// BurgerViewModelFactory.kt
class BurgerViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BurgerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BurgerViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


