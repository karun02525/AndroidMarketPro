package com.pusauli.user.utils

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.widget.ProgressBar
import com.pusauli.user.R


class LoadingDialog {
    private var mProgress: Dialog? = null
    private var progressDrawable: Drawable? = null
    fun dismissLoader() {
        try {
            if (mProgress != null) {
                mProgress!!.cancel()
                mProgress = null
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getProgressDrawableColors(context: Context): IntArray {
        val colors = IntArray(4)
        colors[0] = ContextCompat.getColor(context, R.color.colorPrimary)
        colors[1] = ContextCompat.getColor(context, R.color.green_light)
        colors[2] = ContextCompat.getColor(context, R.color.yellow)
        colors[3] = ContextCompat.getColor(context, R.color.purple)
        return colors
    }



    companion object {

        internal var mInstance: LoadingDialog? = null

        fun initLoader() {
            if (mInstance == null)
                mInstance = LoadingDialog()
        }

        val loader: LoadingDialog
            get() {
                if (mInstance != null)
                    return mInstance!!
                else {
                    mInstance = LoadingDialog()
                    return mInstance!!
                }
            }
    }
}
