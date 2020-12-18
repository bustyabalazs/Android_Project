package com.example.android_project.fragment

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.example.android_project.R

class DetailScreenDirections private constructor() {
  companion object {
    fun actionDetailScreenToMainScreenFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_detailScreen_to_mainScreenFragment)

    fun actionDetailScreenToProfileScreen(): NavDirections =
        ActionOnlyNavDirections(R.id.action_detailScreen_to_profileScreen)
  }
}
