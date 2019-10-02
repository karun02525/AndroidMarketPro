package com.pusauli.user.model
import com.google.gson.annotations.SerializedName



data class ResponseGalleryModelMessage(
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: Boolean?
)


data class ResponseGalleryModel(
    @SerializedName("message")
    var message: String?,
    @SerializedName("data")
    var result: ArrayList<ResultGalleryModel>?,
    @SerializedName("status")
    var status: Boolean?
)

data class ResultGalleryModel(
    @SerializedName("gallery_avatar")
    var galleryAvatar: String?,
    @SerializedName("avatar_id")
    var avatar_id: String?
)