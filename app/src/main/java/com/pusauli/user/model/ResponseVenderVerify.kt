package com.pusauli.user.model

import com.google.gson.annotations.SerializedName

data class ResponseVenderVerify(
    @SerializedName("message")
    var message: String?,
    @SerializedName("data")
    var result: ResultVerify?,
    @SerializedName("status")
    var status: Boolean?
)

data class ResultVerify(
    @SerializedName("is_verify")
    var isVerify: Int?,
    @SerializedName("vender_id")
    var venderId: String?,
    @SerializedName("category_id")
    var category_id: String?,
    @SerializedName("category_name")
    var category_name: String?
)