package com.pusauli.user.model

import com.google.gson.annotations.SerializedName


data class OTPModel(
    @SerializedName("data")
    var `data`: ResultOTP?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: Boolean?
)

data class ResultOTP(
    @SerializedName("is_create")
    var isCreate: Int?
)




