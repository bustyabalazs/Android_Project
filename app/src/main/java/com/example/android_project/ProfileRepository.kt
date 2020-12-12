package com.example.android_project

import androidx.lifecycle.LiveData

class ProfileRepository(private val profileDAO: ProfileDAO) {
    val readProfile:LiveData<Profile> = profileDAO.readAllData()

    suspend fun  addProfile(profile: Profile){
        profileDAO.addProfile(profile)
    }
    suspend fun updateProfile(profile: ProfileUpdate){
        profileDAO.updateProfile(profile)
    }

}