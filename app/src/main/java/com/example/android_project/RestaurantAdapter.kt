package com.example.android_project

import android.content.Context
import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

//import com.example.android_project.fragment.clickedItem

class RestaurantAdapter(var restaurants: List<Restaurant>, var clickListener: ClickListener) :
    RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_items, parent, false)
        return RestaurantViewHolder(clickListener, view)
    }

    override fun getItemCount(): Int {
        return restaurants.size
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            clickListener.clickedItem(restaurants[position])
            //(Navigation.findNavController(context).navigate(R.id.action_mainScreenFragment_to_detailScreen) restaurants[position])
        }
        return holder.bind(restaurants[position])
    }

    fun addData(listItems: List<Restaurant>) {
        var size = restaurants.size
        restaurants=restaurants+listItems
        var sizeNew = restaurants.size
        notifyItemRangeChanged(size, sizeNew)
    }

    class RestaurantViewHolder(var clickListener: ClickListener, itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val photo: ImageView = itemView.findViewById(R.id.restaurant_photo)
        private val name: TextView = itemView.findViewById(R.id.name)
        private val address: TextView = itemView.findViewById(R.id.address)
        private val phone: TextView = itemView.findViewById(R.id.phone)
        private val favouriteButton: AppCompatImageButton = itemView.findViewById(R.id.fav)

        fun bind(restaurant: Restaurant) {
            Glide.with(itemView.context).load(restaurant.image_url).into(photo)
            name.text = restaurant.name
            address.text = "Address: " + restaurant.address
            phone.text = "Phone number :" + restaurant.phone

            setFavouriteButton(restaurant.isFavourite, favouriteButton)

            favouriteButton.setOnClickListener {
                clickListener.clickedFavourite(restaurant)
                restaurant.isFavourite = !restaurant.isFavourite
                setFavouriteButton(restaurant.isFavourite, favouriteButton)
            }
        }
    }
}

private fun setFavouriteButton(isFavourite: Boolean, favouriteButton: ImageButton) {
    if (isFavourite) {
        favouriteButton.setImageResource(android.R.drawable.btn_star_big_on)
    } else {
        favouriteButton.setImageResource(android.R.drawable.btn_star_big_off)
    }
}

private fun restaurantTableAdapter(restaurant: Restaurant): RestaurantTable {
    return RestaurantTable(
        restaurant.id.toInt(),
        restaurant.name,
        restaurant.address,
        restaurant.city,
        restaurant.state,
        restaurant.area,
        restaurant.postal_code,
        restaurant.country,
        restaurant.phone,
        restaurant.lat.toFloat(),
        restaurant.lng.toFloat(),
        restaurant.price.toFloat(),
        restaurant.reserve_url,
        restaurant.mobile_reserve_url,
        restaurant.image_url
    )
}

interface ClickListener {
    fun clickedItem(restaurant: Restaurant)
    fun clickedFavourite(restaurant: Restaurant)
    fun clickedToDelete(restaurantImages: RestaurantImages)
}

abstract class PaginationScrollListener() :
    RecyclerView.OnScrollListener() {

    abstract fun isLoading(): Boolean

    abstract fun loadMoreItems()
}