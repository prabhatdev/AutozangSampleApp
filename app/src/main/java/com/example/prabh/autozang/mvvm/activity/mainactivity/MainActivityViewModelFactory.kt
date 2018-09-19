package com.example.prabh.autozang.mvvm.activity.mainactivity

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.prabh.autozang.room.dao.ServiceCenterDao
import java.lang.IllegalArgumentException

class MainActivityViewModelFactory(val serviceCenterDao: ServiceCenterDao) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(serviceCenterDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}