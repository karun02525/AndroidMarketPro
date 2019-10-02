package com.pusauli.user.model
import com.google.gson.annotations.SerializedName


data class ResponseModel(
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Boolean?,
    @SerializedName("result")
    val result: Result?
)
data class Result(
    @SerializedName("store_id")
    val storeId: String?,
    @SerializedName("vender_id")
    val vender_id: String?
)