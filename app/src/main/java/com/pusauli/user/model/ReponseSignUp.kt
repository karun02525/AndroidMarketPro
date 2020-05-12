package com.pusauli.user.model

import com.google.gson.annotations.SerializedName

data class ReponseSignUp(
    @SerializedName("message")
    var message: String?,
    @SerializedName("data")
    var result: ResultDetails?,
    @SerializedName("status")
    var status: Boolean?
)

data class ResultDetails(
    @SerializedName("name")
    var firstName: String?,
    @SerializedName("gender")
    var gender: String?,
    @SerializedName("mobile")
    var mobile: String?,
    @SerializedName("email")
    var email: String?,
    @SerializedName("uid")
    var userId: String?,
    @SerializedName("address")
    var city: String?,
    @SerializedName("token")
    var token: String?,
    @SerializedName("user_avatar")
    var user_avatar: String?
)

data class ResponseRegisterDevice(
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: Boolean?
)