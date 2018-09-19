package com.example.prabh.autozang.mvvm.activity.mainactivity

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.prabh.autozang.retrofit.model.CurrentLocation
import com.example.prabh.autozang.retrofit.model.ServiceCenterResult
import com.example.prabh.autozang.room.dao.ServiceCenterDao
import com.example.prabh.autozang.room.table.ServiceCenters
import com.example.prabh.autozang.utility.ApiType
import com.example.prabh.autozang.utility.Response
import com.example.prabh.autozang.utility.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivityViewModel(private val serviceCenterDao: ServiceCenterDao) : ViewModel() {
    val compositeDisposable = CompositeDisposable()

    val response: MutableLiveData<Response> = MutableLiveData()
    val appiId="d141c211a6099a6460b108c1c1b86335"

    fun getDataFromTable(): LiveData<List<ServiceCenters>> {
        return serviceCenterDao.getData()
    }
    fun getSortedData(sortType:String):List<ServiceCenters> {
        var serviceCenteres:List<ServiceCenters>?=null
        when (sortType) {

            "distance" -> serviceCenteres=serviceCenterDao.getSortedDataDistance()

            "name" -> serviceCenteres= serviceCenterDao.getSortedDataName()

            "reviews" -> serviceCenteres= serviceCenterDao.getSortedDataReviews()
        }
        return serviceCenteres!!
    }

    fun getData() {
        val mApiService = Utils.interfaceService

        compositeDisposable.add(mApiService.getAllServiceCenters()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map { data -> saveData(data) }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    response.value = Response.loading(ApiType.GET_ALL_SERVICE_CENTERS)
                }
                .subscribe(
                        { it: String -> response.value = Response.success(ApiType.GET_ALL_SERVICE_CENTERS, it) },
                        { throwable: Throwable -> response.value = Response.error(ApiType.GET_ALL_SERVICE_CENTERS, throwable) }
                ))
    }

    fun getLocation(lat: Double, lon: Double) {
        val mApiService = Utils.locationService

        compositeDisposable.add(mApiService.getLocation(appiId,lat.toString(),lon.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    response.value = Response.loading(ApiType.GET_LOCATION)
                }
                .subscribe(
                        { it: CurrentLocation -> response.value = Response.success(ApiType.GET_LOCATION, it) },
                        { throwable: Throwable -> response.value = Response.error(ApiType.GET_LOCATION, throwable) }
                ))
    }

    private fun saveData(data: ServiceCenterResult): String {
        if (data.status == "ok") {
            serviceCenterDao.insertData(data.result!!)
        }
        return data.status!!
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}