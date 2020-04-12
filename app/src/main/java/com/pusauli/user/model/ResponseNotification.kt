package com.pusauli.user.model

import com.google.gson.annotations.SerializedName


data class ResponseNotification(
    @SerializedName("data")
    val `data`: List<NotificationData>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)

data class NotificationData(
    @SerializedName("category")
    var category: String? = null,
    @SerializedName("create_at")
    var createAt: String? = null,
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("notification_id")
    var notificationId: String? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("type")
    var type: String? = null,
    @SerializedName("uid")
    var uid: String? = null,
    @SerializedName("vender_id")
    var venderId: String? = null
)