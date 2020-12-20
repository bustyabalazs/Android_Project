package com.example.android_project.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_project.*

class ProfileScreen : Fragment(),ClickListener {
    private lateinit var profileViewModel: ProfileViewModel

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

        //recycler view
        profileViewModel.readRestaurants.observe(viewLifecycleOwner, Observer { restaurants -> recyclerView(restaurants) })
        val gallery=view.findViewById<ImageButton>(R.id.picture)
        gallery.setOnClickListener{
            openGallery()
        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == 1000) {//IMAGE_PICK_CODE
            Glide.with(requireView().findViewById<ImageView>(R.id.profile_picture))
                .load(data?.data)
                .override(600)
                .circleCrop()
                .into(requireView().findViewById<ImageView>(R.id.profile_picture))

            view?.findViewById<ImageView>(R.id.profile_picture)?.setImageURI(data?.data)
            profileViewModel.profilePicture=data?.data
        }
    }
    private fun openGallery(){
        val intent=Intent(Intent.ACTION_PICK)
        intent.type="image/*"
        startActivityForResult(intent,1000)
    }

    private fun recyclerView(restaurantList: List<RestaurantTable>){
        val restaurants=restaurantListAdapter(restaurantList)
        view?.findViewById<RecyclerView>(R.id.recyclerView)?.setHasFixedSize(true)
        view?.findViewById<RecyclerView>(R.id.recyclerView)?.layoutManager = LinearLayoutManager(context)
        view?.findViewById<RecyclerView>(R.id.recyclerView)?.adapter = RestaurantAdapter(restaurants,this@ProfileScreen)

    }

    private  fun setProfileView(profile: Profile){
        view?.findViewById<EditText>(R.id.name)?.setText(profile.name)
        view?.findViewById<EditText>(R.id.email)?.setText(profile.email)
        view?.findViewById<EditText>(R.id.phone_number)?.setText(profile.phone)
        view?.findViewById<EditText>(R.id.address)?.setText(profile.address)
        Glide.with(requireView().findViewById<ImageView>(R.id.profile_picture))
            .load(profile.picture.toUri())
            .override(600)
            .circleCrop()
            .into(requireView().findViewById(R.id.profile_picture))
    }

    private fun updateProfile(){
        val name=view?.findViewById<EditText>(R.id.name)?.text.toString()
        val address=view?.findViewById<EditText>(R.id.address)?.text.toString()
        val email=view?.findViewById<EditText>(R.id.email)?.text.toString()
        val phone=view?.findViewById<EditText>(R.id.phone_number)?.text.toString()
        val picture=profileViewModel.profilePicture
        val profile=Profile(1,name,picture.toString(),address,phone,email)
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
            restaurant.image_url,
            true
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
    override fun clickedFavourite(restaurant: Restaurant) {
        if(restaurant.isFavourite) {
            profileViewModel.deleteRestaurant(restaurantTableAdapter(restaurant))
        }
        else{
            profileViewModel.addRestaurant(restaurantTableAdapter(restaurant))
        }
    }

    override fun clickedToDelete(restaurantImages: RestaurantImages) {
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
}