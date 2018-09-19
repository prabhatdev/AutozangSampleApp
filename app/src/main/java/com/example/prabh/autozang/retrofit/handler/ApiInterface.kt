package com.example.prabh.autozang.retrofit.handler

import android.support.annotation.Keep
import com.example.prabh.autozang.retrofit.model.CurrentLocation
import com.example.prabh.autozang.retrofit.model.ServiceCenterResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

@Keep
interface ApiInterface {
    @POST("api/listAllEntries")
    fun getAllServiceCenters():Observable<ServiceCenterResult>

    @GET("/data/2.5/weather")
    fun getLocation(@Query("apikey") apiKey:String,@Query("lat")latitude:String,@Query("lon") longitude:String):Observable<CurrentLocation>
}
