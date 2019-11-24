package com.pusauli.user.ui.vender.register_shop_details


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.pusauli.user.R
import com.pusauli.user.model.ResultStoreDetails
import com.pusauli.user.ui.vender.VenderActivity
import com.pusauli.user.utils.WorkaroundMapFragment
import com.pusauli.user.utils.log
import kotlinx.android.synthetic.main.fragment_view_map_vender.view.*


class MapViewFragmentVender : Fragment(), OnMapReadyCallback {

    private lateinit var mActivity: VenderActivity
    private lateinit var mMap: GoogleMap
    var result: ResultStoreDetails? = null
    var latLng: LatLng? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = (activity!! as VenderActivity)

        val bundle = this.arguments
        if (bundle != null) {
            result = bundle.getParcelable("data")
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_view_map_vender, container, false)
        initViewMap(v)
        return v
    }

    private fun initViewMap(v: View) {

            val mapFragment = childFragmentManager.findFragmentById(R.id.map_) as WorkaroundMapFragment
            mapFragment.getMapAsync(this)
            (childFragmentManager.findFragmentById(R.id.map_) as WorkaroundMapFragment)
                .setListener { v.sv_container!!.requestDisallowInterceptTouchEvent(true) }

        v.myCurrentLocation.setOnClickListener {
            getMyLocation()
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        latLng = try {
            LatLng(result!!.latitude!!.toDouble(),result!!.longitude!!.toDouble())
        } catch (e: Exception) {
            LatLng(0.00,0.00)
        }

        mMap = googleMap
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.8f))
        mMap.addMarker(MarkerOptions()
                .position(latLng!!).title(result!!.shopName?:"")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        )

    }

    private fun getMyLocation() {
        try {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.8f))
        } catch (e: Exception) {
        }
    }

    private fun logs(msg: String) {
        mActivity.log("DetailsMapFragment", msg)
    }

    private fun setProgress(flag: Boolean) {

    }


}
