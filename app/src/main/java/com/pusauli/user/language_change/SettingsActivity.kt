package com.pusauli.user.language_change

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pusauli.user.R
import com.pusauli.user.ui.dashboard.MainActivity
import kotlinx.android.synthetic.main.activity_language.*

class SettingsActivity : AppCompatActivity() {

    lateinit var myPreference: MyPreference
    lateinit var context: Context

    var langCode="en"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language)
        context = this
        myPreference = MyPreference(this)


        val lang = myPreference.getLoginCount()

        /*when(lang){
            "en"->tv_english
            "hi"->tv_hindi
        }*/


        ll_english.setOnClickListener {
            langCode="en"
        }

        ll_hindi.setOnClickListener {
            langCode="hi"
        }



        btn_continue.setOnClickListener {
            myPreference.setLoginCount(langCode)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }



}
