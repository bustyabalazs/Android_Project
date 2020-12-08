package com.example.android_project

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenTableEndPoints {

    @GET("restaurants")
    //fun getRestaurants(@Query("restaurants") key: String): Call<Restaurants>
  //  d("danial","here we are")
    fun getRestaurants(@Query("country") country :String): Call<Restaurants>

}