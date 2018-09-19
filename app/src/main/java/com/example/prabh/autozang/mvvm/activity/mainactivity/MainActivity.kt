package com.example.prabh.autozang.mvvm.activity.mainactivity


import android.arch.lifecycle.Observer
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import com.example.prabh.autozang.R
import com.example.prabh.autozang.mvvm.application.AutozangApplication
import com.example.prabh.autozang.retrofit.model.CurrentLocation
import com.example.prabh.autozang.room.table.ServiceCenters
import com.example.prabh.autozang.utility.ApiType
import com.example.prabh.autozang.utility.Status
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_sort_type.view.*
import javax.inject.Inject

class MainActivity : AutozangApplication(), SwipeRefreshLayout.OnRefreshListener {


    @Inject
    lateinit var mainActivityViewModel: MainActivityViewModel
    private var sortType = "name"
    private var locationManager: LocationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainActivityComponent.inject(this)
        locationlistener()
        onClickListeners()
        initialise()
    }

    private fun locationlistener() {

        val locationListener: LocationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                getLocation(location.longitude, location.latitude)
            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?
        current_location.setOnClickListener {
            try {
                // Request location updates
                locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener);
            } catch (ex: SecurityException) {
                Toast.makeText(this, "Please provide location access", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getLocation(longitude: Double, latitude: Double) {
        mainActivityViewModel.getLocation(latitude, longitude)
    }

    private fun onClickListeners() {
        swipeRefreshLayoutItem?.let {
            swipeRefreshLayoutItem?.setOnRefreshListener(this)
        }

        sort_list.setOnClickListener {

            val builder = AlertDialog.Builder(this@MainActivity)
            val view = LayoutInflater.from(this).inflate(R.layout.layout_sort_type, null, false)
            builder.setView(view)
                    .setTitle("Sort By")
                    .setPositiveButton("Ok") { dialog, which ->
                        val checkedId = view.sort_select.checkedRadioButtonId
                        when (checkedId) {
                            R.id.name -> sortType = "name"
                            R.id.distance -> sortType = "distance"
                            R.id.review -> sortType = "reviews"
                        }
                        setAdapterBySort(sortType)
                    }
                    .setNegativeButton("Cancel") { dialog, which -> }
            val dialogBox = builder.create()
            dialogBox.show()
        }

    }

    private fun setAdapterBySort(sortType: String) {
        val serviceCenteres=mainActivityViewModel.getSortedData(sortType)
        service_centres.layoutManager = LinearLayoutManager(this)
        service_centres.adapter = ServiceAdapter(serviceCenteres as ArrayList<ServiceCenters>)
    }

    private fun initialise() {
        getData()
        observeResponse()
        observeServerResponse()
    }

    private fun observeServerResponse() {
        mainActivityViewModel.response.observe(this, Observer {
            processResponse(it)
        })

    }

    private fun processResponse(response: com.example.prabh.autozang.utility.Response?) {
        when (response!!.status) {
            Status.SUCCESS -> {
                processResult(response)
            }
            Status.ERROR -> {
                Log.v("Location", "Error..")
            }
            Status.LOADING -> {
                Log.v("Location", "Loading..")
            }
        }
    }

    private fun processResult(response: com.example.prabh.autozang.utility.Response) {
        when (response.apiType) {
            ApiType.GET_LOCATION -> {
                val locationResult = response.result as CurrentLocation
                location_edit_text.setText(locationResult.name)
            }
            else -> {
            }
        }
    }

    private fun getData() {
        mainActivityViewModel.getData()
    }

    private fun observeResponse() {
        mainActivityViewModel.getDataFromTable().observe(this, Observer {
            processData(it as ArrayList<ServiceCenters>)
        })
    }

    private fun processData(it: ArrayList<ServiceCenters>?) {
        setAdapter(it)
        swipeRefreshLayoutItem.isRefreshing = false
    }

    private fun setAdapter(it: ArrayList<ServiceCenters>?) {
        service_centres.layoutManager = LinearLayoutManager(this)
        service_centres.adapter = ServiceAdapter(it!!)
    }

    override fun onRefresh() {
        swipeRefreshLayoutItem.isRefreshing = true
        getData()
    }
}
