package com.pusauli.user.mvvm

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pusauli.user.model.ResultNearby
import com.pusauli.user.network.NetworkUtil.isHttpStatusCode
import com.pusauli.user.network.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class NearByViewModel : ViewModel() {

    val requestNearByData = MutableLiveData<List<ResultNearby>>()
    val errorMess = MutableLiveData<String>()


    @SuppressLint("CheckResult")
    fun getNearByAPI(uid:String,latitude: Double,longitude: Double) {
        RestClient.webServices().getNearBy(uid,latitude,longitude)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.status!!) requestNearByData.value = it.result!!

            }, { error ->
                errorMess.value = isHttpStatusCode(error)
            })
    }
}