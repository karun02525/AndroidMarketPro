package com.pusauli.user.ui.authentication

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.pusauli.user.R
import com.pusauli.user.model.OTPModel
import com.pusauli.user.ui.dashboard.BaseActivity
import com.pusauli.user.utils.hideSoftKeyboard
import com.pusauli.user.utils.showSnackBar
import com.pusauli.user.utils.startNewActivityNoFinish
import kotlinx.android.synthetic.main.activity_verify_otp.*

class OtpVerifyActivity : BaseActivity() {

    private val instanceViewModel by lazy { AuthViewModel() }
    var mobile=""
    var otp=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_otp)

        initParse()
        initObservers()

    }

    private fun initParse() {
        mobile = intent.getStringExtra("mobile")?:""
        edt_mobile_number.setText(mobile)
    }

    fun btnOtpVerify(v: View){
        if(validation()){
            showProgress()
            instanceViewModel.onVerifyOtpAPI(mobile,otp)
        }
    }

    private fun initObservers() {
        instanceViewModel.requestDataOTPVerify.observe(this, Observer {
            hideProgress()
            successData(it)
        })
        instanceViewModel.errorMess.observe(this, Observer {
            hideProgress()
            showSnackBar(it!!)
        })
    }

    private fun successData(it: OTPModel?) {
           if(it!!.status!!){
               startActivity(Intent(this, CreateActivity::class.java)
                       .putExtra("mobile", mobile))
           }
    }


    fun alreadyLogin(v: View){
        startNewActivityNoFinish(LoginActivity::class.java)
    }



    private fun validation():Boolean{
        otp = edt_otp.text.toString()
        return when {
            otp.isBlank() -> {
                hideSoftKeyboard()
                showSnackBar(getString(R.string.login_validation_mobile_numbers))
                false
            }
            otp.length != 6 -> {
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
