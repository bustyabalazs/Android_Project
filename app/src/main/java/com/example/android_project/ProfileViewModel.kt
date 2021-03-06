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
    val repository: ProfileRepository
    var profilePicture: Uri? = null

    init {
        val profileDAO = ProfileDatabase.getDatabase(application).profileDao()
        repository = ProfileRepository(profileDAO)
        readProfile = repository.readProfile
        readRestaurants=repository.readRestaurants
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
    fun addImage(images: RestaurantImages) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addImage(images)
        }
    }
    fun deleteRestaurant(restaurant: RestaurantTable) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteRestaurant(restaurant)
        }
    }
    fun deleteImage(restaurantImage: RestaurantImages) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteImage(restaurantImage)
        }
    }
}