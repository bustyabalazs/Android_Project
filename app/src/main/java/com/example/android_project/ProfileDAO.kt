package com.example.android_project

import androidx.lifecycle.LiveData
import androidx.room.*

//data access object
@Dao
interface ProfileDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addProfile(profile: Profile)

    @Update(entity = Profile::class)
    suspend fun updateProfile(profile: Profile)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRestaurant(restaurant: RestaurantTable)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addImage(restaurantImage: RestaurantImages)

    @Query("SELECT * FROM profile_table WHERE id=1")
    fun readAllData(): LiveData<Profile>

    @Query("SELECT * FROM restaurant_images WHERE restaurant_id=:id")
    fun readImages(id:Int): LiveData<List<RestaurantImages>>

    @Query("SELECT * FROM restaurants_table")
    fun readRestaurants(): LiveData<List<RestaurantTable>>

    @Delete(entity = RestaurantTable::class)
    suspend fun deleteRestaurant(restaurant: RestaurantTable)

    @Delete(entity = RestaurantImages::class)
    suspend fun deleteImage(restaurantImage: RestaurantImages)
}