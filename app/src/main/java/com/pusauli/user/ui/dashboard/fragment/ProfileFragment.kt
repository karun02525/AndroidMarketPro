package com.pusauli.user.ui.dashboard.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.SpinnerAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.myhexaville.smartimagepicker.ImagePicker
import com.pusauli.user.R
import com.pusauli.user.language_change.LanguageActivity
import com.pusauli.user.model.CategoryModel
import com.pusauli.user.model.DataCategory
import com.pusauli.user.model.ResponseVenderVerify
import com.pusauli.user.model.ResultUpdateProfile
import com.pusauli.user.mvvm.AuthVenderViewModel
import com.pusauli.user.mvvm.AuthViewModel
import com.pusauli.user.mvvm.CategoryViewModel
import com.pusauli.user.network.Const
import com.pusauli.user.network.NetworkUtil
import com.pusauli.user.network.RestClient
import com.pusauli.user.ui.authentication.ChangePasswordActivity
import com.pusauli.user.ui.authentication.LoginActivity
import com.pusauli.user.ui.category.CategorySpinnerAdapter
import com.pusauli.user.ui.dashboard.MainActivity
import com.pusauli.user.ui.vender.VenderActivity
import com.pusauli.user.utils.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.alert_ok.*
import kotlinx.android.synthetic.main.dialog_vender_verification.view.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import java.io.File


class ProfileFragment : Fragment(), AdapterView.OnItemSelectedListener {

    companion object {
        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }

    private lateinit var mActivity: MainActivity
    private val sp by lazy { SharedPref.instance }
    private val instanceViewModel by lazy { AuthViewModel() }
    private val instanceVenderViewModel by lazy { AuthVenderViewModel() }

    private val instanceCategoryViewModel by lazy { CategoryViewModel() }
    private var listSpinner: ArrayList<DataCategory> = arrayListOf()

    private lateinit var viewBind: View
    private lateinit var adpSpinner: SpinnerAdapter
    private var category_id = ""
    private val uid = sp.userId

    @SuppressLint("DefaultLocale")
    private val name = sp.userName!!.toUpperCase()
    private val mobile = sp.mobileNumber

