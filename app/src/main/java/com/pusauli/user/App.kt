package com.pusauli.user

import android.app.Application
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import com.pusauli.user.receiver.NetworkStateChangeReceiver

class App : Application() {


    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }


    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        appContext = applicationContext
        registerForNetworkChangeEvents()
    }

    companion object {
        @get:Synchronized
        var appContext: Context? = null
            private set

        private val WIFI_STATE_CHANGE_ACTION = "android.net.wifi.WIFI_STATE_CHANGED"

    }

    private fun registerForNetworkChangeEvents() {
        val networkStateChangeReceiver = NetworkStateChangeReceiver()
        appContext!!.registerReceiver(networkStateChangeReceiver, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
        appContext!!.registerReceiver(networkStateChangeReceiver, IntentFilter(App.WIFI_STATE_CHANGE_ACTION))
    }


}