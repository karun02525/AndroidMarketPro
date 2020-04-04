package com.pusauli.user.mvvm

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.pusauli.user.model.CategoryModel
import com.pusauli.user.network.NetworkUtil.isHttpStatusCode
import com.pusauli.user.network.RestClient
import com.pusauli.user.utils.SharedPref
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class CategoryViewModel : ViewModel() {

    val requestCategoryData = MutableLiveData<CategoryModel>()
    val errorMess = MutableLiveData<String>()
    private val spCache by lazy { SharedPref.instance }

    @SuppressLint("CheckResult")
    fun getCategoryApi() {
        RestClient.webServices().getCategory()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it!!.status!!) {
                    requestCategoryData.value = it
                    saveCacheData(it)
                }

            }, {
                getCacheData()
                errorMess.value = isHttpStatusCode(it)
            })


    }

    private fun saveCacheData(data: CategoryModel) {
        val json = Gson().toJson(data)
        spCache.dashboardCategory = json
    }

    private fun getCacheData() {
        val getCache = spCache.dashboardCategory
        if (getCache != null && getCache != "" ) {
            val obj = Gson().fromJson(getCache, CategoryModel::class.java)
            requestCategoryData.value = obj
        }
    }


}