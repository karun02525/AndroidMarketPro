package com.pusauli.user.model
import com.google.gson.annotations.SerializedName

data class CategoryModel(
    @SerializedName("data")
    var `data`: ArrayList<DataCategory>,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: Boolean?
)

data class DataCategory(
    @SerializedName("category_avatar")
    var categoryAvatar: String?,
    @SerializedName("category_id")
    var categoryId: String?,
    @SerializedName("category_name")
    var categoryName: String?,
    @SerializedName("create_at")
    var createAt: String?
)
