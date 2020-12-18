package com.example.android_project.fragment

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavArgs
import com.example.android_project.Restaurant
import java.io.Serializable
import java.lang.IllegalArgumentException
import java.lang.UnsupportedOperationException
import kotlin.Suppress
import kotlin.jvm.JvmStatic

data class DetailScreenArgs(
  val restaurant: Restaurant
) : NavArgs {
  @Suppress("CAST_NEVER_SUCCEEDS")
  fun toBundle(): Bundle {
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

  companion object {
    @JvmStatic
    fun fromBundle(bundle: Bundle): DetailScreenArgs {
      bundle.setClassLoader(DetailScreenArgs::class.java.classLoader)
      val __restaurant : Restaurant?
      if (bundle.containsKey("restaurant")) {
        if (Parcelable::class.java.isAssignableFrom(Restaurant::class.java) ||
            Serializable::class.java.isAssignableFrom(Restaurant::class.java)) {
          __restaurant = bundle.get("restaurant") as Restaurant?
        } else {
          throw UnsupportedOperationException(Restaurant::class.java.name +
              " must implement Parcelable or Serializable or must be an Enum.")
        }
        if (__restaurant == null) {
          throw IllegalArgumentException("Argument \"restaurant\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"restaurant\" is missing and does not have an android:defaultValue")
      }
      return DetailScreenArgs(__restaurant)
    }
  }
}
