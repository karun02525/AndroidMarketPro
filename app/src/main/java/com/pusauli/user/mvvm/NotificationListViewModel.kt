package com.pusauli.user.mvvm

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pusauli.user.model.NotificationData
import com.pusauli.user.network.NetworkUtil.isHttpStatusCode
import com.pusauli.user.network.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class NotificationListViewModel : ViewModel() {

    val requestStoreDetailsData = MutableLiveData<List<NotificationData>>()
    val errorMess = MutableLiveData<String>()

    @SuppressLint("CheckResult")
    fun getNotificationApiCall(uid: String) {
        RestClient.webServices().getNotifications(uid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.status) requestStoreDetailsData.value = it.data

            }, {
                errorMess.value = isHttpStatusCode(it)
            })
    }


}