    private var imagePicker: ImagePicker? = null
    private var pathImgBack: File? = null
    private lateinit var spinnerCategory: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = (activity!! as MainActivity)
        initObservers()
        initObserverSubmitVender()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_profile, container, false)
        viewBind=v
        apiCategoryCall()
        initView()
        return v
    }



    private fun initView() {
        viewBind.apply {
            this.txt_user_name.text = name
            this.txt_gender.text = sp.gender
            this.txt_phone.text = mobile
            this.txt_city.text = sp.city
            this.logout.setOnClickListener {
                sp.logOut()
                mActivity.startNewActivityFlag(LoginActivity::class.java)
            }

            this.btnChange.setOnClickListener {
                mActivity.startNewActivityNoFinish(ChangePasswordActivity::class.java)
            }

            this.ll_vender.setOnClickListener {
                setProgress(true)
                venderVerifyAPI(uid!!)
            }

            this.ll_image_main.setOnClickListener {
                showAll()
            }
            this.ll_chnage_lang.setOnClickListener {
                startActivity(Intent(mActivity, LanguageActivity::class.java))
            }
        }




        setProfilePic()

    }


    private fun openBottomSheet() {
        val view = layoutInflater.inflate(R.layout.dialog_vender_verification, null)
        spinnerCategory=view!!.sp_category

        val mBottomSheetDialog = Dialog(mActivity, R.style.MaterialDialogSheet)
        mBottomSheetDialog.setContentView(view)
        mBottomSheetDialog.setCancelable(true)
        mBottomSheetDialog.window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        mBottomSheetDialog.window!!.setGravity(Gravity.BOTTOM)
        mBottomSheetDialog.show()

        view.tv_name.text = name
        view.tv_mob.text = mobile
        view.btnSubmit.setOnClickListener {
            setProgress(true)
            instanceVenderViewModel.submitVenderAPI(uid, name, mobile, category_id)
            mBottomSheetDialog.dismiss()
        }
        initCategoryObservers()
    }


    private fun apiCategoryCall() {
        instanceCategoryViewModel.getCategoryApi()
    }


    private fun initCategoryObservers() {
        instanceCategoryViewModel.requestCategoryData.observe(this, Observer {
            successData(it)
        })
        instanceCategoryViewModel.errorMess.observe(this, Observer {
            mActivity.showSnackBar(it!!)
        })
    }

    private fun successData(it: CategoryModel) {
        listSpinner= it.data
       // listSpinner[0].categoryName="--Select Category--"
        adpSpinner = CategorySpinnerAdapter(mActivity, listSpinner)
        spinnerCategory.adapter = adpSpinner
        spinnerCategory.onItemSelectedListener = this
    }


    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        category_id = listSpinner[position].categoryId!!
    }


    /*Upload Picture*/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imagePicker!!.handleActivityResult(resultCode, requestCode, data)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        imagePicker!!.handlePermission(requestCode, grantResults)
    }

    private fun showAll() {
        refreshImagePicker()
        imagePicker!!.choosePicture(true)
    }

    private fun refreshImagePicker() {
        imagePicker = ImagePicker(activity, this) { imageUri ->
            pathImgBack = imagePicker!!.imageFile
            setProgress(true)
            Handler().postDelayed({ uploadProfile() }, 100)
            viewBind.img_user_img.setImageURI(imageUri)
        }.setWithImageCrop(40, 40)

    }


    private fun setProfilePic() {
        viewBind.img_user_img.loadImageProfile(Const.PROFILE_AVATAR_BASE_URL+sp.profileAvatar!!)
    }

    @SuppressLint("CheckResult")
    private fun uploadProfile() {
        setProgress(true)
        instanceViewModel.uploadProfileAPI(pathImgBack, uid!!)
    }

    private fun initObservers() {
        instanceViewModel.requestDataUpdateProfile.observe(this, Observer {
            updateProfile(it)
            setProgress(false)
        })
        instanceViewModel.errorMess.observe(this, Observer {
            setProgress(false)
            mActivity.showSnackBar(it!!)
        })
    }

    private fun updateProfile(it: ResultUpdateProfile?) {
        sp.profileAvatar =it!!.userAvatar
        setProfilePic()
    }


    @SuppressLint("CheckResult")
    fun venderVerifyAPI(uid: String?) {
        RestClient.webServices().venderVerify(uid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                setProgress(false)
                if (it.status!!) {
                    successfullyData(it)
                }
            },
                {
                    val mess = NetworkUtil.isHttpStatusCode(it)
                    log("vender verify", "Error=>$mess")
                    mActivity.showSnackBar(mess)
                }
            )
    }

    private fun initObserverSubmitVender() {
        instanceVenderViewModel.requestUpdateData.observe(this, Observer {
            successfullyData(it)
            setProgress(false)
        })
        instanceVenderViewModel.errorMess.observe(this, Observer {
            setProgress(false)
            mActivity.showSnackBar(it!!)
        })
    }


    private fun successfullyData(it: ResponseVenderVerify?) {
        val isVerify = it!!.result!!.isVerify
        val venderId = it.result!!.venderId
        val category_id = it.result!!.category_id
        val category_name = it.result!!.category_name


        when (isVerify) {
            0 -> //0 means create first time
                openBottomSheet()
            1 -> //1 means verify pending
                showDialog(venderId,isVerify)
            2 -> { //2 means verifyed
                sp.venderId = venderId!!
                sp.category_id = category_id!!
                sp.category_name = category_name!!
                mActivity.startActivity(
                    Intent(mActivity, VenderActivity::class.java)
                        .putExtra("actionFragment", 2)
                )
            }
            3 -> { //3 means reject
                showDialog(venderId,isVerify)
            }
        }
    }

    @SuppressLint("InflateParams", "SetTextI18n")
    private fun showDialog(venderId: String?,isVerify:Int) {
        val inflater = this.layoutInflater
        val layout = inflater.inflate(R.layout.alert_ok, null)
        val alertDio = AlertDialog.Builder(mActivity).setView(layout).show()

        alertDio.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDio.setCancelable(false)
        alertDio.setContentView(R.layout.alert_ok)

        if(isVerify==1){
            alertDio.txt_booking_confirmation.text = "Your verification Pending"
            alertDio.txt_booking_confirmation.setTextColor(Color.GREEN)
        }
        if(isVerify==3){
            alertDio.txt_booking_confirmation.text = "Your shop has been rejected"
            alertDio.txt_booking_confirmation.setTextColor(Color.RED)
        }

        alertDio.tv_vender_id.text = "#$venderId"
        val ok = alertDio.tv_ok
        ok.setOnClickListener {
            alertDio.dismiss()
        }
    }


    private fun setProgress(flag: Boolean) {
        if (flag)
            mActivity.showProgress()
        else mActivity.hideProgress()
    }

    private fun log(msg: String) {
        mActivity.log("ProfileFragment", msg)
    }
}
