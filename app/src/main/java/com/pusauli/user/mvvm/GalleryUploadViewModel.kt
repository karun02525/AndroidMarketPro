package com.pusauli.user.mvvm

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.pusauli.user.model.ResultGalleryModel
import com.pusauli.user.network.NetworkUtil.isHttpStatusCode
import com.pusauli.user.network.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class GalleryUploadViewModel : ViewModel() {

    val requestLoginData = MutableLiveData<ArrayList<ResultGalleryModel>>()
    val errorMess = MutableLiveData<String>()
    val successMess = MutableLiveData<String>()


    @SuppressLint("CheckResult")
    fun getStoreGalleryApiCall(venderId: String?) {
        RestClient.webServices().getStoreGallery(venderId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.status!!) {
                    requestLoginData.value = it.result!!
                }

            }, { error ->
                error.printStackTrace()
                errorMess.value = isHttpStatusCode(error)
            })
    }


    @SuppressLint("CheckResult")
    fun getApiRemoveGallery(avatar_id: String,vender_id:String) {
        RestClient.webServices().getRemoveGallery(avatar_id,vender_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.status!!) {
                    successMess.value=it.message
                }

            }, { error ->
                errorMess.value = isHttpStatusCode(error)
            })
    }


}