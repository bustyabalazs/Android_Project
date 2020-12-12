package com.example.android_project.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.android_project.ProfileDatabase
import com.example.android_project.ProfileRepository
import com.example.android_project.R

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = (supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment).navController
    }
}