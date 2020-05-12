package com.pusauli.user.ui.authentication

import android.annotation.SuppressLint
import androidx.lifecycle.Observer
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import android.text.method.PasswordTransformationMethod
import com.pusauli.user.R
import com.pusauli.user.mvvm.AuthViewModel
import com.pusauli.user.ui.dashboard.BaseActivity
import com.pusauli.user.utils.*
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_registation.edit_lname
import kotlinx.android.synthetic.main.activity_registation.edit_mobile
import kotlinx.android.synthetic.main.activity_registation.edit_password
import kotlinx.android.synthetic.main.alert_dialog.*

class ForgotPasswordActivity : BaseActivity() {

    private val instanceViewModel by lazy { AuthViewModel() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        initObservers()
        initView()
    }

    private fun initView() {
        edit_password.transformationMethod = PasswordTransformationMethod()
        edit_cnf_password.transformationMethod = PasswordTransformationMethod()

        btnSubmit.setOnClickListener {
            val mob = edit_mobile.text.toString()
            val lname = edit_lname.text.toString()
            val pass = edit_password.text.toString()
            val cnf_password = edit_cnf_password.text.toString()

            when {
                mob.isBlank() -> {
                    hideSoftKeyboard()
                    showSnackBar(getString(R.string.login_validation_mobile_numbers))
                }
                mob.length != 10 -> {
                    hideSoftKeyboard()
                    showSnackBar(getString(R.string.login_validation_valid_mobile_numbers))
                }
                lname.isBlank() -> {
                    hideSoftKeyboard()
                    showSnackBar(getString(R.string.alert_name))
                }
                pass.isBlank() -> {
                    hideSoftKeyboard()
                    showSnackBar(getString(R.string.enter_new_password))
                }
                pass.length <=7 -> {
                    hideSoftKeyboard()
                    showSnackBar(getString(R.string.login_validation_valid_password))
                }
                cnf_password.isBlank() -> {
                    hideSoftKeyboard()
                    showSnackBar(getString(R.string.crf_pass))
                }
                cnf_password.length <=7 -> {
                    hideSoftKeyboard()
                    showSnackBar(getString(R.string.login_validation_valid_password))
                }
                pass != cnf_password -> showSnackBar(getString(R.string.password_not_match))
                else -> {
                    showProgress()
                    instanceViewModel.forgotPassword(mob,lname,cnf_password)
                }
            }
        }
    }


    private fun initObservers() {
        instanceViewModel.successMessage.observe(this, Observer {
            hideProgress()
            log("Forgot API", "Response => $it")
            if (it != null) {
                showDialog(it)
            }

        })
        instanceViewModel.errorMess.observe(this, Observer {
            hideProgress()
            showSnackBar(it!!)
        })
    }

    @SuppressLint("SetTextI18n")
    private fun showDialog(m: String) {
        val inflater = this.layoutInflater
        val layout = inflater.inflate(R.layout.alert_dialog, null)
        val alertDio = AlertDialog.Builder(this).setView(layout).show()
        alertDio.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDio.setCancelable(false)
        alertDio.setContentView(R.layout.alert_dialog)
        alertDio.tv_messs.text = m
        val ok = alertDio.tv_ok
        ok.setOnClickListener {
            startNewActivityFlag(LoginActivity::class.java)
            alertDio.dismiss()
        }
    }

}
