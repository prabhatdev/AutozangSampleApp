package com.example.prabh.autozang.dagger.module

import android.content.Context
import com.example.prabh.autozang.mvvm.application.AutozangApplication
import com.example.prabh.autozang.room.handler.AutozangDatabase
import com.example.prabh.autozang.utility.Utils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ActivityModule(private val autozangApplication: AutozangApplication){
    @Provides
    @Singleton
    fun provideAutozangApplication():AutozangApplication=autozangApplication

    @Provides
    @Singleton
    fun provideContext() : Context =autozangApplication

    @Provides
    @Singleton
    fun provideUtil(context: Context): Utils = Utils.provideUtil(context)


    @Provides
    @Singleton
    fun provideWisOptDatabase(context: Context): AutozangDatabase = AutozangDatabase.getInstance(context)!!
}