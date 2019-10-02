package com.pusauli.user.ui.vender.fragment.fragement_details


import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pusauli.user.R
import com.pusauli.user.model.ResultStoreDetails
import com.pusauli.user.ui.vender.VenderActivity
import com.pusauli.user.utils.log
import kotlinx.android.synthetic.main.fragment_view_vender.view.*


class ViewFragmentVender : Fragment() {

    private lateinit var mActivity: VenderActivity
    var result: ResultStoreDetails?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity=(activity!! as VenderActivity)

        val bundle = this.arguments
        if (bundle != null) {
            result = bundle.getParcelable("data")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v= inflater.inflate(R.layout.fragment_view_vender, container, false)
        uploadData(v,result)
        return v
    }


    @SuppressLint("SetTextI18n")
    private fun uploadData(v:View?, it: ResultStoreDetails?) {
        with(v!!) {
            tv_shop_id.text="#${it!!.venderId}"
            tv_shop_name.text=it.shopName
            tv_shop_no.text=it.shopMobile
            tv_shop_second_no.text=it.shopMobile
            tv_shop_email.text=it.shopEmail
            tv_address.text=it.shopAddress
            tv_near_by.text=it.shopNearBy
            tv_pincode.text=it.shopZip
            tv_name.text=it.owerName
            tv_owner_mobile.text=it.owerMobile
            tv_owner_email.text=it.owerEmail
        }
    }

    private fun logs(msg: String) {
        mActivity.log("VenderList", msg)
    }

    private fun setProgress(flag: Boolean) {

    }



}
