package com.pusauli.user.ui.dialog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.pusauli.user.R
import kotlinx.android.synthetic.main.dialog_vender_verification.view.*

class DialogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)

        //https://github.com/kmvignesh

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.dialog_vender_verification, null)

        val close = view.popup_window
        close.setOnClickListener {
            dialog.dismiss()
        }
        dialog.setCancelable(false)
        dialog.setContentView(view)
        dialog.show()

    }



}