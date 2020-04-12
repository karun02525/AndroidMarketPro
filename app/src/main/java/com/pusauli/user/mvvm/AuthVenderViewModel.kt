package com.pusauli.user.mvvm

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
    fun submitVenderAPI(category_id: String,category_name: String) {
        RestClient.webServices().venderRegister(category_id,category_name)
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