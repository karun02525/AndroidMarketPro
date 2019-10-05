package com.pusauli.user.ui.authentication

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.pusauli.user.R
import com.pusauli.user.model.ResultOTP
import com.pusauli.user.mvvm.AuthViewModel
import com.pusauli.user.ui.dashboard.BaseActivity
import com.pusauli.user.utils.hideSoftKeyboard
import com.pusauli.user.utils.showSnackBar
import com.pusauli.user.utils.startNewActivityNoFinish
import kotlinx.android.synthetic.main.activity_send_otp.*

class OtpSendActivity : BaseActivity() {

    private val instanceViewModel by lazy { AuthViewModel() }
    private var mobileNumber=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_otp)

        initObservers()
    }

    fun btnSendOTP(v: View){
        if(validation()){
            showProgress()
            instanceViewModel.onSendOtpAPI(mobileNumber)
        }
    }


    private fun initObservers() {
        instanceViewModel.requestDataOTP.observe(this, Observer {
            hideProgress()
            successData(it)
        })
        instanceViewModel.errorMess.observe(this, Observer {
            hideProgress()
            showSnackBar(it!!)
        })
    }

    private fun successData(it: ResultOTP?) {

        if(it!!.isCreate==0){
            startActivity(Intent(this, OtpVerifyActivity::class.java)
                .putExtra("mobile", mobileNumber))
        }
        if(it.isCreate==1){
            startActivity(Intent(this, CreateActivity::class.java)
                .putExtra("mobile", mobileNumber))
        }
    }


    fun alreadyHaveOtp(v: View){
        if(validation()){
            startActivity(Intent(this, OtpVerifyActivity::class.java)
                .putExtra("mobile", mobileNumber))
        }
    }

    fun alreadyLogin(v: View){
        startNewActivityNoFinish(LoginActivity::class.java)
    }


    private fun validation():Boolean{
        mobileNumber = edt_mobile_number.text.toString()
        return when {
            mobileNumber.isBlank() -> {
                hideSoftKeyboard()
                showSnackBar(getString(R.string.login_validation_mobile_numbers))
                false
            }
            mobileNumber.length != 10 -> {
                hideSoftKeyboard()
                showSnackBar(getString(R.string.login_validation_valid_mobile_numbers))
                false
            }
            else ->{
                true
            }
        }
    }
}
