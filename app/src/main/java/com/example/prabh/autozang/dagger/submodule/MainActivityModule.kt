package com.example.prabh.autozang.dagger.submodule

import android.arch.lifecycle.ViewModelProviders
import com.example.prabh.autozang.dagger.scope.MainActivityScope
import com.example.prabh.autozang.mvvm.activity.mainactivity.MainActivityViewModel
import com.example.prabh.autozang.mvvm.activity.mainactivity.MainActivityViewModelFactory
import com.example.prabh.autozang.mvvm.application.AutozangApplication
import com.example.prabh.autozang.room.dao.ServiceCenterDao
import com.example.prabh.autozang.room.handler.AutozangDatabase
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule
{
    @Provides
    @MainActivityScope
    fun providePortfolioDao(autozangDatabase: AutozangDatabase) : ServiceCenterDao = autozangDatabase.serviceCenterDao()

    @Provides
    @MainActivityScope
    fun provideMainActivityViewModelFactory(serviceCenterDao: ServiceCenterDao): MainActivityViewModelFactory = MainActivityViewModelFactory(serviceCenterDao)

    @Provides
    @MainActivityScope
    fun provideMainActivityViewModel(application: AutozangApplication, mainActivityViewModelFactory: MainActivityViewModelFactory):MainActivityViewModel=
            ViewModelProviders.of(application,mainActivityViewModelFactory).get(MainActivityViewModel::class.java)
}