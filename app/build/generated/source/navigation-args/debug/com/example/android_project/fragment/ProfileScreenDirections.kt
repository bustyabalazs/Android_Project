package com.example.android_project.fragment

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavDirections
import com.example.android_project.R
import com.example.android_project.Restaurant
import java.io.Serializable
import java.lang.UnsupportedOperationException
import kotlin.Int
import kotlin.Suppress

class ProfileScreenDirections private constructor() {
  private data class ActionProfileScreenToDetailScreen3(
    val restaurant: Restaurant
  ) : NavDirections {
    override fun getActionId(): Int = R.id.action_profileScreen_to_detailScreen3

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
    fun actionProfileScreenToDetailScreen3(restaurant: Restaurant): NavDirections =
        ActionProfileScreenToDetailScreen3(restaurant)
  }
}
