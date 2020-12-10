package com.example.android_project

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

//data access object
@Dao
interface ProfileDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addProfile(profile: Profile)

    @Query("SELECT favoriteRestaurants FROM profile_table")
    fun readAllRestaurants(): LiveData<Restaurants>//TODO List<Restaurant>?
}