package com.pusauli.user.mvvm

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.pusauli.user.model.DataCategory
import com.pusauli.user.model.StoreListResult
import com.pusauli.user.network.NetworkUtil.isHttpStatusCode
import com.pusauli.user.network.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class DashboardViewModel : ViewModel() {

    val requestStoreListData = MutableLiveData<List<StoreListResult>>()
    val errorMess = MutableLiveData<String>()

    @SuppressLint("CheckResult")
    fun getStoreListApi(category_id: String) {
        RestClient.webServices().getStoreList(category_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it!!.status!!) requestStoreListData.value = it.result

            }, {
                errorMess.value = isHttpStatusCode(it)
            })
    }


}