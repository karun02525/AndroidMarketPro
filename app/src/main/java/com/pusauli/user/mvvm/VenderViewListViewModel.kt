package com.pusauli.user.mvvm

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pusauli.user.model.ResultStoreDetails
import com.pusauli.user.model.ResultVenderModel
import com.pusauli.user.network.NetworkUtil.isHttpStatusCode
import com.pusauli.user.network.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class VenderViewListViewModel : ViewModel() {

    val requestVenderData = MutableLiveData<ResultVenderModel>()
    val requestStoreDetailsData = MutableLiveData<ResultStoreDetails>()
    val errorMess = MutableLiveData<String>()


    @SuppressLint("CheckResult")
    fun getVenderListApiCall(user_id: Int) {
        RestClient.webServices().getVenderList(user_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.status!!) requestVenderData.value = it.result!!
            }, { error ->
                errorMess.value = isHttpStatusCode(error)
            })
    }


    @SuppressLint("CheckResult")
    fun getStoreDetailsApiCall(vender_id: String) {
        RestClient.webServices().getStoreDetails(vender_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.status!!) requestStoreDetailsData.value = it.result

            }, { error ->
                errorMess.value = isHttpStatusCode(error)
            })
    }


}