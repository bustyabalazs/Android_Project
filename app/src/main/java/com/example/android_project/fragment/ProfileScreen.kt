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
import com.example.android_project.Profile
import com.example.android_project.ProfileViewModel
import com.example.android_project.R

class ProfileScreen : Fragment() {
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
        return view
    }
    private  fun setProfileView(profile: Profile){
        view?.findViewById<EditText>(R.id.name)?.setText(profile.name)
        view?.findViewById<EditText>(R.id.email)?.setText(profile.email)
        view?.findViewById<EditText>(R.id.phone_number)?.setText(profile.phone)
        view?.findViewById<EditText>(R.id.address)?.setText(profile.address)
        //v iew?.findViewById<EditText>(R.id.profile_picture)?.setText(profile.picture)
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

    private fun insertDataToDatabase() {
        val name = view?.findViewById<EditText>(R.id.name).toString()
        val address = view?.findViewById<EditText>(R.id.address).toString()
        val phone = view?.findViewById<EditText>(R.id.phone).toString()
        val email = view?.findViewById<EditText>(R.id.email).toString()
        val picture = "picture"//view?.findViewById<EditText>(R.id.email).toString()
        val profile = Profile(
            1,
            name,
            picture,
            address,
            phone,
            email
        )
        profileViewModel.addProfile(profile)
        Toast.makeText(requireContext(),"Profile saved!",Toast.LENGTH_LONG).show()

    }
}