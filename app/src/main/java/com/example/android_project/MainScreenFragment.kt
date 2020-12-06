package com.example.android_project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log
import android.widget.ProgressBar

class MainScreenFragment : Fragment() {

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

        val request = ServiceBuilder.buildService(OpenTableEndPoints::class.java)
        val call = request.getRestaurants("https://opentable.herokuapp.com/api/")

        call.enqueue(object : Callback<Restaurants> {
            override fun onResponse(call: Call<Restaurants>, response: Response<Restaurants>) {
                if (response.isSuccessful){
                    view.findViewById<ProgressBar>(R.id.progress_bar).visibility= View.GONE
                            view.findViewById<RecyclerView>(R.id.recyclerView).apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(context)
                        adapter = RestaurantAdapter(response.body()!!.results)
                    }
                }
            }
            override fun onFailure(call: Call<Restaurants>, t: Throwable) {
                Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

        //restaurantAdapter= RestaurantAdapter()
        //restaurantAdapter!!.setData(restaurants)

        view.findViewById<RecyclerView>(R.id.recyclerView).layoutManager=LinearLayoutManager(this.context)
        view.findViewById<RecyclerView>(R.id.recyclerView).setHasFixedSize(true)
        view.findViewById<RecyclerView>(R.id.recyclerView).adapter=restaurantAdapter
        return view
    }
}