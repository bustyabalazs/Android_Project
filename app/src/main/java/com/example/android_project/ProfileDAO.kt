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

    @Query("SELECT * FROM profile_table WHERE id=1")
    fun readAllData(): LiveData<Profile>//TODO List<Restaurant>?

    @Query("SELECT * FROM restaurants_table")
    fun readRestaurants(): LiveData<List<RestaurantTable>>//TODO List<Restaurant>?

    @Delete(entity = RestaurantTable::class)
    fun deleteRestaurant(restaurant: RestaurantTable)//TODO List<Restaurant>?
}