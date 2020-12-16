package com.example.android_project

import android.app.Application
import android.net.Uri
import android.util.Log.d
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URI


class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    val readProfile: LiveData<Profile>
    val readRestaurants: LiveData<List<RestaurantTable>>
    private val repository: ProfileRepository
    var profilePicture: Uri? = null

    init {
        val profileDAO = ProfileDatabase.getDatabase(application).profileDao()
        repository = ProfileRepository(profileDAO)
        readProfile = repository.readProfile
        readRestaurants=repository.readRestaurants
    }

    fun addProfile(profile: Profile) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addProfile(profile)
        }
    }

    fun addRestaurant(restaurant: RestaurantTable) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addRestaurant(restaurant)
        }
    }
    fun updateProfile(profile: Profile) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateProfile(profile)
        }
    }
    fun deleteRestaurant(restaurant: RestaurantTable) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteRestaurant(restaurant)
        }
    }
}