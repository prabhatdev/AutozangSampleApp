package com.example.prabh.autozang.mvvm.application

import android.support.v7.app.AppCompatActivity
import com.example.prabh.autozang.dagger.component.ActivityComponent
import com.example.prabh.autozang.dagger.component.DaggerActivityComponent
import com.example.prabh.autozang.dagger.module.ActivityModule
import com.example.prabh.autozang.dagger.subcomponent.MainActivityComponent
import com.example.prabh.autozang.dagger.submodule.MainActivityModule
import com.example.prabh.autozang.utility.Utils
import javax.inject.Inject

abstract class AutozangApplication:AppCompatActivity(){


    @Inject
    lateinit var utils: Utils

    private val activityComponent:ActivityComponent by lazy {
        DaggerActivityComponent
                .builder()
                .activityModule(ActivityModule(this))
                .build()
    }

    val mainActivityComponent: MainActivityComponent by lazy {
        activityComponent.plusMainActivityComponent(MainActivityModule())
    }
    fun isConnected() = utils.isInternetAvailable(this)
}