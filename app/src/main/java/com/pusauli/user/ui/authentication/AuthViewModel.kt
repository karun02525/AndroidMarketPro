package com.pusauli.user.ui.authentication

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.pusauli.user.BuildConfig
import com.pusauli.user.model.OTPModel
import com.pusauli.user.model.ResultDetails
import com.pusauli.user.model.ResultOTP
import com.pusauli.user.model.ResultUpdateProfile
import com.pusauli.user.network.NetworkUtil
import com.pusauli.user.network.RestClient
import com.pusauli.user.utils.getDeviceToken
import com.pusauli.user.utils.log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class AuthViewModel : ViewModel() {

    var requestData = MutableLiveData<ResultDetails>()
    var requestDataUpdateProfile = MutableLiveData<ResultUpdateProfile>()
    var requestDataOTP = MutableLiveData<ResultOTP>()
    var requestDataOTPVerify = MutableLiveData<OTPModel>()



    val errorMess = MutableLiveData<String>()
    val successMessage = MutableLiveData<String>()


    val deviceToken = getDeviceToken()!!
    val verName = BuildConfig.VERSION_NAME
    val verCode = BuildConfig.VERSION_CODE
    val deviceName = com.pusauli.user.utils.deviceName




    /*Login*/
    @SuppressLint("CheckResult")
    fun onLoginSubmit(mob: String, pass: String) {
        log("Login", "Data=>$mob ,$pass")
        val map = HashMap<String, String>()
        map["mobile"] = mob
        map["password"] = pass
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
    fun onSignUpSubmit(mobile: String,fname: String, lname: String, pass: String, gender: String) {
        val map = HashMap<String, String>()
        map["mobile"] = mobile
        map["first_name"] = fname
        map["last_name"] = lname
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