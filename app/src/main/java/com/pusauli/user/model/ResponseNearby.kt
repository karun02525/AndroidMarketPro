package com.pusauli.user.model
import com.google.gson.annotations.SerializedName


data class ResponseNearby(
    @SerializedName("message")
    var message: String?,
    @SerializedName("data")
    var result: List<ResultNearby>?,
    @SerializedName("status")
    var status: Boolean?
)

data class ResultNearby(
    @SerializedName("category")
    val category: String,
    @SerializedName("distance")
    val distance: String,
    @SerializedName("location")
    val location: List<Double>,
    @SerializedName("shop_name")
    val shopName: String
)


