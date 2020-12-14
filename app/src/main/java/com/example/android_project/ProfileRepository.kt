package com.example.android_project

import androidx.lifecycle.LiveData

class ProfileRepository(private val profileDAO: ProfileDAO) {
    val readProfile:LiveData<Profile> = profileDAO.readAllData()
    val readRestaurants:LiveData<List<RestaurantTable>> =profileDAO.readRestaurants()

    suspend fun  addProfile(profile: Profile){
        profileDAO.addProfile(profile)
    }
    suspend fun  addRestaurant(restaurant: RestaurantTable){
        profileDAO.addRestaurant(restaurant)
    }
    suspend fun updateProfile(profile: Profile){
        profileDAO.updateProfile(profile)
    }
    suspend fun deleteRestaurant(restaurant: RestaurantTable){
        profileDAO.deleteRestaurant(restaurant)
    }
}