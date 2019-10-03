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
    @SerializedName("first_name")
    var firstName: String?,
    @SerializedName("gender")
    var gender: String?,
    @SerializedName("last_name")
    var lastName: String?,
    @SerializedName("mobile")
    var mobile: String?,
    @SerializedName("email")
    var email: String?,
    @SerializedName("uid")
    var userId: String?,
    @SerializedName("city")
    var city: String?,
    @SerializedName("token")
    var authentication: String?,
    @SerializedName("user_avatar")
    var user_avatar: String?
)