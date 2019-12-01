package com.pusauli.user.ui.authentication

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.pusauli.user.R
import com.pusauli.user.ui.dashboard.BaseActivity
import com.pusauli.user.ui.dashboard.MainActivity
import com.pusauli.user.utils.SharedPref
import com.pusauli.user.utils.getDeviceToken
import android.view.animation.AnimationUtils
import com.pusauli.user.ui.intro.IntroSliderActivity
import com.pusauli.user.utils.log
import com.pusauli.user.utils.startNewActivityFlag
import kotlinx.android.synthetic.main.activity_splash.*
import kotlin.math.log

class SplashActivity : BaseActivity() {


    private val sp by lazy { SharedPref.instance }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
        iv_logo.startAnimation(AnimationUtils.loadAnimation(this,R.anim.splash_in))

        //showProgress()
        Handler().postDelayed({
            iv_logo.startAnimation(AnimationUtils.loadAnimation(this,R.anim.splash_out))
            initOperation()
        },2500)
    }

    private fun initOperation() {
        Handler().postDelayed({
            iv_logo.visibility = View.GONE
            if(sp.isLoginStatus !=0){
                startNewActivityFlag(MainActivity::class.java)
                //startNewActivityFlag(IntroSliderActivity::class.java)
            }else {
                startNewActivityFlag(LoginActivity::class.java)
            }
           // hideProgress()
            try {
                log("FCM Token=>: ", getDeviceToken()!!)
            } catch (e: Exception) {
            }
        },500)


    }


}
