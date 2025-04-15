package com.example.mrbugger_app.model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mrbugger_app.R
import kotlinx.coroutines.launch


//Stores the user profile data
class UserProfileViewModel : ViewModel()
{
    private val userProfileData = mutableStateOf(
        User(
            username = "Colonel Harland",
            password = "*********",
            email = "Colonel@gmail.com",
            contact = "+94 - 74 1248950",
            address =  "204, Wall Street, Colombo"
        )
    )

    val userProfile = userProfileData
//update the user profile data
    fun updateUserData(username:String, password:String, email:String, contact:String, address:String){
        viewModelScope.launch {
            userProfileData.value = User(
                username = username,
                password = password,
                email = email,
                contact = contact,
                address = address
            )
        }
    }
}