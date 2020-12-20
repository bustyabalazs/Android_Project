package com.example.android_project

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenTableEndPoints {

    @GET("restaurants")
    fun getRestaurantsPage(@Query("country") country :String,@Query("page") page :Int=1): Call<Restaurants>
    @GET("restaurants")
    fun getRestaurantsName(@Query("country") country :String,@Query("page") page :Int=1,@Query("name") name :String): Call<Restaurants>
    @GET("restaurants")
    fun getRestaurantsCity(@Query("country") country :String,@Query("page") page :Int=1,@Query("city") city :String): Call<Restaurants>
    @GET("restaurants")
    fun getRestaurantsPrice(@Query("country") country :String,@Query("page") page :Int=1,@Query("price") price :Int): Call<Restaurants>
}