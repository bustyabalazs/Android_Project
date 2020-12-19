package com.example.android_project.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_project.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//import com.example.android_project.fragment.MainScreenFragmentDirections

class
MainScreenFragment : Fragment(), ClickListener {

    private lateinit var profileViewModel: ProfileViewModel
    var isLoading:Boolean=false
    var currentPage:Int=1
    //lateinit var restaurants: List<Restaurant>
    private val request = ServiceBuilder.buildService(OpenTableEndPoints::class.java)
    lateinit var layoutManager : LinearLayoutManager
    lateinit var adapter: RestaurantAdapter
    var restaurants : MutableLiveData<List<Restaurant>> = MutableLiveData()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val view: View = inflater.inflate(R.layout.fragment_main_screen, container, false)
        //
        layoutManager=LinearLayoutManager(context)
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        val favouriteRestaurants = profileViewModel.readRestaurants

        val call = request.getRestaurants("US",currentPage)//("https://opentable.herokuapp.com/api/")

        call.enqueue(object : Callback<Restaurants> {
            override fun onResponse(call: Call<Restaurants>, response: Response<Restaurants>) {

                if (response.isSuccessful) {
                    favouriteRestaurants.observe(viewLifecycleOwner) {
                        for (restaurant in response.body()!!.restaurants) {
                            restaurant.isFavourite = it.contains(restaurantTableAdapter(restaurant))
                        }
                        view.findViewById<ProgressBar>(R.id.progress_bar).visibility = View.GONE
                        view.findViewById<RecyclerView>(R.id.recyclerView).setHasFixedSize(true)
                        view.findViewById<RecyclerView>(R.id.recyclerView).layoutManager = layoutManager
                        restaurants.value=response.body()!!.restaurants
                        adapter= RestaurantAdapter(restaurants.value!!, this@MainScreenFragment)
                        view.findViewById<RecyclerView>(R.id.recyclerView).adapter = adapter

                    }
                }
            }

            override fun onFailure(call: Call<Restaurants>, t: Throwable) {
                Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
        setScrollListeners(view)
        return view
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

    private fun setScrollListeners(view: View) {
        view.findViewById<RecyclerView>(R.id.recyclerView).addOnScrollListener(object : PaginationScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount

                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                if (!isLoading()) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                        loadMoreItems()
                    }
                }
            }
            override fun isLoading(): Boolean {
                view.findViewById<ProgressBar>(R.id.progress_bar).visibility =  if(isLoading) View.VISIBLE else View.GONE
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                getMoreItems()
            }
        })
    }
    private fun getMoreItems() {
        currentPage++
        val call=request.getRestaurants("US",currentPage)
        call.enqueue(object : Callback<Restaurants> {
            override fun onResponse(call: Call<Restaurants>, response: Response<Restaurants>) {

                if (response.isSuccessful) {
                    restaurants.value=restaurants.value!!+response.body()!!.restaurants
                    var lastFirstVisiblePosition = (requireView().findViewById<RecyclerView>(R.id.recyclerView).layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    Log.e( "dfg","$lastFirstVisiblePosition")
                    adapter= RestaurantAdapter(restaurants.value!!, this@MainScreenFragment)
                    requireView().findViewById<RecyclerView>(R.id.recyclerView).adapter = adapter
                    (requireView().findViewById<RecyclerView>(R.id.recyclerView).layoutManager as LinearLayoutManager).scrollToPosition(lastFirstVisiblePosition)
                }
            }
            override fun onFailure(call: Call<Restaurants>, t: Throwable) {
                Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
        isLoading = false
    }

    override fun clickedToDelete(restaurantImages: RestaurantImages) {
    }
}