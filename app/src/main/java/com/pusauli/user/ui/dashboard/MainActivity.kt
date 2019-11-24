package com.pusauli.user.ui.dashboard

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import com.pusauli.user.R
import com.pusauli.user.ui.dashboard.fragment.HomeFragment
import com.pusauli.user.ui.dashboard.fragment.NotificationFragment
import com.pusauli.user.ui.dashboard.fragment.ProfileFragment
import com.pusauli.user.ui.dashboard.fragment.StoreFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
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
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
        setFragmentSupport(HomeFragment.newInstance())
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
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
