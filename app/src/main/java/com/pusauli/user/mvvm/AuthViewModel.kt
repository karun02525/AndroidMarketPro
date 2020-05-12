package com.pusauli.user.mvvm

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pusauli.user.BuildConfig
import com.pusauli.user.model.*
import com.pusauli.user.network.ApiStatus.isCheckAPIStatus
import com.pusauli.user.network.NetworkUtil
import com.pusauli.user.network.RestClient
import com.pusauli.user.utils.deviceInfo
import com.pusauli.user.utils.getDeviceToken
import com.pusauli.user.utils.log
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.io.File


class AuthViewModel : ViewModel() {

    var requestData = MutableLiveData<ResultDetails>()
    var requestDataUpdateProfile = MutableLiveData<ResultUpdateProfile>()
    var requestDataOTP = MutableLiveData<ResultOTP>()
    var requestDataOTPVerify = MutableLiveData<OTPModel>()
    private lateinit var job1: Job



    val errorMess = MutableLiveData<String>()
    val successMessage = MutableLiveData<String>()


    val deviceToken = getDeviceToken()!!
    val verName = BuildConfig.VERSION_NAME
    val verCode = BuildConfig.VERSION_CODE
    val deviceName = com.pusauli.user.utils.deviceName


    @SuppressLint("CheckResult")
    fun onLoginSubmit(mob: String, pass: String) {
        val apiInterface = RestClient.webServices()
        val map = HashMap<String, String>()
        map["mobile"] = mob
        map["password"] = pass

        val handler = CoroutineExceptionHandler { _, exception ->
            val mess = NetworkUtil.isHttpStatusCode(exception)
            errorMess.postValue(mess)
            log("LoginResponseSuccess", "Error 2=>$mess")
            log("LoginResponseSuccess", "Error 2=> "+ Thread.currentThread().name)
        }

        job1 = viewModelScope.launch(handler + Dispatchers.IO) {
                val resLogin= apiInterface.login(map)
                val(a,b) = coroutineScope {
                    log("LoginResponseSuccess", "Error 3=> "+ Thread.currentThread().name)
                    val resDevice= async {
                        apiInterface.registerDevice("Bearer "+resLogin.result?.token!!, deviceInfo())}
                    val resCategory= async {apiInterface.getCategorys("Bearer "+resLogin.result?.token!!)}
                    resDevice.await() to resCategory.await()
                }
                 requestData.postValue(resLogin.result)

                log("LoginResponseSuccess", "Error 4=> "+ Thread.currentThread().name)
                log("LoginResponseSuccess 1-> ",""+ resLogin)
                log("LoginResponseSuccess 2-> ",""+ a)
                log("LoginResponseSuccess 3-> ",""+ b)
        }
        log("LoginResponseSuccess job1-> ",""+ job1)
    }



    override fun onCleared() {
        job1.cancel()
        log("LoginResponseSuccess -> ","onCleared 1")
        super.onCleared()
        log("LoginResponseSuccess -> ","onCleared 2")

    }

/*

    */
/*Login*//*

    @SuppressLint("CheckResult")
    fun onLoginSubmit(mob: String, pass: String) {
        val apiInterface = RestClient.webServices()
        val map = HashMap<String, String>()
        map["mobile"] = mob
        map["password"] = pass
        apiInterface.login(map)
            .flatMap{
                 apiInterface.registerDevice("Bearer "+it.result?.token!!, deviceInfo())
                .map { obj->Pair(it,obj) }
            }
           */
/* .flatMap{
                apiInterface.getCategory("Bearer "+it.first.result?.token!!).map { obj->Triple(it.first,it.second,obj) }
            }*//*

            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
               log("Login Success 1-> ",it.first.toString())
               log("Login Success 2-> ",it.second.toString())
              // log("Login Success 3-> ",it.third.toString())
               if (it.first.status!!) {
                   requestData.value = it.first.result
                }
            },
                {
                    val mess = NetworkUtil.isHttpStatusCode(it)
                    log("Login", "Error=>$mess")
                    errorMess.value = mess
                }
            )
    }
*/

