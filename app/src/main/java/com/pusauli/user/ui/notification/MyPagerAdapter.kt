package com.pusauli.user.ui.notification

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.pusauli.user.ui.notification.fragment.GeneralNotificationFragment
import com.pusauli.user.ui.notification.fragment.PersonalNotificationFragment


class MyPagerAdapter(fm: FragmentManager, private var mNumOfTabs: Int) :
    FragmentPagerAdapter(fm) {

    override fun getItem(pos: Int): Fragment {
        return when (pos) {
            0 -> GeneralNotificationFragment.newInstance()
            1 -> PersonalNotificationFragment.newInstance()
            else -> PersonalNotificationFragment.newInstance()
        }
    }

    override fun getCount(): Int {
        return mNumOfTabs
    }

}
