package com.example.android_project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainScreenFragment : Fragment() {

    val restaurants= arrayListOf<Restaurant>(Restaurant("restaurant1"),
            Restaurant("restaurant1"),
            Restaurant("restaurant1"),
            Restaurant("restaurant1"),
            Restaurant("restaurant1"),
            Restaurant("restaurant1"),
            Restaurant("restaurant1"),
            Restaurant("restaurant1"),
            Restaurant("restaurant1"),
            Restaurant("restaurant1"),
            Restaurant("restaurant1"),
            Restaurant("restaurant1"),
            Restaurant("restaurant1"))

    var restaurantAdapter: RestaurantAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view: View =inflater.inflate(R.layout.fragment_main_screen, container, false)

        restaurantAdapter= RestaurantAdapter()
        restaurantAdapter!!.setData(restaurants)

        view.findViewById<RecyclerView>(R.id.recyclerView).layoutManager=LinearLayoutManager(this.context)
        view.findViewById<RecyclerView>(R.id.recyclerView).setHasFixedSize(true)
        view.findViewById<RecyclerView>(R.id.recyclerView).adapter=restaurantAdapter
        return view
    }
}