    /*
    //Home API
    @SuppressLint("CheckResult")
    fun onGetHome(uid: String) {
        val map = HashMap<String, String>()
        map["mobile"] = uid
        RestClient.webServices().login(map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.status!!) {
                    requestData.value = it.result
                }
            },
                {
                    val mess = NetworkUtil.isHttpStatusCode(it)
                    log("Login", "Error=>$mess")
                    errorMess.value = mess
                }
            )
    }
*/
    /*Send OTP*/
    @SuppressLint("CheckResult")
    fun onSendOtpAPI(mobileNumber: String) {
        val map = HashMap<String, String>()
        map["mobile"] = mobileNumber
        RestClient.webServices().onSendOtp(map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.status!!) {
                    requestDataOTP.value = it.data
                }
            },
                {
                    val mess = NetworkUtil.isHttpStatusCode(it)
                    log("Login", "Error=>$mess")
                    errorMess.value = mess
                }
            )
    }


    /*verify OTP*/
    @SuppressLint("CheckResult")
    fun onVerifyOtpAPI(mobileNumber: String,otp: String) {
        val map = HashMap<String, String>()
        map["mobile"] = mobileNumber
        map["otp"] = otp
        RestClient.webServices().onSendVerityOtp(map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.status!!) {
                    requestDataOTPVerify.value = it
                }
            },
                {
                    val mess = NetworkUtil.isHttpStatusCode(it)
                    log("Login", "Error=>$mess")
                    errorMess.value = mess
                }
            )
    }

    /*Create Account*/
    @SuppressLint("CheckResult")
    fun onSignUpSubmit(mobile: String,name: String, email: String, pass: String, gender: String) {
        val map = HashMap<String, String>()
        map["mobile"] = mobile
        map["name"] = name
        map["email"] = email
        map["password"] = pass
        map["gender"] = gender
        RestClient.webServices().signUp(map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.status!!) {
                    val data=it.result
                    requestData.value = data
                    try {
                       // onRegisterDevices(data!!.userId!!,data.firstName!!,data.mobile!!)
                    } catch (e: Exception) {
                    }
                }
            },
                {
                    val mess = NetworkUtil.isHttpStatusCode(it)
                    log("SignUp", "Error=>$mess")
                    errorMess.value = mess
                }
            )
    }

    /*RegisterDevices*/
    @SuppressLint("CheckResult")
    fun onRegisterDevices(user_id: Int, fname: String, mob:String) {
        log("onRegisterDevices", "Data=>$mob ,$user_id ,$deviceToken ,$verName ,$verCode ,$deviceName ")
        val map = HashMap<String, String>()
        map["user_id"] = user_id.toString()
        map["first_name"] = fname
        map["mobile"] = mob
        map["ver_name"] = verName
        map["ver_code"] = verCode.toString()
        map["device_name"] = deviceName
        map["device_token"] = deviceToken
        RestClient.webServices().registerDevices(map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.status!!) {
                    log("onRegisterDevices", "Error=>${it.message}")
                }
            },
                {
                    val mess = NetworkUtil.isHttpStatusCode(it)
                    log("onRegisterDevices", "Error=>$mess")
                }
            )
    }

    /*Forgot password*/
    @SuppressLint("CheckResult")
    fun forgotPassword(mobile: String, last_name: String, password: String) {
        val map = HashMap<String, String>()
        map["mobile"] = mobile
        map["last_name"] = last_name
        map["password"] = password
        RestClient.webServices().forgotPassword(map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.status!!) {
                    successMessage.value = it.message
                }
            },
                {
                    val mess = NetworkUtil.isHttpStatusCode(it)
                    log("ForgotPassword", "Error=>$mess")
                    errorMess.value = mess
                }
            )
    }

    /*Change password*/
    @SuppressLint("CheckResult")
    fun changePassword(mobile:String,current_password: String, new_password: String) {
        val map = HashMap<String, String>()
        map["mobile"] = mobile
        map["current_password"] = current_password
        map["new_password"] = new_password
        RestClient.webServices().changePassword(map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.status!!) {
                    successMessage.value = it.message
                }
            },
                {
                    val mess = NetworkUtil.isHttpStatusCode(it)
                    log("ChangePasswordActivity", "Error=>$mess")
                    errorMess.value = mess
                }
            )
    }


    /*Update Profile picture*/
    @SuppressLint("CheckResult")
    fun uploadProfileAPI(pathImgBack: File?, uid: String) {
        val mFile = RequestBody.create(MediaType.parse("multipart/form-data"), pathImgBack!!)
        val fileToUploadBackImg = MultipartBody.Part.createFormData("user_avatar", pathImgBack.name, mFile)
        val user_id = RequestBody.create(MediaType.parse("text/plain"), uid.toString())

        RestClient.webServices().uploadProfile(fileToUploadBackImg, user_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.status!!) {
                    requestDataUpdateProfile.value = it.result
                }
            },
                {
                    val mess = NetworkUtil.isHttpStatusCode(it)
                    log("Profile Upload", "Error=>$mess")
                    errorMess.value = mess
                }
            )
    }


}