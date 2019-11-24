package com.pusauli.user.ui.authentication

import android.annotation.SuppressLint
import androidx.lifecycle.Observer
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.pusauli.user.R
import com.pusauli.user.mvvm.AuthViewModel
import com.pusauli.user.ui.dashboard.BaseActivity
import com.pusauli.user.utils.SharedPref
import com.pusauli.user.utils.hideSoftKeyboard
import com.pusauli.user.utils.log
import com.pusauli.user.utils.showSnackBar
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.activity_forgot_password.btnSubmit
import kotlinx.android.synthetic.main.activity_forgot_password.edit_cnf_password
import kotlinx.android.synthetic.main.alert_dialog.*

class ChangePasswordActivity : BaseActivity() {

    private val instanceViewModel by lazy { AuthViewModel() }
    private val sp by lazy { SharedPref.instance }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        initObservers()
        initView()
    }

    private fun initView() {

        btnSubmit.setOnClickListener {
            val oldPassword = edit_old_password.text.toString()
            val newPassword = edit_new_password.text.toString()
            val cnfPassword = edit_cnf_password.text.toString()

            when {
                oldPassword.isBlank() -> {
                    hideSoftKeyboard()
                    showSnackBar(getString(R.string.enter_old_password))
                }
                oldPassword.length <= 7 -> {
                    hideSoftKeyboard()
                    showSnackBar(getString(R.string.login_validation_valid_password))
                }
                newPassword.isBlank() -> {
                    hideSoftKeyboard()
                    showSnackBar(getString(R.string.enter_new_password))
                }
                newPassword.length <= 7 -> {
                    hideSoftKeyboard()
                    showSnackBar(getString(R.string.login_validation_valid_password))
                }
                cnfPassword.isBlank() -> {
                    hideSoftKeyboard()
                    showSnackBar(getString(R.string.crf_pass))
                }
                cnfPassword.length <= 7 -> {
                    hideSoftKeyboard()
                    showSnackBar(getString(R.string.login_validation_valid_password))
                }
                newPassword != cnfPassword -> showSnackBar(getString(R.string.password_not_match))
                else -> {
                    showProgress()
                    instanceViewModel.changePassword(sp.mobileNumber!!,oldPassword, cnfPassword)
                }
            }
        }
    }


    private fun initObservers() {
        instanceViewModel.successMessage.observe(this, Observer {
            hideProgress()
            log("Change Password API", "Response => $it")
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
            finish()
            onBackPressed()
            alertDio.dismiss()
        }
    }

}
