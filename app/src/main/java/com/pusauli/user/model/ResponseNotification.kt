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
    @SerializedName("create_at")
    val createAt: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("notification_id")
    val notificationId: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("uid")
    val uid: String
)
