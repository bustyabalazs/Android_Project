package com.example.android_project.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_project.*

class ProfileScreen : Fragment(),ClickListener {
    private lateinit var profileViewModel: ProfileViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val view: View = inflater.inflate(R.layout.profile_screen, container, false)
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        profileViewModel.readProfile.observe(viewLifecycleOwner, Observer { profile -> setProfileView(profile) })
        view.findViewById<Button>(R.id.save).setOnClickListener {
            updateProfile()
        }
        val restaurants=profileViewModel.readRestaurants
        //recycler view
        view.findViewById<RecyclerView>(R.id.recyclerView).setHasFixedSize(true)
        view.findViewById<RecyclerView>(R.id.recyclerView).layoutManager = LinearLayoutManager(context)
        view.findViewById<RecyclerView>(R.id.recyclerView).adapter =
            RestaurantAdapter(restaurantListAdapter(restaurants.value),this@ProfileScreen)
        return view
    }
    private  fun setProfileView(profile: Profile){
        view?.findViewById<EditText>(R.id.name)?.setText(profile.name)
        view?.findViewById<EditText>(R.id.email)?.setText(profile.email)
        view?.findViewById<EditText>(R.id.phone_number)?.setText(profile.phone)
        view?.findViewById<EditText>(R.id.address)?.setText(profile.address)
        //view?.findViewById<EditText>(R.id.profile_picture)?.setText(profile.picture)
    }

    private fun updateProfile(){
        val name=view?.findViewById<EditText>(R.id.name)?.text.toString()
        val address=view?.findViewById<EditText>(R.id.address)?.text.toString()
        val email=view?.findViewById<EditText>(R.id.email)?.text.toString()
        val phone=view?.findViewById<EditText>(R.id.phone_number)?.text.toString()
        val profile=Profile(1,name,"picture",address,phone,email)
        profileViewModel.updateProfile(profile)
        Toast.makeText(requireContext(),"Profile saved!",Toast.LENGTH_LONG).show()
    }
    private fun restaurantAdapter(restaurant: RestaurantTable): Restaurant {
        return Restaurant(
            restaurant.id,
            restaurant.name,
            restaurant.address,
            restaurant.city,
            restaurant.state,
            restaurant.area,
            restaurant.postal_code,
            restaurant.country,
            restaurant.phone,
            restaurant.lat,
            restaurant.lng,
            restaurant.price,
            restaurant.reserve_url,
            restaurant.mobile_reserve_url,
            restaurant.image_url
        )
    }
    private fun restaurantListAdapter(restaurants: List<RestaurantTable>?):List<Restaurant>{
        val temp=mutableListOf<Restaurant>()
        if (restaurants != null) {
            for(item in restaurants){
                temp.add(restaurantAdapter(item))
            }
        }
        return temp
    }

    override fun clickedItem(restaurant: Restaurant) {
        Navigation.findNavController(this.requireView()).navigate(
            ProfileScreenDirections.actionProfileScreenToDetailScreen3(
                restaurant
            )
        )
    }
}