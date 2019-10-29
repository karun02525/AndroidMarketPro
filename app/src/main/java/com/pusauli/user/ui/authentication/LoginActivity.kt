package com.pusauli.user.ui.authentication

import android.arch.lifecycle.Observer
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.PasswordTransformationMethod
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import com.pusauli.user.R
import com.pusauli.user.model.ResultDetails
import com.pusauli.user.mvvm.AuthViewModel
import com.pusauli.user.ui.dashboard.BaseActivity
import com.pusauli.user.ui.dashboard.MainActivity
import com.pusauli.user.utils.*
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : BaseActivity() {

    companion object{
        var isSessionExp=false
    }
    private val sp by lazy { SharedPref.instance }
    private val instanceViewModel by lazy { AuthViewModel() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initObservers()
        initView()
    }

    override fun onStart() {
        super.onStart()
        try {
            if(isSessionExp){
                try {
                    toast("Your session has been expired. You have to login again.")
                } catch (e: Exception) {
                }
            }
        } catch (e: Exception) {
        }
    }


    private fun initView() {
        val s="Welcome to Digital Pusauli"
        val spannable=SpannableString(s)
        val t1=ForegroundColorSpan(Color.RED)
        val t2=ForegroundColorSpan(Color.GREEN)
        val t3=BackgroundColorSpan(Color.YELLOW)

        spannable.setSpan(t1,0,7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(t2,8,10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(t3,19,26, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        tv_title.text=spannable


        edit_password.transformationMethod = PasswordTransformationMethod()
        btnForgotPassword.setOnClickListener {
            startNewActivityNoFinish(ForgotPasswordActivity::class.java)
        }

        btnLogin.setOnClickListener {
            val mobileNumber = edit_mobile.text.toString()
            val password = edit_password.text.toString()

            when {
                mobileNumber.isBlank() -> {
                    hideSoftKeyboard()
                    showSnackBar(getString(R.string.login_validation_mobile_numbers))
                }
                mobileNumber.length != 10 -> {
                    hideSoftKeyboard()
                    showSnackBar(getString(R.string.login_validation_valid_mobile_numbers))
                }
                password.isBlank() -> {
                    hideSoftKeyboard()
                    showSnackBar(getString(R.string.login_validation_password))
                }
                password.length < 7 -> {
                    hideSoftKeyboard()
                    showSnackBar(getString(R.string.login_validation_valid_password))
                }
                else ->{
                    showProgress()
                    instanceViewModel.onLoginSubmit(mobileNumber, password)
                }
            }
        }
    }


    private fun initObservers() {
        instanceViewModel.requestData.observe(this, Observer {
            hideProgress()
            successfullyLogin(it)
        })
        instanceViewModel.errorMess.observe(this, Observer {
            hideProgress()
            showSnackBar(it!!)
        })
    }

    private fun successfullyLogin(data: ResultDetails?) {
        log("Login API","Response => $data")
        sp.isLoginStatus = 1
        sp.userId = data!!.userId
        sp.userName = data.firstName + " " + data.lastName
        sp.mobileNumber = data.mobile
        sp.gender = data.gender
        sp.city = data.city
        sp.profileAvatar = data.user_avatar
        sp.authToken = data.authentication

        if (sp.isLoginStatus != 0) {
            startNewActivityFlag(MainActivity::class.java)
        }
    }

    fun btnSignUp(v: View) {
        startNewActivityNoFinish(OtpSendActivity::class.java)
    }
}
