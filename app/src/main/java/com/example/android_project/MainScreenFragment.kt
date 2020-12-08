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
import android.util.Log.d
import android.widget.ProgressBar
import android.widget.TextView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

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

        val name=view.findViewById<TextView>(R.id.name)
        val request = ServiceBuilder.buildService(OpenTableEndPoints::class.java)
        val call = request.getRestaurants("US")//("https://opentable.herokuapp.com/api/")

        call.enqueue(object : Callback<Restaurants> {
            override fun onResponse(call: Call<Restaurants>, response: Response<Restaurants>) {

                if (response.isSuccessful){
                    view.findViewById<ProgressBar>(R.id.progress_bar).visibility= View.GONE

                   // view.findViewById<RecyclerView>(R.id.recyclerView).apply {
                    view.findViewById<RecyclerView>(R.id.recyclerView).setHasFixedSize(true)
                    view.findViewById<RecyclerView>(R.id.recyclerView).layoutManager = LinearLayoutManager(context)
                    view.findViewById<RecyclerView>(R.id.recyclerView).adapter = RestaurantAdapter(response.body()!!.restaurants)
                   // }
                }
            }
            override fun onFailure(call: Call<Restaurants>, t: Throwable) {
                Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

        //restaurantAdapter= RestaurantAdapter()
        //restaurantAdapter!!.setData(restaurants)

       // view.findViewById<RecyclerView>(R.id.recyclerView).layoutManager=LinearLayoutManager(this.context)
       // view.findViewById<RecyclerView>(R.id.recyclerView).setHasFixedSize(true)
       // view.findViewById<RecyclerView>(R.id.recyclerView).adapter=restaurantAdapter
        d("danial","here we are")
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        requireView().findViewById<SwipeRefreshLayout>(R.id.refreshLayout).setOnRefreshListener {
//            fetchRestaurants()
//        }
//        fetchRestaurants()
    }
//    private fun fetchRestaurants(){
//        requireView().findViewById<SwipeRefreshLayout>(R.id.refreshLayout).isRefreshing=true
//
//        val request = ServiceBuilder.buildService(OpenTableEndPoints::class.java)
//        val call = request.getRestaurants("US")//("https://opentable.herokuapp.com/api/")
//
//        call.enqueue(object : Callback<Restaurants> {
//            override fun onResponse(call: Call<Restaurants>, response: Response<Restaurants>) {
//                d("danial","here we are")
//                if (response.isSuccessful){
//                    requireView().findViewById<SwipeRefreshLayout>(R.id.refreshLayout).isRefreshing=false
//                   // d("danial","${response.body()!!}")
//                    requireView().findViewById<ProgressBar>(R.id.progress_bar).visibility= View.GONE
//                    requireView().findViewById<RecyclerView>(R.id.recyclerView).apply {
//                        setHasFixedSize(true)
//                        layoutManager = LinearLayoutManager(this.context)
//                        adapter = RestaurantAdapter(response.body()!!.results)
//                    }
//                }
//            }
//            override fun onFailure(call: Call<Restaurants>, t: Throwable) {
//                requireView().findViewById<SwipeRefreshLayout>(R.id.refreshLayout).isRefreshing=false
//                Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
//            }
//        })

//    }

}