package com.example.android_project.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_project.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class
MainScreenFragment : Fragment(), ClickListener {

    private lateinit var profileViewModel: ProfileViewModel
    var isLoading: Boolean = false
    var isLastPage: Boolean=false
    var currentPage: Int = 1
    var selected: Int = 0
    private val request = ServiceBuilder.buildService(OpenTableEndPoints::class.java)
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: RestaurantAdapter
    var restaurants: MutableLiveData<List<Restaurant>> = MutableLiveData()
    lateinit var favouriteRestaurants: LiveData<List<RestaurantTable>>

    //filters
    val filterData= arrayListOf("","1","","","0") //country,page,name,city,price

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val view: View = inflater.inflate(R.layout.fragment_main_screen, container, false)
        layoutManager = LinearLayoutManager(context)
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
         favouriteRestaurants = profileViewModel.readRestaurants
        val call = request.getRestaurantsPage("US", currentPage)
        APIrequest(call)
        setScrollListeners(view)

        return view
    }

    override fun clickedItem(restaurant: Restaurant) {
        Navigation.findNavController(this.requireView()).navigate(
            MainScreenFragmentDirections.actionMainScreenFragmentToDetailScreen(
                restaurant
            )
        )
    }

    override fun clickedFavourite(restaurant: Restaurant) {
        if (restaurant.isFavourite) {
            profileViewModel.deleteRestaurant(restaurantTableAdapter(restaurant))
        } else {
            profileViewModel.addRestaurant(restaurantTableAdapter(restaurant))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_bar, menu)
        menu.findItem(R.id.profile)?.setOnMenuItemClickListener {
            Navigation.findNavController(this.requireView())
                .navigate(MainScreenFragmentDirections.actionMainScreenFragmentToProfileScreen())
            return@setOnMenuItemClickListener true
        }
        //Choosing filter
        val filter = menu.findItem(R.id.filter)
        val spinner = filter.actionView as Spinner

        val spinnerStringArray: ArrayList<String> = ArrayList()
        spinnerStringArray.add("Country")
        spinnerStringArray.add("Page")
        spinnerStringArray.add("Name")
        spinnerStringArray.add("City")
        spinnerStringArray.add("Price")

        val spinnerAdapter: ArrayAdapter<String> = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            spinnerStringArray
        )
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                selected = position
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        //Searching
        val menuItem = menu.findItem(R.id.search)
        val searchView = menuItem.actionView as SearchView
        searchView.maxWidth = Int.MAX_VALUE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(filterString: String?): Boolean {
                if (filterString != null) {
                    filterData[selected]=filterString
                }
                filtering()
                return true
            }

            override fun onQueryTextChange(filterString: String?): Boolean {
                if (filterString != null) {
                    filterData[selected]=filterString
                    filtering()
                }
                return true
            }

        })
        return super.onCreateOptionsMenu(menu, inflater)
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

    private fun setScrollListeners(view: View) {
        view.findViewById<RecyclerView>(R.id.recyclerView)
            .addOnScrollListener(object : PaginationScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount

                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                    if (!isLoading()) {
                        if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                            loadMoreItems()
                        }
                    }
                }

                override fun isLoading(): Boolean {
                    view.findViewById<ProgressBar>(R.id.progress_bar).visibility =
                        if (isLoading) View.VISIBLE else View.GONE
                    return isLoading
                }

                override fun loadMoreItems() {
                    isLastPage = layoutManager.itemCount<25
                    if(!isLoading&&!isLastPage) {
                        isLoading = true
                        getMoreItems()
                    }
                }
            })
    }

    private fun getMoreItems() {
        currentPage++
        val call = request.getRestaurantsPage("US", currentPage)
        call.enqueue(object : Callback<Restaurants> {
            override fun onResponse(call: Call<Restaurants>, response: Response<Restaurants>) {

                if (response.isSuccessful) {
                    restaurants.value = restaurants.value!! + response.body()!!.restaurants
                    var lastFirstVisiblePosition =
                        (requireView().findViewById<RecyclerView>(R.id.recyclerView).layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    Log.e("dfg", "$lastFirstVisiblePosition")
                    adapter = RestaurantAdapter(restaurants.value!!, this@MainScreenFragment)
                    requireView().findViewById<RecyclerView>(R.id.recyclerView).adapter = adapter
                    (requireView().findViewById<RecyclerView>(R.id.recyclerView).layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
                        lastFirstVisiblePosition, 0
                    )
                }
            }

            override fun onFailure(call: Call<Restaurants>, t: Throwable) {
                Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
        isLoading = false
    }

    private fun APIrequest(call: Call<Restaurants>){
        call.enqueue(object : Callback<Restaurants> {
            override fun onResponse(call: Call<Restaurants>, response: Response<Restaurants>) {

                if (response.isSuccessful) {
                    restaurants.value = response.body()!!.restaurants
                    favouriteRestaurants.observe(viewLifecycleOwner) {
                        for (restaurant in response.body()!!.restaurants) {
                            restaurant.isFavourite = it.contains(restaurantTableAdapter(restaurant))
                        }
                        requireView().findViewById<ProgressBar>(R.id.progress_bar).visibility = View.GONE
                        requireView().findViewById<RecyclerView>(R.id.recyclerView).setHasFixedSize(true)
                        requireView().findViewById<RecyclerView>(R.id.recyclerView).layoutManager = layoutManager
                        var lastFirstVisiblePosition = (requireView().findViewById<RecyclerView>(R.id.recyclerView).layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                        adapter = RestaurantAdapter(restaurants.value!!, this@MainScreenFragment)
                        requireView().findViewById<RecyclerView>(R.id.recyclerView).adapter = adapter
                        (requireView().findViewById<RecyclerView>(R.id.recyclerView).layoutManager as LinearLayoutManager).scrollToPosition(
                            lastFirstVisiblePosition)

                    }
                }
            }

            override fun onFailure(call: Call<Restaurants>, t: Throwable) {
                Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

        private fun filtering(){
            restaurants.value=null //Clearing restaurant list
            val call:Call<Restaurants>
            val page:Int = try{
                filterData[1].toInt()
            } catch (nfe: NumberFormatException) {
                1
            }
            val price:Int =try{
                filterData[4].toInt()
            } catch (nfe: java.lang.NumberFormatException){
                0
            }
            call = when(selected){
                2-> request.getRestaurantsName(filterData[0], page,filterData[2])
                3-> request.getRestaurantsCity(filterData[0], page,filterData[3])
                4-> request.getRestaurantsPrice(filterData[0], page,price)
                else -> request.getRestaurantsPage(filterData[0], page)
            }
        APIrequest(call)
    }
    override fun clickedToDelete(restaurantImages: RestaurantImages) {
    }
}

