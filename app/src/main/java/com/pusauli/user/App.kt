package com.pusauli.user

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import androidx.appcompat.app.AppCompatDelegate

class App : Application() {

    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }


    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        appContext = applicationContext

    }

    companion object {
        @get:Synchronized
        var appContext: Context? = null
            private set

    }


}