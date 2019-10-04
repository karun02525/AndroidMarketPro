package com.pusauli.user.ui.vender

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import com.pusauli.user.R
import com.pusauli.user.ui.dashboard.BaseActivity
import com.pusauli.user.ui.vender.shop_fragment.GalleryFragment
import com.pusauli.user.ui.vender.shop_fragment.RegistationFragment
import com.pusauli.user.ui.vender.shop_fragment.VenderFragment
import kotlinx.android.synthetic.main.activity_vender.*


class VenderActivity : BaseActivity() {


    var action: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vender)

        initExtraParse()
    }

    private fun initExtraParse() {
        try {
            action = intent.getIntExtra("actionFragment", 0)
        } catch (e: Exception) {
        }


        if (action == 1) {
            venderFragment()
        }
        if (action == 2) {
            registerFragment()
        }


        tab1.setOnClickListener {
            venderFragment()
        }
        tab2.setOnClickListener {
            registerFragment()
        }
        tab3.setOnClickListener {
            galleryFragment()
        }

    }

    fun onBack(){
        onBackPressed()
    }

    private fun venderFragment() {
        iv_tab1.setImageResource(R.drawable.ic_shopping_bag_active)
        iv_tab2.setImageResource(R.drawable.ic_register_deactive)
        iv_tab3.setImageResource(R.drawable.ic_image_gallery_deactive)

        tv_tab1.setTextColor(Color.parseColor("#F3655C"))
        tv_tab2.setTextColor(Color.parseColor("#716f6f"))
        tv_tab3.setTextColor(Color.parseColor("#716f6f"))
        setFragmentSupport(VenderFragment.newInstance())
    }

    private fun registerFragment() {
        iv_tab1.setImageResource(R.drawable.ic_shopping_bag_deactive)
        iv_tab2.setImageResource(R.drawable.ic_register_active)
        iv_tab3.setImageResource(R.drawable.ic_image_gallery_deactive)

        tv_tab1.setTextColor(Color.parseColor("#716f6f"))
        tv_tab2.setTextColor(Color.parseColor("#F3655C"))
        tv_tab3.setTextColor(Color.parseColor("#716f6f"))
        setFragmentSupport(RegistationFragment.newInstance())
    }

    fun galleryFragment() {
        iv_tab1.setImageResource(R.drawable.ic_shopping_bag_deactive)
        iv_tab2.setImageResource(R.drawable.ic_register_deactive)
        iv_tab3.setImageResource(R.drawable.ic_image_gallery_active)

        tv_tab1.setTextColor(Color.parseColor("#716f6f"))
        tv_tab2.setTextColor(Color.parseColor("#716f6f"))
        tv_tab3.setTextColor(Color.parseColor("#F3655C"))
        setFragmentSupport(GalleryFragment.newInstance())
    }


    private fun setFragmentSupport(f: Fragment) {
        this.supportFragmentManager.beginTransaction()
            .replace(R.id.main_fram_vender, f)
            .commit()
    }

    /*Camera crop */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragments = supportFragmentManager.fragments
        for (f in fragments) {
            if (f is RegistationFragment) {
                f.onActivityResult(requestCode, resultCode, data)
            }
            if (f is GalleryFragment) {
                f.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

}
