package com.example.android_project

import androidx.lifecycle.LiveData

class ProfileRepository(val profileDAO: ProfileDAO,private val id:Int=0) {
    val readProfile:LiveData<Profile> = profileDAO.readAllData()
    val readRestaurants:LiveData<List<RestaurantTable>> =profileDAO.readRestaurants()

    suspend fun  addProfile(profile: Profile){
        profileDAO.addProfile(profile)
    }
    suspend fun  addRestaurant(restaurant: RestaurantTable){
        profileDAO.addRestaurant(restaurant)
    }
    suspend fun  addImage(restaurantImage: RestaurantImages){
        profileDAO.addImage(restaurantImage)
    }
    suspend fun updateProfile(profile: Profile){
        profileDAO.updateProfile(profile)
    }
    suspend fun deleteRestaurant(restaurant: RestaurantTable){
        profileDAO.deleteRestaurant(restaurant)
    }
    suspend fun deleteImage(restaurantImage: RestaurantImages){
        profileDAO.deleteImage(restaurantImage)
    }
}