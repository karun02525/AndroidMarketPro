package com.pusauli.user.ui.dashboard


import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pusauli.user.language_change.MyContextWrapper
import com.pusauli.user.language_change.MyPreference
import com.pusauli.user.utils.CustomProgressDialog
import com.pusauli.user.utils.leftToRight
import com.pusauli.user.utils.rightToLeft


abstract class BaseActivity : AppCompatActivity() {
    private val pd by lazy { CustomProgressDialog(this, false) }
    lateinit var myPreference: MyPreference


    override fun attachBaseContext(newBase: Context?) {
        myPreference = MyPreference(newBase!!)
        val lang = myPreference.getLoginCount()
        super.attachBaseContext(MyContextWrapper.wrap(newBase,lang!!))
    }

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
        pd.let { pd.show()}
    }

    fun hideProgress() {
        pd.let { pd.dismiss()}
    }
}
