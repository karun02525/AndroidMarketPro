package com.pusauli.user.ui.dashboard


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.pusauli.user.utils.CustomProgressDialog
import com.pusauli.user.utils.LoadingDialog
import com.pusauli.user.utils.leftToRight
import com.pusauli.user.utils.rightToLeft


abstract class BaseActivity : AppCompatActivity() {
    private val pd by lazy { CustomProgressDialog(this, false) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        leftToRight()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        rightToLeft()
        return true
    }


    override fun onBackPressed() {
        super.onBackPressed()
        rightToLeft()
    }


    fun showProgress() {
        pd.show()
    }

    fun hideProgress() {
        pd.dismiss()
    }
}
