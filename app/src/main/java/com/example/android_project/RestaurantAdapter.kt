package com.example.android_project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class RestaurantAdapter(val movies: List<Restaurant>): RecyclerView.Adapter<RestaurantViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_items, parent, false)
        return RestaurantViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        return holder.bind(movies[position])
    }
}

    class RestaurantViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        private val photo: ImageView = itemView.findViewById(R.id.restaurant_photo)
        private val name:TextView = itemView.findViewById(R.id.name)
        private val address:TextView = itemView.findViewById(R.id.address)
        private val phone:TextView = itemView.findViewById(R.id.phone)

        fun bind(restaurant: Restaurant) {
            Glide.with(itemView.context).load(restaurant.image_url).into(photo)
            name.text = "Title: "+restaurant.name
            address.text = restaurant.address
            phone.text = "Phone number : "+restaurant.phone
        }

    }