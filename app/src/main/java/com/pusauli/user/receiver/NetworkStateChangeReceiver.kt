package com.pusauli.user.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class NetworkStateChangeReceiver : BroadcastReceiver() {

    companion object {
        const val NETWORK_AVAILABLE_ACTION = "com.pusauli.user.NetworkAvailable"
        const val IS_NETWORK_AVAILABLE = "isNetworkAvailable"
    }


    override fun onReceive(context: Context, intent: Intent) {
        val networkStateIntent =
            Intent(NETWORK_AVAILABLE_ACTION)
        networkStateIntent.putExtra(
            IS_NETWORK_AVAILABLE,
            isConnectedToInternet(context)
        )
        LocalBroadcastManager.getInstance(context).sendBroadcast(networkStateIntent)
    }

    private fun isConnectedToInternet(context: Context?): Boolean {
        return try {
            if (context != null) {
                val connectivityManager =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val networkInfo = connectivityManager.activeNetworkInfo
                return networkInfo != null && networkInfo.isConnected
            }
            false
        } catch (e: Exception) {
            Log.e(NetworkStateChangeReceiver::class.java.name, e.message)
            false
        }
    }

}