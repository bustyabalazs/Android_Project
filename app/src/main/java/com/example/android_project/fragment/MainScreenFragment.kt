package com.example.android_project.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.view.*
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.findFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import com.example.android_project.*

//import com.example.android_project.fragment.MainScreenFragmentDirections

class
MainScreenFragment : Fragment(), ClickListener {

    private lateinit var profileViewModel: ProfileViewModel
    var restaurantAdapter: RestaurantAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val view: View = inflater.inflate(R.layout.fragment_main_screen, container, false)
        //
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        val favouriteRestaurants = profileViewModel.readRestaurants

        val request = ServiceBuilder.buildService(
            OpenTableEndPoints::class.java
        )
        val call = request.getRestaurants("US")//("https://opentable.herokuapp.com/api/")

        call.enqueue(object : Callback<Restaurants> {
            override fun onResponse(call: Call<Restaurants>, response: Response<Restaurants>) {

                if (response.isSuccessful) {
                    favouriteRestaurants.observe(viewLifecycleOwner) {
                        for (restaurant in response!!.body()!!.restaurants) {
                            restaurant.isFavourite = it.contains(restaurantTableAdapter(restaurant))
                        }
                        view.findViewById<ProgressBar>(R.id.progress_bar).visibility = View.GONE
                        view.findViewById<RecyclerView>(R.id.recyclerView).setHasFixedSize(true)
                        view.findViewById<RecyclerView>(R.id.recyclerView).layoutManager =
                            LinearLayoutManager(context)
                        view.findViewById<RecyclerView>(R.id.recyclerView).adapter =
                            RestaurantAdapter(
                                response.body()!!.restaurants,
                                this@MainScreenFragment
                            )
                    }

                }
            }

            override fun onFailure(call: Call<Restaurants>, t: Throwable) {
                Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun clickedItem(restaurant: Restaurant) {
        Navigation.findNavController(this.requireView()).navigate(
            MainScreenFragmentDirections.actionMainScreenFragmentToDetailScreen(
                restaurant
            )
        )
    }

    override fun clickedFavourite(restaurant: Restaurant) {
        if (restaurant.isFavourite) {
            profileViewModel.deleteRestaurant(restaurantTableAdapter(restaurant))
        } else {
            profileViewModel.addRestaurant(restaurantTableAdapter(restaurant))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_bar, menu)
        menu?.findItem(R.id.profile)?.setOnMenuItemClickListener {
            Navigation.findNavController(this.requireView())
                .navigate(MainScreenFragmentDirections.actionMainScreenFragmentToProfileScreen())
            return@setOnMenuItemClickListener true
        }
        return super.onCreateOptionsMenu(menu, inflater)
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

    override fun clickedToDelete(restaurantImages: RestaurantImages) {

    }
}