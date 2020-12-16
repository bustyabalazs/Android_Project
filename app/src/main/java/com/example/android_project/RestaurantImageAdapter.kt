package com.example.android_project

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

//import com.example.android_project.fragment.clickedItem

class RestaurantImageAdapter(var restaurantImages: List<RestaurantImages>) :
    RecyclerView.Adapter<RestaurantImageAdapter.RestaurantImageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_items, parent, false)
        return RestaurantImageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return restaurantImages.size
    }

    override fun onBindViewHolder(holder: RestaurantImageAdapter.RestaurantImageViewHolder, position: Int) {
        return holder.bind(restaurantImages[position])
    }

    class RestaurantImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val photo: ImageView = itemView.findViewById(R.id.restaurant_photo)
        private val name: TextView = itemView.findViewById(R.id.name)
        private val address: TextView = itemView.findViewById(R.id.address)
        private val phone: TextView = itemView.findViewById(R.id.phone)
        val favouriteButton: AppCompatImageButton = itemView.findViewById(R.id.fav)

        fun bind(restaurantImage: RestaurantImages) {
            Glide.with(itemView.context).load(restaurantImage.image_url).into(photo)
        }
    }
}



