package com.pusauli.user.model
import com.google.gson.annotations.SerializedName


data class ResponseVenderModel(
    @SerializedName("message")
    var message: String?,
    @SerializedName("result")
    var result: ResultVenderModel?,
    @SerializedName("status")
    var status: Boolean?
)

data class ResultVenderModel(
    @SerializedName("latitude")
    var latitude: String?,
    @SerializedName("longitude")
    var longitude: String?,
    @SerializedName("ower_email")
    var owerEmail: String?,
    @SerializedName("ower_mobile")
    var owerMobile: String?,
    @SerializedName("ower_name")
    var owerName: String?,
    @SerializedName("shop_address")
    var shopAddress: String?,
    @SerializedName("shop_avatar")
    var shopAvatar: String?,
    @SerializedName("shop_color")
    var shopColor: String?,
    @SerializedName("shop_email")
    var shopEmail: String?,
    @SerializedName("shop_mobile")
    var shopMobile: String?,
    @SerializedName("shop_name")
    var shopName: String?,
    @SerializedName("shop_near_by")
    var shopNearBy: String?,
    @SerializedName("shop_zip")
    var shopZip: String?,
    @SerializedName("user_avatar")
    var userAvatar: String?
)