package com.pusauli.user.ui.dashboard.fragment

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.pusauli.user.R
import com.pusauli.user.model.ResultNearby
import com.pusauli.user.mvvm.NearByViewModel
import com.pusauli.user.ui.dashboard.MainActivity
import com.pusauli.user.utils.SharedPref
import com.pusauli.user.utils.log
import com.pusauli.user.utils.showSnackBar

class StoreFragment  : Fragment(), OnMapReadyCallback {

    companion object {
        fun newInstance(): StoreFragment {
            return StoreFragment()
        }
    }

    private lateinit var mMap: GoogleMap
    private val instanceViewModel by lazy { NearByViewModel() }
    private var list: ArrayList<ResultNearby> = arrayListOf()
    private lateinit var mActivity: MainActivity
    private var latitude=25.099259
    private var longitude=83.719550
    private val sp by lazy { SharedPref.instance }
    private val uid = sp.userId

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity=(activity!! as MainActivity)
        initObservers()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v= inflater.inflate(R.layout.fragment_store, container, false)
        initView(v)
        return v
    }

    private fun initView(v: View?) {
        initGoogleMap()
        instanceViewModel.getNearByAPI(uid!!,latitude,longitude)
    }

    private fun initGoogleMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    private fun initObservers() {
        instanceViewModel.requestNearByData.observe(this, Observer {
            parseData(it)
        })
        instanceViewModel.errorMess.observe(this, Observer {
            mActivity.showSnackBar(it!!)
        })
    }

    private fun parseData(it: List<ResultNearby>?) {
       list=it as ArrayList<ResultNearby>
        log(list.toString())
        run()
    }



    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        this.mMap = googleMap;
        val style = MapStyleOptions.loadRawResourceStyle(mActivity, R.raw.map_style);
        this.mMap.setMapStyle(style);
//        mMap.isMyLocationEnabled = true




        mMap.setOnMapLoadedCallback {
            // mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 30))

        }


    }


    private fun run(){
        val bulder= LatLngBounds.Builder()
        for(l in list){
            val lanlng=LatLng(l.location[0],l.location[1])
            bulder.include(lanlng)
            val markarOption=MarkerOptions().position(lanlng).title(l.shopName)
                .snippet(l.category).snippet(l.distance)
            mMap.addMarker(markarOption)
        }

        val bound =bulder.build()
        val cameraUpdate=CameraUpdateFactory.newLatLngBounds(bound,100)
        mMap.moveCamera(cameraUpdate)

        val sydney = LatLng(latitude,longitude)
        mMap.addMarker(MarkerOptions().position(sydney).title("Current Location")
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12f))
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f), 100, null)

    }



    private fun log(msg: String) {
        mActivity.log("StoreFragment", msg)
    }
}
