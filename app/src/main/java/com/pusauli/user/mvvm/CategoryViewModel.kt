package com.pusauli.user.mvvm

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pusauli.user.model.DataCategory
import com.pusauli.user.network.NetworkUtil.isHttpStatusCode
import com.pusauli.user.network.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class CategoryViewModel : ViewModel() {

    val requestCategoryData = MutableLiveData<List<DataCategory>>()
    val errorMess = MutableLiveData<String>()

    @SuppressLint("CheckResult")
    fun getCategoryApi() {
        RestClient.webServices().getCategory()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it!!.status!!) requestCategoryData.value = it.data

            }, {
                errorMess.value = isHttpStatusCode(it)
            })
    }


}