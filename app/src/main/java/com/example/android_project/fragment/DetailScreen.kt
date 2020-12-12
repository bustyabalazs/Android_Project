package com.example.android_project.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
//import com.example.android_project.DetailScreenArgs
//import com.example.android_project.DetailScreenDirections
import com.example.android_project.R

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
class DetailScreen : Fragment() {
    val args: DetailScreenArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detail_screen, container, false)
        view.findViewById<TextView>(R.id.name).text="Name: "+args.restaurant.name
        view.findViewById<TextView>(R.id.address).text="Address: "+args.restaurant.address
        view.findViewById<TextView>(R.id.city).text="City: "+args.restaurant.city
        view.findViewById<TextView>(R.id.area).text="Area: "+args.restaurant.area
        view.findViewById<TextView>(R.id.postal_code).text="Postal code: "+args.restaurant.postal_code
        view.findViewById<TextView>(R.id.country).text="Country: "+args.restaurant.country
        view.findViewById<TextView>(R.id.phone).text="Phone: "+args.restaurant.phone
        view.findViewById<TextView>(R.id.price).text="Price: "+args.restaurant.price
        view.findViewById<TextView>(R.id.reserve_url).text="Reserve: "+args.restaurant.reserve_url
        view.findViewById<TextView>(R.id.mobile_reserve_url).text="Mobile reserve: "+args.restaurant.mobile_reserve_url
        Glide.with( view.findViewById<ImageView>(R.id.restaurant_photo))
            .load(args.restaurant.image_url)
            .override(700)
            .circleCrop()
            .into(view.findViewById<ImageView>(R.id.restaurant_photo))
        return view

    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_bar,menu)
        menu?.findItem(R.id.profile)?.setOnMenuItemClickListener {
            Navigation.findNavController(this.requireView()).navigate(DetailScreenDirections.actionDetailScreenToProfileScreen())
            return@setOnMenuItemClickListener true
        }
        return super.onCreateOptionsMenu(menu, inflater)
    }
}
