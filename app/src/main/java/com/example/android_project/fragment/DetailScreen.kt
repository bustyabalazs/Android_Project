package com.example.android_project.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_project.*

class DetailScreen : Fragment(),ClickListener{
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

        profileViewModel.repository.profileDAO.readImages(args.restaurant.id.toInt()).observe(viewLifecycleOwner, Observer { images -> recyclerView(images) })

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
        val restaurants = profileViewModel.readRestaurants
        var isFavourite=true
        restaurants.observe(viewLifecycleOwner) {
            isFavourite = it.contains(restaurantTableAdapter(args.restaurant))
            view.findViewById<Button>(R.id.favourite).text = if (isFavourite) "REMOVE FROM FAVOURITES" else "ADD TO FAVOURITES"
        }


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
        val insertOrDeleteRestaurant = view.findViewById<Button>(R.id.favourite)
        insertOrDeleteRestaurant.setOnClickListener {
            if (isFavourite) {
                deleteFavouriteRestaurant(args.restaurant)
            } else {
                insertFavouriteRestaurant(args.restaurant)
            }
        }

        //ADD image
        val add = view.findViewById<ImageButton>(R.id.add_picture)
        add.setOnClickListener {
            openGallery()
        }


        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == 1000) {//IMAGE_PICK_CODE
            updateImages(data?.data)
        }
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
        profileViewModel.deleteRestaurant(restaurantTableAdapter(restaurant))
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

    private fun recyclerView(restaurantImages: List<RestaurantImages>){
        val last=RestaurantImages(0,args.restaurant.id.toInt(),args.restaurant.image_url)
        restaurantImages.reversed()
        val fullList=restaurantImages.reversed()+last       //reversed the list => the newer will appear in the front, and add image from the api
        view?.findViewById<RecyclerView>(R.id.recyclerViewImages)?.setHasFixedSize(true)
        val layoutManager=LinearLayoutManager(context)
        layoutManager.orientation=LinearLayoutManager.HORIZONTAL
        view?.findViewById<RecyclerView>(R.id.recyclerViewImages)?.layoutManager = layoutManager
        val adapter=RestaurantImageAdapter(fullList,this@DetailScreen)
        view?.findViewById<RecyclerView>(R.id.recyclerViewImages)?.adapter = adapter
    }

    private fun openGallery(){
        val intent=Intent(Intent.ACTION_PICK)
        intent.type="image/*"
        startActivityForResult(intent,1000)
    }
    private fun updateImages(uri:Uri?){
       val image=RestaurantImages(0,args.restaurant.id.toInt(),uri.toString())
        profileViewModel.addImage(image)
        Toast.makeText(requireContext(),"Image added!",Toast.LENGTH_LONG).show()
    }

    override fun clickedFavourite(restaurant: Restaurant) {

    }

    override fun clickedItem(restaurant: Restaurant) {

    }

    override fun clickedToDelete(restaurantImages: RestaurantImages) {
        profileViewModel.deleteImage(restaurantImages)
    }
}