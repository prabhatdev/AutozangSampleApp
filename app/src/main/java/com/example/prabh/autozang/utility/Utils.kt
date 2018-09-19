package com.example.prabh.autozang.utility

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import com.example.prabh.autozang.retrofit.handler.ApiInterface
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.NetworkInterface
import java.util.*

class Utils(activityname: String) {
    companion object {

        val interfaceService: ApiInterface
            get() {
                val BASE_URL = "http://13.232.30.77/"
                val retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                return retrofit.create<ApiInterface>(ApiInterface::class.java)
            }

        val locationService: ApiInterface
            get() {
                val BASE_URL = "http://api.openweathermap.org/"
                val retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                return retrofit.create<ApiInterface>(ApiInterface::class.java)
            }
        fun getClassName(fullName: String) = fullName.substring(fullName.lastIndexOf('.') + 1, fullName.length)

        fun provideUtil(context: Context): Utils {
            var activityname = Utils.getClassName(context.javaClass.name)
            activityname = if (activityname.length > 20) {
                activityname.substring(0, 20)
            } else {
                activityname
            }
            return Utils(activityname)
        }
    }

    fun isInternetAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        val isOnline = (activeNetwork != null && activeNetwork.isAvailable && activeNetwork.isConnected)
        if (!isOnline) {
            Toast.makeText(context, "Please Check your Internet Connection!", Toast.LENGTH_SHORT).show()
        }
        if (isVPNConnected()) {
            Toast.makeText(context, "Your network is being monitored, disable monitoring app to secure the connection", Toast.LENGTH_LONG).show()
        }
        return !isVPNConnected() && isOnline
    }
    private fun isVPNConnected(): Boolean {
        val networkList = ArrayList<String>()
        try {
            for (networkInterface in Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (networkInterface.isUp) {
                    networkList.add(networkInterface.name)
                }
            }
        } catch (ex: Exception) {
            Log.d("isVPNConnected", "Error")
        }
        return networkList.contains("tun0") || networkList.contains("ppp0")
    }
}