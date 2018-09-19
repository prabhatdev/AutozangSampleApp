package com.example.prabh.autozang.dagger.subcomponent

import com.example.prabh.autozang.mvvm.activity.mainactivity.MainActivity
import com.example.prabh.autozang.dagger.scope.MainActivityScope
import com.example.prabh.autozang.dagger.submodule.MainActivityModule
import dagger.Subcomponent


@MainActivityScope
@Subcomponent(modules = [MainActivityModule::class])

interface MainActivityComponent {
    fun inject(target: MainActivity)
}