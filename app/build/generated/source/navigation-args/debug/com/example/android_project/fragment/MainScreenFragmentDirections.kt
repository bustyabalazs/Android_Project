package com.example.android_project.fragment

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.example.android_project.R
import com.example.android_project.Restaurant
import java.io.Serializable
import java.lang.UnsupportedOperationException
import kotlin.Int
import kotlin.Suppress

class MainScreenFragmentDirections private constructor() {
  private data class ActionMainScreenFragmentToDetailScreen(
    val restaurant: Restaurant
  ) : NavDirections {
    override fun getActionId(): Int = R.id.action_mainScreenFragment_to_detailScreen

    @Suppress("CAST_NEVER_SUCCEEDS")
    override fun getArguments(): Bundle {
      val result = Bundle()
      if (Parcelable::class.java.isAssignableFrom(Restaurant::class.java)) {
        result.putParcelable("restaurant", this.restaurant as Parcelable)
      } else if (Serializable::class.java.isAssignableFrom(Restaurant::class.java)) {
        result.putSerializable("restaurant", this.restaurant as Serializable)
      } else {
        throw UnsupportedOperationException(Restaurant::class.java.name +
            " must implement Parcelable or Serializable or must be an Enum.")
      }
      return result
    }
  }

  companion object {
    fun actionMainScreenFragmentToDetailScreen(restaurant: Restaurant): NavDirections =
        ActionMainScreenFragmentToDetailScreen(restaurant)

    fun actionMainScreenFragmentToProfileScreen(): NavDirections =
        ActionOnlyNavDirections(R.id.action_mainScreenFragment_to_profileScreen)
  }
}
