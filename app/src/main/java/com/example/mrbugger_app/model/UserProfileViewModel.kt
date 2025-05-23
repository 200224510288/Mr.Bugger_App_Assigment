package com.example.mrbugger_app.model

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

class UserProfileViewModel(application: Application) : AndroidViewModel(application) {

    // SharedPreferences for data persistence
    private val sharedPreferences = getApplication<Application>()
        .getSharedPreferences("user_profile", Context.MODE_PRIVATE)

    // User profile state
    private val userProfileData = mutableStateOf(
        User(
            username = "Colonel Harland",
            password = "*********",
            email = "Colonel@gmail.com",
            contact = "+94 - 74 1248950",
            address = "204, Wall Street, Colombo"
        )
    )

    val userProfile = userProfileData

    // Profile image states
    var profileImageBitmap = mutableStateOf<Bitmap?>(null)
    var profileImageUri = mutableStateOf<Uri?>(null)

    // Loading state
    var isLoading = mutableStateOf(false)

    init {
        loadUserData()
        loadProfileImage()
    }

    // Load user data from SharedPreferences
    private fun loadUserData() {
        val username = sharedPreferences.getString("username", "Colonel Harland") ?: "Colonel Harland"
        val password = sharedPreferences.getString("password", "*********") ?: "*********"
        val email = sharedPreferences.getString("email", "Colonel@gmail.com") ?: "Colonel@gmail.com"
        val contact = sharedPreferences.getString("contact", "+94 - 74 1248950") ?: "+94 - 74 1248950"
        val address = sharedPreferences.getString("address", "204, Wall Street, Colombo") ?: "204, Wall Street, Colombo"

        userProfileData.value = User(
            username = username,
            password = password,
            email = email,
            contact = contact,
            address = address
        )
    }

    // Update user profile data with persistence
    fun updateUserData(username: String, password: String, email: String, contact: String, address: String) {
        viewModelScope.launch {
            isLoading.value = true

            // Update the state
            userProfileData.value = User(
                username = username,
                password = password,
                email = email,
                contact = contact,
                address = address
            )

            // Save to SharedPreferences
            saveUserData(username, password, email, contact, address)

            isLoading.value = false
        }
    }

    // Save user data to SharedPreferences
    private fun saveUserData(username: String, password: String, email: String, contact: String, address: String) {
        val editor = sharedPreferences.edit()
        editor.putString("username", username)
        editor.putString("password", password)
        editor.putString("email", email)
        editor.putString("contact", contact)
        editor.putString("address", address)
        editor.apply()
    }

    // Update profile image from URI (for gallery selection)
    // FIXED: Now converts URI to bitmap and saves it permanently
    fun updateProfileImage(uri: Uri) {
        viewModelScope.launch {
            try {
                // Convert URI to bitmap and save it to internal storage
                val bitmap = uriToBitmap(uri)
                if (bitmap != null) {
                    profileImageBitmap.value = bitmap
                    saveProfileImageToStorage(bitmap)
                    // Clear URI since we're now using the saved bitmap
                    profileImageUri.value = null
                    clearProfileImageUri()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Update profile image from Bitmap (for camera capture)
    fun updateProfileImage(bitmap: Bitmap) {
        viewModelScope.launch {
            profileImageBitmap.value = bitmap
            saveProfileImageToStorage(bitmap)
            // Clear URI since we're using bitmap
            profileImageUri.value = null
            clearProfileImageUri()
        }
    }

    // Convert URI to Bitmap
    private fun uriToBitmap(uri: Uri): Bitmap? {
        return try {
            val inputStream: InputStream? = getApplication<Application>().contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Save profile image URI to SharedPreferences (keeping for backward compatibility)
    private fun saveProfileImageUri(uriString: String) {
        val editor = sharedPreferences.edit()
        editor.putString("profile_image_uri", uriString)
        editor.apply()
    }

    // Clear profile image URI from SharedPreferences
    private fun clearProfileImageUri() {
        val editor = sharedPreferences.edit()
        editor.remove("profile_image_uri")
        editor.apply()
    }

    // Save profile image bitmap to internal storage
    private fun saveProfileImageToStorage(bitmap: Bitmap) {
        try {
            val filePath = saveBitmapToFile(bitmap)
            val editor = sharedPreferences.edit()
            editor.putString("profile_image_path", filePath)
            editor.apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Save bitmap to file
    private fun saveBitmapToFile(bitmap: Bitmap): String {
        val file = File(getApplication<Application>().filesDir, "profile_image.jpg")
        try {
            val fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos)
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file.absolutePath
    }

    // FIXED: Load profile image from storage
    private fun loadProfileImage() {
        // First try to load from bitmap file (most reliable)
        val imagePath = sharedPreferences.getString("profile_image_path", null)
        if (imagePath != null) {
            val file = File(imagePath)
            if (file.exists()) {
                profileImageBitmap.value = BitmapFactory.decodeFile(imagePath)
                return
            }
        }

        // Fallback: try to load from URI (less reliable after app restart)
        val uriString = sharedPreferences.getString("profile_image_uri", null)
        if (uriString != null) {
            try {
                val uri = Uri.parse(uriString)
                val bitmap = uriToBitmap(uri)
                if (bitmap != null) {
                    // Convert URI to bitmap and save it for future use
                    profileImageBitmap.value = bitmap
                    saveProfileImageToStorage(bitmap)
                    // Clear the URI since we now have it saved as bitmap
                    clearProfileImageUri()
                } else {
                    // URI is invalid, clear it
                    clearProfileImageUri()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Clear invalid URI
                clearProfileImageUri()
            }
        }
    }

    // Clear profile image
    fun clearProfileImage() {
        viewModelScope.launch {
            profileImageUri.value = null
            profileImageBitmap.value = null

            // Clear from SharedPreferences
            val editor = sharedPreferences.edit()
            editor.remove("profile_image_uri")
            editor.remove("profile_image_path")
            editor.apply()

            // Delete file if exists
            val file = File(getApplication<Application>().filesDir, "profile_image.jpg")
            if (file.exists()) {
                file.delete()
            }
        }
    }

    // Validate user data
    fun validateUserData(): Pair<Boolean, String> {
        val user = userProfile.value

        return when {
            user.username.isBlank() -> Pair(false, "Username cannot be empty")
            user.email.isBlank() -> Pair(false, "Email cannot be empty")
            !isValidEmail(user.email) -> Pair(false, "Please enter a valid email")
            user.contact.isBlank() -> Pair(false, "Contact cannot be empty")
            user.address.isBlank() -> Pair(false, "Address cannot be empty")
            else -> Pair(true, "Valid")
        }
    }

    // Email validation helper
    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Reset to default data
    fun resetToDefaults() {
        viewModelScope.launch {
            clearProfileImage()

            userProfileData.value = User(
                username = "Colonel Harland",
                password = "*********",
                email = "Colonel@gmail.com",
                contact = "+94 - 74 1248950",
                address = "204, Wall Street, Colombo"
            )

            // Clear SharedPreferences
            sharedPreferences.edit().clear().apply()
        }
    }
}