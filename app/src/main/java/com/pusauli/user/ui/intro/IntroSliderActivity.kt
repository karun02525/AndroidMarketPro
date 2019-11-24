package com.pusauli.user.ui.intro

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pusauli.user.R
import kotlinx.android.synthetic.main.into_activity.*

class IntroSliderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.into_activity)

        view_pager2.adapter = ViewPagerAdapter()
       // view_pager2.orientation = ViewPager2.ORIENTATION_VERTICAL

        //below line can be used to change the orientation to vertical i.e. vertical swipe
        //view_pager2.orientation = ViewPager2.ORIENTATION_VERTICAL
    }
}