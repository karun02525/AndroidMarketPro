package com.pusauli.user.ui.common

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.pusauli.user.R
import com.pusauli.user.utils.loadImage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_zoom_image.*

class ZoomImageActivity : AppCompatActivity() {

    var galleryAvatar=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zoom_image)

        try {
            galleryAvatar = intent.getStringExtra("galleryAvatar")
        } catch (e: Exception) {
        }
        initView()

        btn_done.setOnClickListener {
            finish()
        }
    }

    private fun initView() {

        val color = arrayOf(ColorDrawable(ContextCompat.getColor(this,
            R.color.black_tint)),
            ColorDrawable(Color.BLACK))
        val trans = TransitionDrawable(color)
        ll_change_color.background = trans
        trans.startTransition(1000)
        ViewCompat.setTransitionName(img_pic, "full_imageview")
        img_pic.loadImage(galleryAvatar)
    }
}
