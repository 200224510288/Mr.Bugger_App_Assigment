package com.example.mrbugger_app.model

import android.app.Application
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel

class ThemeViewModel(application: Application) : AndroidViewModel(application) {

    // SharedPreferences for theme persistence
    private val sharedPreferences = getApplication<Application>()
        .getSharedPreferences("app_theme", Context.MODE_PRIVATE)

    private val _isDarkMode = mutableStateOf(false)
    val isDarkMode: State<Boolean> = _isDarkMode

    init {
        loadThemePreference()
    }

    // Load theme preference from SharedPreferences
    private fun loadThemePreference() {
        _isDarkMode.value = sharedPreferences.getBoolean("is_dark_mode", false)
    }

    // Save theme preference to SharedPreferences
    private fun saveThemePreference(isDark: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("is_dark_mode", isDark)
        editor.apply()
    }

    fun toggleTheme() {
        val newTheme = !_isDarkMode.value
        _isDarkMode.value = newTheme
        saveThemePreference(newTheme)
    }

    fun setTheme(isDark: Boolean) {
        _isDarkMode.value = isDark
        saveThemePreference(isDark)
    }
}