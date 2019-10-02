package com.pusauli.user.model

import com.google.gson.annotations.SerializedName


data class ResponseUpdateProfile(
    @SerializedName("message")
    var message: String?,
    @SerializedName("data")
    var result: ResultUpdateProfile?,
    @SerializedName("status")
    var status: Boolean?
)

data class ResultUpdateProfile(
    @SerializedName("user_avatar")
    var userAvatar: String?,
    @SerializedName("uid")
    var userId: String?
)

