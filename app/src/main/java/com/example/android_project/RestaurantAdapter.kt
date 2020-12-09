package com.example.android_project

import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import retrofit2.Callback


class RestaurantAdapter(var restaurants: List<Restaurant>, var clickListener: ClickListener): RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_items, parent, false)
        return RestaurantViewHolder(view)
    }

    override fun getItemCount(): Int {
        return restaurants.size
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.itemView.setOnClickListener{
            clickListener.clickedItem(restaurants[position])
            //(Navigation.findNavController(context).navigate(R.id.action_mainScreenFragment_to_detailScreen) restaurants[position])
        }
        return holder.bind(restaurants[position])

    }
    class RestaurantViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        private val photo: ImageView = itemView.findViewById(R.id.restaurant_photo)
        private val name:TextView = itemView.findViewById(R.id.name)
        private val address:TextView = itemView.findViewById(R.id.address)
        private val phone:TextView = itemView.findViewById(R.id.phone)

        fun bind(restaurant: Restaurant) {
            Glide.with(itemView.context).load(restaurant.image_url).into(photo)
            name.text = restaurant.name
            address.text ="Address: "+restaurant.address
            phone.text = "Phone number :"+restaurant.phone
        }
    }
}

interface ClickListener{
    fun clickedItem(restaurant: Restaurant)
}