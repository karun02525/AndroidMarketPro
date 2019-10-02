package com.pusauli.user.ui.vender.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pusauli.user.R
import com.pusauli.user.model.GalleryDetails
import com.pusauli.user.model.ResultStoreDetails
import com.pusauli.user.mvvm.VenderViewListViewModel
import com.pusauli.user.network.Const
import com.pusauli.user.ui.vender.VenderActivity
import com.pusauli.user.ui.vender.fragment.fragement_details.GalleryViewFragmentVender
import com.pusauli.user.ui.vender.fragment.fragement_details.MapViewFragmentVender
import com.pusauli.user.ui.vender.fragment.fragement_details.ViewFragmentVender
import com.pusauli.user.utils.SharedPref
import com.pusauli.user.utils.loadImage
import com.pusauli.user.utils.loadImageProfile
import com.pusauli.user.utils.log
import kotlinx.android.synthetic.main.fragment_vender.view.*


class VenderFragment : Fragment() {


    companion object {
        fun newInstance(): VenderFragment {
            return VenderFragment()
        }
    }

    private val sp by lazy { SharedPref.instance }
    private lateinit var mActivity: VenderActivity
    lateinit var vi:View

    var title = arrayOf<CharSequence>("View", "Gallery", "Map")
    private val instanceViewModel by lazy { VenderViewListViewModel() }
    var result: ResultStoreDetails?=null
    var galleryList: ArrayList<GalleryDetails> = arrayListOf()
    var userId:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity=(activity!! as VenderActivity)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v= inflater.inflate(R.layout.fragment_vender, container, false)
        vi=v
        initObservers()
        initView()
        v.backBTN.setOnClickListener {
            mActivity.onBack()
        }
        return v
    }




    private fun initView() {
        setProgress(true)
        instanceViewModel.getStoreDetailsApiCall(sp.venderId.toString())
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
        vi.iv_vender_shopBack.loadImage(Const.STORE_AVATAR_BASE_URL+it!!.shopAvatar!!)
        vi.clg_logo_vender.loadImageProfile(Const.PROFILE_AVATAR_BASE_URL+it.userAvatar!!)


        sp.shopAvatar= it.shopAvatar!!
        sp.profileAvatar= it.userAvatar
        sp.shopName=it.shopName!!
        sp.mobileOtherNumber=it.shopMobile!!
        sp.emailId=it.shopEmail!!
        sp.shopAddress=it.shopAddress!!
        sp.shopNearBy=it.shopNearBy!!
        sp.pinCode=it.shopZip!!

        sp.owerName=it.owerName!!
        sp.ownerEmailId=it.owerEmail!!
        sp.ownerMobileNumber=it.owerMobile!!



        result=it
        galleryList= (it.galleryList as ArrayList<GalleryDetails>?)!!

        try {
            setUpTabs()
        } catch (e: Exception) {
        }
    }


    private fun setUpTabs() {
        vi.pager!!.adapter = MainPagerAdapterVender(mActivity.supportFragmentManager, title, title.size)
        vi.tabs!!.setupWithViewPager(vi.pager)
    }

    inner class MainPagerAdapterVender(fm: FragmentManager,
                                 private var titles: Array<CharSequence>,
                                 var numbOfTabs: Int
    ) : FragmentStatePagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> {
                    val f= ViewFragmentVender()
                    val bundle = Bundle()
                    bundle.putParcelable("data",result)
                    f.arguments = bundle
                    return f
                }
                1 -> {
                    val f= GalleryViewFragmentVender()
                    val bundle = Bundle()
                    bundle.putParcelableArrayList("gallery",galleryList)
                    f.arguments = bundle
                    return f
                }
                else -> {
                    val f= MapViewFragmentVender()
                    val bundle = Bundle()
                    bundle.putParcelable("data",result)
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

    private fun setProgress(flag: Boolean) {
        if (flag)
            mActivity.showProgress()
        else mActivity.hideProgress()
    }
}
