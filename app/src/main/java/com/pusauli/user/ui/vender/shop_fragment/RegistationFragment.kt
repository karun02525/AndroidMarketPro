package com.pusauli.user.ui.vender.shop_fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import com.google.android.gms.maps.model.LatLng
import com.myhexaville.smartimagepicker.ImagePicker
import com.pusauli.user.R
import com.pusauli.user.network.Const
import com.pusauli.user.network.NetworkUtil
import com.pusauli.user.network.RestClient
import com.pusauli.user.ui.common.BaseFragmentVender
import com.pusauli.user.ui.vender.VenderActivity
import com.pusauli.user.utils.SharedPref
import com.pusauli.user.utils.loadImage
import com.pusauli.user.utils.log
import com.pusauli.user.utils.showSnackBar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.alert_dialog.*
import kotlinx.android.synthetic.main.fragment_registation.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class RegistationFragment : BaseFragmentVender() {


    companion object {
        fun newInstance(): RegistationFragment {
            return RegistationFragment()
        }

        private val PLACE_PICKER_REQUEST = 3224
    }


    private lateinit var imgBack: ImageView
    private var imagePicker: ImagePicker? = null
    private lateinit var mActivity: VenderActivity
    private lateinit var btn_view_color: Button

    private var pathImgBack: File? = null
    private var mSelectedColor: Int = 0
    private var shopLatLng: LatLng? = null
    private val sp by lazy { SharedPref.instance }
    private val uid = sp.userId
    private val mobile = sp.mobileNumber
    private val venderId = sp.venderId
    private val category_id = sp.category_id
    private val category_name = sp.category_name

    private var viewes: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = (activity!! as VenderActivity)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_registation, container, false)
        initView(v)
        return v
    }

    @SuppressLint("SetTextI18n")
    private fun initView(v: View?) {
        imgBack = v!!.imgBack
        viewes = v
        val avt = sp.shopAvatar!!
        if (avt != "") {
            imgBack.loadImage(Const.STORE_AVATAR_BASE_URL+sp.shopAvatar!!)
            v.vender_shop_reg.text = "#$venderId"
            v.vender_shop_category.text = category_name
            v.edit_shopMobile.setText(mobile)
            v.edit_shopName.setText(sp.shopName)
            v.edit_shopEmail.setText(sp.emailId)
            v.edit_shopMobile.setText(mobile)
            v.edit_shopAddress.setText(sp.shopAddress)
            v.edit_shopNearst.setText(sp.shopNearBy)
            v.edit_pincode.setText(sp.pinCode)
            v.edit_ownerEmail.setText(sp.ownerEmailId)
            v.edit_ownerName.setText(sp.owerName)
            v.edit_ownerContact.setText(sp.ownerMobileNumber)

        } else {
            v.vender_shop_reg.text = "#$venderId"
            v.vender_shop_category.text = category_name
            v.edit_shopMobile.setText(mobile)
            v.edit_ownerName.setText(sp.userName)
            v.edit_ownerContact.setText(mobile)
        }

        btn_view_color = v.btn_view_color
        val btn_location = v.btn_location
        initViewField(v)


        v.back_img_btn.setOnClickListener {
            showAll()
        }


        btn_view_color.text = "🎨 ${mActivity.getString(R.string.choose_your_shop_color)}"
        btn_location.text = "🏡 ${mActivity.getString(R.string.add_your_shop_location)}"

        btn_view_color.setOnClickListener {
            colorPikar()
        }

        btn_location.setOnClickListener {
            // locationPlacesIntent()
        }
    }

    private fun initViewField(v: View?) {


        v!!.apply {
            btn_submit.setOnClickListener {
                val shopName = edit_shopName.text.toString()
                val shopEmail = edit_shopEmail.text.toString()
                val shopMobile = edit_shopMobile.text.toString()
                val shopAddress = edit_shopAddress.text.toString()
                val shopNearBy = edit_shopNearst.text.toString()
                val shopPinCode = edit_pincode.text.toString()
                val owerName = edit_ownerName.text.toString()
                val owerMobile = edit_ownerContact.text.toString()
                val owerEmail = edit_ownerEmail.text.toString()

                onSubmit(
                    shopName,
                    shopEmail,
                    shopMobile,
                    shopAddress,
                    shopNearBy,
                    shopPinCode,
                    owerName,
                    owerMobile,
                    owerEmail
                )
            }
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            imagePicker!!.handleActivityResult(resultCode, requestCode, data)
        } catch (e: Exception) {
        }

        /* if (requestCode == PLACE_PICKER_REQUEST) {
             if (resultCode == Activity.RESULT_OK) {
                 val place = PlacePicker.getPlace(mActivity, data)
                 if (place != null) {
                     val latLng = place.latLng
                     val latlong = latLng.latitude.toString() + " : " + latLng.longitude.toString()
                     val address = String.format("%s", place.address)
                     try {
                         viewes!!.edit_shopAddress.setText(address)
                     } catch (e: Exception) {
                     }
                     shopLatLng = latLng
                     log("LatLog:  $latlong   ")
                 } else {
                     //PLACE IS NULL
                 }
             }
         }*/
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        imagePicker!!.handlePermission(requestCode, grantResults)
    }


    fun showAll() {
        refreshImagePicker()
        imagePicker!!.choosePicture(true)
    }


    private fun refreshImagePicker() {
        imagePicker = ImagePicker(activity, this) { imageUri ->
            pathImgBack = imagePicker!!.imageFile
            imgBack.setImageURI(imageUri)
        }.setWithImageCrop(12, 8)
    }


    /*fun chooseFromGallery() {
      refreshImagePicker()
      imagePicker!!.choosePicture(false)
  }

  fun openCamera() {
      refreshImagePicker()
      imagePicker!!.openCamera()
  }*/


    //Pikar Location


    //Pikar Color
    private fun colorPikar() {
        ColorPickerDialogBuilder
            .with(mActivity)
            .setTitle("🎨${getString(R.string.choose_your_shop_color)}")
            .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
            .density(12)
            .setPositiveButton("ok") { _, color, _ ->
                mSelectedColor = color
                Log.d("TAHS", "color :  $color")
                btn_view_color.setBackgroundColor(mSelectedColor);
            }
            .setNegativeButton("cancel") { _, _ -> }
            .build()
            .show()
    }


    @SuppressLint("CheckResult")
    private fun onSubmit(
        shopName: String,
        shopEmail: String,
        shopMobile: String,
        shopAddress: String,
        shopNearBy: String,
        shopPinCode: String,
        owerName: String,
        owerMobile: String,
        owerEmail: String
    ) {
        setProgress(true)
        var fileToUploadBackImg: MultipartBody.Part? = null;
        if (pathImgBack != null) {
            val mFile =
                RequestBody.create(MediaType.parse("application/octet-stream"), pathImgBack!!)
            fileToUploadBackImg =
                MultipartBody.Part.createFormData("store_avatar", pathImgBack!!.name, mFile)
        }

        val map =HashMap<String,Any>()
        map["vender_id"] = venderId!!
        map["uid"] = uid!!
        map["category_id"] = category_id!!
        map["shop_name"] = shopName
        map["shop_mobile"] = shopMobile
        map["shop_email"] = shopEmail
        map["shop_address"] = shopAddress
        map["shop_nearby"] = shopNearBy
        map["shop_pincode"] = shopPinCode
        map["shop_color"] = "" + mSelectedColor
        map["shop_ownername"] = owerName
        map["shop_owner_mobile"] = owerMobile
        map["shop_owner_email"] = owerEmail
        val latitude=12.39949934
        val longitude=24.46469934
        map["location"] = listOf(latitude,longitude)



       // val jsonPart = RequestBody.create(MediaType.parse("application/json"), json.toString())

        RestClient.webServices().postData(fileToUploadBackImg, map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.status!!) {
                    showDialog(it.message!!)
                }
                setProgress(false)
            },
                {
                    setProgress(false)
                    val mess = NetworkUtil.isHttpStatusCode(it)
                    log("Login", "Error=>$mess")
                    mActivity.showSnackBar(mess)
                }
            )
    }


    @SuppressLint("SetTextI18n")
    private fun showDialog(m: String) {
        val inflater = this.layoutInflater
        val layout = inflater.inflate(R.layout.alert_dialog, null)
        val alertDio = AlertDialog.Builder(mActivity).setView(layout).show()

        alertDio.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDio.setCancelable(false)
        alertDio.setContentView(R.layout.alert_dialog)
        alertDio.tv_messs.text = m
        val ok = alertDio.tv_ok
        ok.setOnClickListener {
            mActivity.galleryFragment()
            // mActivity.startNewActivityFlag(MainActivity::class.java)
            alertDio.dismiss()
        }
    }


    private fun setProgress(flag: Boolean) {
        if (flag)
            mActivity.showProgress()
        else mActivity.hideProgress()
    }

    private fun log(msg: String) {
        mActivity.log("Vender Register Store Fragment", msg)
    }
}
