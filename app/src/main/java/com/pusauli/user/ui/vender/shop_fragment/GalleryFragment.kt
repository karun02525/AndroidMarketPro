package com.pusauli.user.ui.vender.shop_fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myhexaville.smartimagepicker.ImagePicker
import com.pusauli.user.R
import com.pusauli.user.model.ResultGalleryModel
import com.pusauli.user.mvvm.GalleryUploadViewModel
import com.pusauli.user.network.Const
import com.pusauli.user.network.NetworkUtil
import com.pusauli.user.network.RestClient
import com.pusauli.user.ui.common.ZoomImageActivity
import com.pusauli.user.ui.vender.VenderActivity
import com.pusauli.user.utils.SharedPref
import com.pusauli.user.utils.loadImage
import com.pusauli.user.utils.log
import com.pusauli.user.utils.showSnackBar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.adapter_gallery.view.*
import kotlinx.android.synthetic.main.dialog_remove_gallery_disgard_alert.view.*
import kotlinx.android.synthetic.main.fragment_gallery.view.*
import kotlinx.android.synthetic.main.toolbar.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class GalleryFragment : Fragment() {


    companion object {
        fun newInstance(): GalleryFragment {
            return GalleryFragment()
        }
    }


    private lateinit var mActivity: VenderActivity
    private var listStore: ArrayList<ResultGalleryModel> = arrayListOf()
    private val instanceViewModel by lazy { GalleryUploadViewModel() }
    private lateinit var mAdapter: GalleryListAdapter
    private val sp by lazy { SharedPref.instance }
    private val venderId = sp.venderId
    private val fullname = sp.userName
    private lateinit var rev_gallery: RecyclerView

    private var imagePicker: ImagePicker? = null
    private var pathImgBack: File? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = (activity!! as VenderActivity)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_gallery, container, false)
        initView(v)
        return v
    }

    @SuppressLint("SetTextI18n")
    private fun initView(v: View?) {
        v!!.tv_title.text = fullname!!.toUpperCase() + " (Store Gallery)"
        v.iv_back.visibility = View.GONE

        rev_gallery = v.rev_gallerys
        rev_gallery.layoutManager = GridLayoutManager(context, 2)

        v.btnCamera.setOnClickListener {
            showAll()
        }
        initObservers()
        initGalleryObservers()

        setProgress(true)
        instanceViewModel.getStoreGalleryApiCall(venderId!!)
    }

    /*Upload Picture*/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imagePicker!!.handleActivityResult(resultCode, requestCode, data)

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        imagePicker!!.handlePermission(requestCode, grantResults)
    }

    private fun showAll() {
        refreshImagePicker()
        imagePicker!!.choosePicture(true)
    }

    private fun refreshImagePicker() {
        imagePicker = ImagePicker(activity, this) {
            pathImgBack = imagePicker!!.imageFile
            setProgress(true)
            Handler().postDelayed({ apiUploadGallery() }, 100)
            //viewes.img_user_img.setImageURI(imageUri)
        }.setWithImageCrop(40, 40)

    }

    /*Upload Gallery*/
    @SuppressLint("CheckResult")
    private fun apiUploadGallery() {
        val mFile = RequestBody.create(MediaType.parse("multipart/form-data"), pathImgBack!!)
        val fileToUploadBackImg = MultipartBody.Part.createFormData("gallery_store", pathImgBack!!.name, mFile)
        val vender_id = RequestBody.create(MediaType.parse("text/plain"), venderId!!)

        RestClient.webServices().uploadGallery(fileToUploadBackImg,vender_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.status!!) {
                    setProgress(true)
                    instanceViewModel.getStoreGalleryApiCall(venderId)
                }
                setProgress(false)
            },
                { error ->
                    logs("Error: " + NetworkUtil.isHttpStatusCode(error))
                    setProgress(false)
                }
            )
    }

    private fun initObservers() {
        instanceViewModel.requestLoginData.observe(this, Observer {
            setProgress(false)
            uploadData(it)
        })
        instanceViewModel.errorMess.observe(this, Observer {
            setProgress(false)
        })
    }


    private fun uploadData(it: ArrayList<ResultGalleryModel>?) {
        listStore = it!!
        mAdapter = GalleryListAdapter(listStore, object : GalleryListAdapter.ItemClickListener {
            override fun onItemClicked(repos: ResultGalleryModel) {
                openBottomSheet(repos.avatar_id)
            }

        })
        rev_gallery.adapter = mAdapter
        mAdapter.notifyDataSetChanged()
    }


    class GalleryListAdapter(var list: ArrayList<ResultGalleryModel>, var listener: ItemClickListener) :
        RecyclerView.Adapter<GalleryListAdapter.ViewHolder>() {

        interface ItemClickListener {
            fun onItemClicked(repos: ResultGalleryModel)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_gallery, parent, false)
            return ViewHolder(v)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bindItems(list[position])
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            @SuppressLint("SetTextI18n")
            val context = itemView.context

            fun bindItems(model: ResultGalleryModel) {

                itemView.setOnClickListener {
                    context.startActivity(
                        Intent(context, ZoomImageActivity::class.java)
                            .putExtra("galleryAvatar",Const.STORE_AVATAR_BASE_URL+model.galleryAvatar)
                    )
                }
                itemView.setOnLongClickListener {
                    listener.onItemClicked(model)
                    true
                }

                itemView.iv_store.loadImage(Const.STORE_AVATAR_BASE_URL+model.galleryAvatar!!)
            }
        }
    }

    private fun logs(msg: String) {
        mActivity.log("StoreList", msg)
    }

    private fun setProgress(flag: Boolean) {
        if (flag)
            mActivity.showProgress()
        else mActivity.hideProgress()
    }


    private fun openBottomSheet(avatar_id: String?) {
        val view = layoutInflater.inflate(R.layout.dialog_remove_gallery_disgard_alert, null)
        val mBottomSheetDialog = Dialog(mActivity, R.style.MaterialDialogSheet)
        mBottomSheetDialog.setContentView(view)
        mBottomSheetDialog.setCancelable(false)
        mBottomSheetDialog.window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        mBottomSheetDialog.window!!.setGravity(Gravity.BOTTOM)
        mBottomSheetDialog.show()


        view.tv_btn_yes.setOnClickListener {
            setProgress(true)
            instanceViewModel.getApiRemoveGallery(avatar_id!!,venderId!!)
            mBottomSheetDialog.dismiss()
        }

        view.tv_btn_cancel.setOnClickListener {
            mBottomSheetDialog.dismiss()
        }
    }

    private fun initGalleryObservers() {
        instanceViewModel.successMess.observe(this, Observer {
            setProgress(false)
            mActivity.showSnackBar(it!!)
            instanceViewModel.getStoreGalleryApiCall(venderId!!)
        })
        instanceViewModel.errorMess.observe(this, Observer {
            logs("Error response $it")
            mActivity.showSnackBar(it!!)
            setProgress(false)
        })
    }
}
