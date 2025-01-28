package com.example.mrbugger_app.model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UserProfileViewModel : ViewModel()
{
    private val userProfileData = mutableStateOf(
        User(
            username = "Colonel Harland",
            password = "*********",
            email = "Colonel@mail.com",
            contact = "+94 74 897 098",
            address = "204, wall street, Kandy"
        )
    )

    val userProfile = userProfileData

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