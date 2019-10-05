package com.pusauli.user.mvvm

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.pusauli.user.model.ResponseVenderVerify
import com.pusauli.user.network.NetworkUtil.isHttpStatusCode
import com.pusauli.user.network.RestClient
import com.pusauli.user.utils.log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class AuthVenderViewModel : ViewModel() {

    val requestUpdateData = MutableLiveData<ResponseVenderVerify>()
    val errorMess = MutableLiveData<String>()




    @SuppressLint("CheckResult")
    fun submitVenderAPI(uid: String?, fullname: String, mobile: String?, category_id: String) {
        val map = HashMap<String, String>()
        map["uid"] = uid.toString()
        map["fullname"] = fullname
        map["mobile"] = mobile!!
        map["category_id"] = category_id

        RestClient.webServices().venderRegister(map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.status!!) {
                    requestUpdateData.value = it!!
                }

            },
                {
                    val mess = isHttpStatusCode(it)
                    log("vender register", "Error=>$mess")
                    errorMess.value = mess
                }
            )


    }
}