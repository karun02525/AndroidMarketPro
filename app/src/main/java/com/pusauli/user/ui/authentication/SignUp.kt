package com.pusauli.user.ui.authentication

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.View
import com.pusauli.user.R
import com.pusauli.user.model.ResultDetails
import com.pusauli.user.ui.dashboard.BaseActivity
import com.pusauli.user.ui.dashboard.MainActivity
import com.pusauli.user.utils.*
import kotlinx.android.synthetic.main.activity_registation.*

class SignUp : BaseActivity() {

    private val sp by lazy { SharedPref.instance }
    private val instanceViewModel by lazy { AuthViewModel() }

    var gender = "Male"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registation)

        initObservers()
        initView()
    }

    private fun initView() {
        radio_group?.setOnCheckedChangeListener { _, checkedId ->
            gender = if (R.id.radio_male == checkedId) "Male" else "Female"
        }

        btnSignUp.setOnClickListener {
            val fname = edit_fname.text.toString()
            val lname = edit_lname.text.toString()
            val mob = edit_mobile.text.toString()
            val email = edit_email.text.toString()
            val pass = edit_password.text.toString()
            val city = edit_city.text.toString()

            when {
                fname.isBlank() -> {
                    hideSoftKeyboard()
                    showSnackBar(getString(R.string.login_validation_fname))
                }
                lname.isBlank() -> {
                    hideSoftKeyboard()
                    showSnackBar(getString(R.string.login_validation_lname))
                }
                mob.isBlank() -> {
                    hideSoftKeyboard()
                    showSnackBar(getString(R.string.login_validation_mobile_numbers))
                }
                mob.length != 10 -> {
                    hideSoftKeyboard()
                    showSnackBar(getString(R.string.login_validation_valid_mobile_numbers))
                }
                !email.isBlank() -> {
                    hideSoftKeyboard()
                    if (!email.isEmail()) {
                        showSnackBar(getString(R.string.login_validation_email))
                    }
                }
                pass.isBlank() -> {
                    hideSoftKeyboard()
                    showSnackBar(getString(R.string.login_validation_password))
                }
                pass.length < 7 -> {
                    hideSoftKeyboard()
                    showSnackBar(getString(R.string.login_validation_valid_password))
                }
                city.isBlank() -> {
                    hideSoftKeyboard()
                    showSnackBar(getString(R.string.login_validation_city))
                }
                else -> {
                    showProgress()
                    instanceViewModel.onSignUpSubmit(fname, lname, mob, email, pass, gender, city)
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
        log("Signup API", "Response => $data")
        sp.isLoginStatus = 1
        sp.userId = data!!.userId
        sp.userName = data.firstName + " " + data.lastName
        sp.mobileNumber = data.mobile
        sp.gender = data.gender
        sp.authToken = data.authentication
        sp.city = data.city

        if (sp.isLoginStatus != 0) {
            startNewActivityFlag(MainActivity::class.java)
        }
    }

    fun btnLogin(v: View) {
        startNewActivityNoFinish(LoginActivity::class.java)
    }
}
