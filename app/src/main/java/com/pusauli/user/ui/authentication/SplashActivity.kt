package com.pusauli.user.ui.authentication

import android.os.Bundle
import android.os.Handler
import com.pusauli.user.R
import com.pusauli.user.ui.dashboard.BaseActivity
import com.pusauli.user.ui.dashboard.MainActivity
import com.pusauli.user.utils.SharedPref
import com.pusauli.user.utils.getDeviceToken
import com.pusauli.user.utils.log
import com.pusauli.user.utils.startNewActivityFlag
import kotlin.math.log

class SplashActivity : BaseActivity() {


    private val sp by lazy { SharedPref.instance }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        showProgress()
        initOperation()
    }

    private fun initOperation() {
        Handler().postDelayed({
            if(sp.isLoginStatus !=0){
                startNewActivityFlag(MainActivity::class.java)
            }else {
                startNewActivityFlag(LoginActivity::class.java)
            }
            hideProgress()
            try {
                log("FCM Token=>: ", getDeviceToken()!!)
            } catch (e: Exception) {
            }
        },2000)


    }


}
