package com.pusauli.user.ui.dashboard

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.pusauli.user.R
import com.pusauli.user.receiver.NetworkStateChangeReceiver
import com.pusauli.user.receiver.NetworkStateChangeReceiver.Companion.IS_NETWORK_AVAILABLE
import com.pusauli.user.ui.dashboard.fragment.HomeFragment
import com.pusauli.user.ui.dashboard.fragment.NotificationFragment
import com.pusauli.user.ui.dashboard.fragment.ProfileFragment
import com.pusauli.user.ui.dashboard.fragment.StoreFragment
import com.pusauli.user.utils.showSnackBar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : BaseActivity() {

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            initNetworkBroadCast()
            when (item.itemId) {
                R.id.navigation_home -> {
                    setFragmentSupport(HomeFragment.newInstance())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_stores -> {
                    setFragmentSupport(StoreFragment.newInstance())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_notifications -> {
                    setFragmentSupport(NotificationFragment.newInstance())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_profile -> {
                    setFragmentSupport(ProfileFragment.newInstance())
                    return@OnNavigationItemSelectedListener true }

            }
            false
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        initNetworkBroadCast()


        setFragmentSupport(HomeFragment.newInstance())
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


    }

    private fun initNetworkBroadCast() {
        val intentFilter =
            IntentFilter(NetworkStateChangeReceiver.NETWORK_AVAILABLE_ACTION)
        LocalBroadcastManager.getInstance(this).registerReceiver(object : BroadcastReceiver() {
            @SuppressLint("SetTextI18n")
            override fun onReceive(context: Context, intent: Intent) {
                val isNetworkAvailable = intent.getBooleanExtra(IS_NETWORK_AVAILABLE, false)
                if (isNetworkAvailable){
                    tv_network.text="Back Online"
                    tv_network.setBackgroundColor(Color.parseColor("#22c646"))
                    GlobalScope.launch(Dispatchers.Main) {
                        delay(1500)
                        tv_network.visibility= View.GONE
                    }
                }else{
                    tv_network.visibility= View.VISIBLE
                    tv_network.text="No connection"
                    tv_network.setBackgroundColor(Color.BLACK)
                }
            }
        }, intentFilter)
    }

    private fun setFragmentSupport(f: Fragment) {
        this.supportFragmentManager.beginTransaction()
            .replace(R.id.main_fram, f)
            .commit()

    }

    /*Camera crop */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragments = supportFragmentManager.fragments
        for (f in fragments) {
            if (f is ProfileFragment) {
                f.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

}
