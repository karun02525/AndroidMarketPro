package com.pusauli.user.ui.store_list.details

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.pusauli.user.R
import com.pusauli.user.model.GalleryDetails
import com.pusauli.user.model.ResultStoreDetails
import com.pusauli.user.mvvm.VenderViewListViewModel
import com.pusauli.user.network.Const
import com.pusauli.user.ui.dashboard.BaseActivity
import com.pusauli.user.ui.store_list.details.fragement.GalleryViewFragment
import com.pusauli.user.ui.store_list.details.fragement.MapViewFragment
import com.pusauli.user.ui.store_list.details.fragement.ViewFragment
import com.pusauli.user.utils.loadImage
import com.pusauli.user.utils.loadImageProfile
import com.pusauli.user.utils.log
import kotlinx.android.synthetic.main.activity_details.*


class DetailsActivity : BaseActivity() {

    var title = arrayOf<CharSequence>("View", "Gallery", "Map")
    private val instanceViewModel by lazy { VenderViewListViewModel() }
    var result: ResultStoreDetails? = null
    var galleryList: ArrayList<GalleryDetails> = arrayListOf()
    var vender_id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        initObservers()
        initView()

        backBTN.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initView() {
        try {
            vender_id = intent.getStringExtra("vender_id")
        } catch (e: Exception) {
            vender_id = ""
        }

        setProgress(true)
        instanceViewModel.getStoreDetailsApiCall(vender_id!!)
    }

    private fun initObservers() {
        instanceViewModel.requestStoreDetailsData.observe(this, Observer {
            setProgress(false)
            uploadData(it)
        })
        instanceViewModel.errorMess.observe(this, Observer {
            setProgress(false)
        })
    }

    private fun uploadData(it: ResultStoreDetails?) {
        try {
            iv_vender_shopBack.loadImage(Const.STORE_AVATAR_BASE_URL + it!!.shopAvatar!!)
        } catch (e: Exception) {
        }
        try {
            clg_logo_vender.loadImageProfile(Const.PROFILE_AVATAR_BASE_URL + it!!.userAvatar!!)
        } catch (e: Exception) {
        }

        result = it

        if(it!!.galleryList !=null ) {
            galleryList = (it.galleryList as ArrayList<GalleryDetails>?)!!
        }

        try {
            setUpTabs()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun setUpTabs() {
        pager!!.adapter = MainPagerAdapter(this.supportFragmentManager, title, title.size)
        tabs!!.setupWithViewPager(pager)
    }

    inner class MainPagerAdapter(
        fm: FragmentManager,
        private var titles: Array<CharSequence>,
        var numbOfTabs: Int
    ) : FragmentStatePagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> {
                    val f = ViewFragment()
                    val bundle = Bundle()
                    bundle.putParcelable("data", result)
                    f.arguments = bundle
                    return f
                }
                1 -> {
                    val f = GalleryViewFragment()
                    val bundle = Bundle()
                    bundle.putParcelableArrayList("gallery", galleryList)
                    f.arguments = bundle
                    return f
                }
                else -> {
                    val f = MapViewFragment()
                    val bundle = Bundle()
                    bundle.putParcelable("data", result)
                    f.arguments = bundle
                    return f
                }
            }
        }

        override fun getPageTitle(position: Int): CharSequence {
            return titles[position]
        }

        override fun getCount(): Int {
            return numbOfTabs
        }
    }

    private fun logs(msg: String) {
        log("VenderListDetails", msg)
    }

    private fun setProgress(flag: Boolean) {
        if (flag)
            showProgress()
        else hideProgress()
    }


}
