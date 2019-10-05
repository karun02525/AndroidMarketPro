package com.pusauli.user.model
import com.google.gson.annotations.SerializedName


data class ResponseStoreList(
    @SerializedName("data")
    var result: List<StoreListResult>?,
    @SerializedName("status")
    var status: Boolean?
)

data class StoreListResult(
    @SerializedName("uid")
    var userId: String?,
    @SerializedName("vender_id")
    var venderId: String?,
    @SerializedName("category_id")
    var category_id: String?,
    @SerializedName("category_name")
    var category_name: String?,
    @SerializedName("shop_address")
    var shopAddress: String?,
    @SerializedName("store_avatar")
    var shopAvatar: String?,
    @SerializedName("user_avatar")
    var user_avatar: String?,
    @SerializedName("shop_mobile")
    var shopMobile: String?,
    @SerializedName("shop_name")
    var shopName: String?
)