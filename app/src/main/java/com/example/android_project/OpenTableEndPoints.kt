package com.example.android_project

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenTableEndPoints {

    @GET("GET /api/restaurants")
    fun getRestaurants(@Query("api_key") key: String): Call<Restaurants>

}