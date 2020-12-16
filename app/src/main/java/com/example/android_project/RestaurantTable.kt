package com.example.android_project

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restaurants_table")
data class RestaurantTable (
    @PrimaryKey(autoGenerate = false)
    val id:Int,
    val name:String,
    val address:String,
    val city:String,
    val state:String,
    val area:String,
    val postal_code:String,
    val country:String,
    val phone:String,
    val lat: Float,
    val lng: Float,
    val price: Float,
    val reserve_url:String,
    val mobile_reserve_url:String,
    val image_url:String
)

@Entity(tableName = "restaurants_images")
data class RestaurantImages (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val restaurant_id:Int,
    val image_url:String
)