package com.example.prabh.autozang.dagger.component

import com.example.prabh.autozang.dagger.module.ActivityModule
import com.example.prabh.autozang.dagger.subcomponent.MainActivityComponent
import com.example.prabh.autozang.dagger.submodule.MainActivityModule
import com.example.prabh.autozang.mvvm.application.AutozangApplication
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ActivityModule::class])
interface ActivityComponent{
    fun inject(target: AutozangApplication)

    fun plusMainActivityComponent(mainActivityModule: MainActivityModule):MainActivityComponent
}