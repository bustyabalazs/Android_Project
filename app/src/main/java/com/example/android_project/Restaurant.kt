package com.example.android_project

data class Restaurants(
        val count: Number,
        val per_page: Number,
        val current_page: Number,
        val restaurants: List<Restaurant>
)

data class Restaurant (
        val id:Number,
        val name:String,
        val address:String,
        val city:String,
        val state:String,
        val area:String,
        val postal_code:String,
        val country:String,
        val phone:String,
        val lat: Number,
        val lng: Number,
        val price: Number,
        val reserve_url:String,
        val mobile_reserve_url:String,
        val image_url:String
)