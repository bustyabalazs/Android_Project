package com.example.android_project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class RestaurantAdapter : RecyclerView.Adapter<RestaurantAdapter.RestaurantAdapterVH>() {

    var restaurantList=ArrayList<Restaurant>()

    fun setData(restaurantList:ArrayList<Restaurant>){
        this.restaurantList=restaurantList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantAdapterVH {
        return RestaurantAdapterVH(LayoutInflater.from(parent.context).inflate(R.layout.row_items,parent,false))
    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }

    override fun onBindViewHolder(holder: RestaurantAdapterVH, position: Int) {
        val restaurant=restaurantList[position]
        holder.name.text=restaurant.name
    }

    class RestaurantAdapterVH(restaurantView: View): RecyclerView.ViewHolder(restaurantView){
        val name= restaurantView.findViewById<TextView>(R.id.rtName)
    }

}