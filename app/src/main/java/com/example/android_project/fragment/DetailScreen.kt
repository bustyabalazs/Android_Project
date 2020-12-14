package com.example.android_project.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.android_project.ProfileViewModel
import com.example.android_project.R
import com.example.android_project.Restaurant
import com.example.android_project.RestaurantTable

//import com.example.android_project.DetailScreenArgs
//import com.example.android_project.DetailScreenDirections

//import com.example.android_project.fragment.DetailScreenArgs
//import com.example.android_project.fragment.DetailScreenDirections

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailScreen.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailScreen : Fragment(){
    val args: DetailScreenArgs by navArgs()
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        val restaurants = profileViewModel.readRestaurants
        val view = inflater.inflate(R.layout.fragment_detail_screen, container, false)
        view.findViewById<TextView>(R.id.name).text = "Name: " + args.restaurant.name
        view.findViewById<TextView>(R.id.address).text = "Address: " + args.restaurant.address
        view.findViewById<TextView>(R.id.city).text = "City: " + args.restaurant.city
        view.findViewById<TextView>(R.id.area).text = "Area: " + args.restaurant.area
        view.findViewById<TextView>(R.id.postal_code).text =
            "Postal code: " + args.restaurant.postal_code
        view.findViewById<TextView>(R.id.country).text = "Country: " + args.restaurant.country
        view.findViewById<TextView>(R.id.price).text = "Price: " + args.restaurant.price
        view.findViewById<TextView>(R.id.reserve_url).text =
            "Reserve: " + args.restaurant.reserve_url
        view.findViewById<TextView>(R.id.mobile_reserve_url).text =
            "Mobile reserve: " + args.restaurant.mobile_reserve_url
        Glide.with(view.findViewById<ImageView>(R.id.restaurant_photo))
            .load(args.restaurant.image_url)
            .override(700)
            .circleCrop()
            .into(view.findViewById<ImageView>(R.id.restaurant_photo))

        var isFavourite=true
        restaurants.observe(viewLifecycleOwner) {
            isFavourite = it.contains(restaurantTableAdapter(args.restaurant))
            view.findViewById<Button>(R.id.favourite).text = if (isFavourite) "REMOVE FROM FAVOURITES" else "ADD TO FAVOURITES"
        }
        //view.findViewById<Button>(R.id.favourite).text = if (isFavourite) "REMOVE FROM FAVOURITES" else "ADD TO FAVOURITES"

        //Mapview
        val map = view.findViewById<Button>(R.id.map)
        map.setOnClickListener {
            val intent = Intent(android.content.Intent.ACTION_VIEW)
            intent.data =
                Uri.parse("http://maps.google.com/maps?q=loc:${args.restaurant.lat},${args.restaurant.lng}")
            intent.setPackage("com.google.android.apps.maps");
            startActivity(intent)
        }

        //CALL
        val call = view.findViewById<Button>(R.id.call)
        call.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_DIAL, Uri.parse("tel:${args.restaurant.phone}"))
            startActivity(intent)
        }
        //ADD favourite restaurant
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        val insertOrDeleteRestaurant = view.findViewById<Button>(R.id.favourite)
        insertOrDeleteRestaurant.setOnClickListener {
            if (isFavourite) {
                deleteFavouriteRestaurant(args.restaurant)
            } else {
                insertFavouriteRestaurant(args.restaurant)
            }
        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_bar, menu)
        menu.findItem(R.id.profile)?.setOnMenuItemClickListener {
            Navigation.findNavController(this.requireView())
                .navigate(DetailScreenDirections.actionDetailScreenToProfileScreen())
            return@setOnMenuItemClickListener true
        }
        return super.onCreateOptionsMenu(menu, inflater)
    }

    private fun insertFavouriteRestaurant(restaurant: Restaurant) {
        profileViewModel.addRestaurant(restaurantTableAdapter(restaurant))
        Toast.makeText(requireContext(), "Added to favourites!", Toast.LENGTH_LONG).show()
    }

    private fun deleteFavouriteRestaurant(restaurant: Restaurant) {
        profileViewModel.deleteRestaurant(restaurantTableAdapter(args.restaurant))
        Toast.makeText(requireContext(), "Removed from favourites!", Toast.LENGTH_LONG).show()
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
