package com.example.android_project.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.view.*
import android.widget.ProgressBar
import androidx.navigation.Navigation
import com.example.android_project.*

//import com.example.android_project.fragment.MainScreenFragmentDirections

class
MainScreenFragment : Fragment(), ClickListener {

    var restaurantAdapter: RestaurantAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val view: View =inflater.inflate(R.layout.fragment_main_screen, container, false)

        val request = ServiceBuilder.buildService(
            OpenTableEndPoints::class.java
        )
        val call = request.getRestaurants("US")//("https://opentable.herokuapp.com/api/")

        call.enqueue(object : Callback<Restaurants> {
            override fun onResponse(call: Call<Restaurants>, response: Response<Restaurants>) {

                if (response.isSuccessful){
                    view.findViewById<ProgressBar>(R.id.progress_bar).visibility= View.GONE

                    view.findViewById<RecyclerView>(R.id.recyclerView).setHasFixedSize(true)
                    view.findViewById<RecyclerView>(R.id.recyclerView).layoutManager = LinearLayoutManager(context)
                    view.findViewById<RecyclerView>(R.id.recyclerView).adapter =
                        RestaurantAdapter(
                            response.body()!!.restaurants,
                            this@MainScreenFragment
                        )
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_bar,menu)
        menu?.findItem(R.id.profile)?.setOnMenuItemClickListener {
            Navigation.findNavController(this.requireView()).navigate(MainScreenFragmentDirections.actionMainScreenFragmentToProfileScreen())
            return@setOnMenuItemClickListener true
        }
        return super.onCreateOptionsMenu(menu, inflater)
    }
}