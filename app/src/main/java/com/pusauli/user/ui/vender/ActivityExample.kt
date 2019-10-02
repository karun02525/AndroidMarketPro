package com.pusauli.user.ui.vender

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.myhexaville.smartimagepicker.ImagePicker
import com.pusauli.user.R
import kotlinx.android.synthetic.main.test.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody


class ActivityExample : AppCompatActivity() {
    private var imagePicker: ImagePicker? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test)

        btn.setOnClickListener {
            showAll()
        }




    }

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

    private fun chooseFromGallery() {
        refreshImagePicker()
        imagePicker!!.choosePicture(false)
    }

    private fun openCamera() {
        refreshImagePicker()
        imagePicker!!.openCamera()
    }

    private fun refreshImagePicker() {
        imagePicker = ImagePicker(
            this, null)
        { imageUri ->
            Log.d(TAG, "refreshImagePicker: $imageUri")
            Log.d(TAG, "File Name: " + imagePicker!!.imageFile.name)
            Log.d(TAG, "File path: " + imagePicker!!.imageFile)
            iv.setImageURI(imageUri)
        }.setWithImageCrop(1,1)


    }

    companion object {
        private val TAG = "ActivityExample"
    }



    @SuppressLint("CheckResult")
    fun save(v: View){
        val file = imagePicker!!.imageFile
        Log.d(TAG, "refreshImagePicker: $file")
     //   val log = File(pathLogo.toString())
        val mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val fileToUploadLogo = MultipartBody.Part.createFormData("file_owner_pic", file.name, mFile)

      /*  RestClient.webServices().postDatas(fileToUploadLogo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.status!!) {
                    toast(it.message!!)
                }
            },
                { error ->
                    Log.d("TAHS", "Error: " + NetworkUtil.isHttpStatusCode(error))
                }
            )*/

    }
}