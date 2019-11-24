package com.pusauli.user.ui.authentication

import androidx.lifecycle.Observer
import android.os.Bundle
import android.view.View
import com.pusauli.user.R
import com.pusauli.user.model.ResultDetails
import com.pusauli.user.mvvm.AuthViewModel
import com.pusauli.user.ui.dashboard.BaseActivity
import com.pusauli.user.ui.dashboard.MainActivity
import com.pusauli.user.utils.*
import kotlinx.android.synthetic.main.activity_create.*

class CreateActivity : BaseActivity() {

    private val sp by lazy { SharedPref.instance }
    private val instanceViewModel by lazy { AuthViewModel() }
    var gender = "Male"
    var mobile = ""
    var fname = ""
    var lname = ""
    var pass = ""
    var cnfpass = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        initParse()
        initObservers()

    }

    private fun initParse() {
        mobile = intent.getStringExtra("mobile") ?: ""
        edt_mobile_number.setText(mobile)
    }


    fun btnSubmit(v: View) {
        if (validation()) {
            showProgress()
            instanceViewModel.onSignUpSubmit(mobile, fname, lname, pass, gender)
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
        log("Login API", "Response => $data")
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

    fun alreadyLogin(v: View) {
        startNewActivityNoFinish(LoginActivity::class.java)
    }


    private fun validation(): Boolean {

        fname = edt_first_name.text.toString()
        lname = edt_last_name.text.toString()
        pass = edt_password.text.toString()
        cnfpass = edt_cnf_pass.text.toString()

        return when {
            fname.isBlank() -> {
                hideSoftKeyboard()
                showSnackBar(getString(R.string.login_validation_fname))
                false
            }
            lname.isBlank() -> {
                hideSoftKeyboard()
                showSnackBar(getString(R.string.login_validation_lname))
                false
            }
            pass.isBlank() -> {
                hideSoftKeyboard()
                showSnackBar(getString(R.string.login_validation_password))
                false
            }
            pass.length < 7 -> {
                hideSoftKeyboard()
                showSnackBar(getString(R.string.login_validation_valid_password))
                false
            }
            cnfpass.isBlank() -> {
                hideSoftKeyboard()
                showSnackBar(getString(R.string.crf_pass))
                false
            }
            cnfpass.length < 7 -> {
                hideSoftKeyboard()
                showSnackBar(getString(R.string.crf_pass_valid))
                false
            }
            pass != cnfpass -> {
                hideSoftKeyboard()
                showSnackBar(getString(R.string.password_not_match))
                false
            }
            else -> {
                radio_group?.setOnCheckedChangeListener { _, checkedId ->
                    gender = if (R.id.radio_male == checkedId) "Male" else "Female"
                }
                true
            }
        }
    }
}
