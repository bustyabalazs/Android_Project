package com.example.android_project

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile_table")
data class Profile (
    @PrimaryKey(autoGenerate = true)
    val name:String,
    val picture: String,
    val address: String,
    val phone: Number,
    val email:String,
    val favoriteRestaurants:Restaurants
)