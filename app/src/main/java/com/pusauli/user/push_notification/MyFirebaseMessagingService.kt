package com.pusauli.user.push_notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.firebase.jobdispatcher.GooglePlayDriver
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.pusauli.user.R
import org.json.JSONObject


class MyFirebaseMessagingService : FirebaseMessagingService() {
   // private val sp by lazy { SharedPrefUtils.instance }
    private val TAG = MyFirebaseMessagingService::class.java.name

    companion object {
        var NOTIFICATION_ID = 0
        var NOTIFICATION_GEN = 0
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {

        if (remoteMessage!!.data.isNotEmpty()) {
            var type = ""
            var segment = ""
            var cta_text  = ""
            try {

                val title = remoteMessage.data["title"]
                val mess = remoteMessage.data["message"]

                try {
                    type = remoteMessage.data!!["type"]!!
                } catch (e: java.lang.Exception) {
                }

                try {
                    segment = remoteMessage.data!!["segment"]!!
                } catch (e: java.lang.Exception) {
                }

                try {
                    cta_text  = remoteMessage.data!!["cta_text"]!!
                } catch (e: java.lang.Exception) {
                }

                if (type == "url") {
                    try {
                        if (segment == "link_redirect") {
                            val payload = remoteMessage.data!!["payload"]!!
                            val json_url = JSONObject(payload)
                            var url: String? = null
                            var action: String? = null
                            try {
                                url = json_url["url"] as String
                            } catch (e: Exception) {
                            }
                            try {
                                action = json_url["isImage"] as String
                            } catch (e: Exception) {
                            }
                            try {
                             //   notificationWithMessgaeNoti(title!!, mess!!, url, action)
                            } catch (e: Exception) {
                            }
                        }
                    } catch (e: Exception) {
                    }
                }

                if (type == "message") {
                    try {

                        var id = 0
                        var list_page=false
                        try {
                            val payload = remoteMessage.data!!["payload"]!!
                            val json_id = JSONObject(payload)
                            id = json_id["id"] as Int

                            try {
                                list_page = json_id["list_page"] as Boolean
                            } catch (e: Exception) {
                            }


                        } catch (e: Exception) {
                        }
                        try {
                            notificationWithMessgae(title!!, mess!!, id, segment,cta_text,list_page)
                        } catch (e: Exception) {
                        }
                    } catch (e: Exception) {
                    }
                }

                scheduleJob()
            } catch (e: Exception) {
            }
        }
    }

    /**
     * Schedule a job using FirebaseJobDispatcher.
     */
    private fun scheduleJob() {
        // [START dispatch_job]
        val dispatcher = FirebaseJobDispatcher(GooglePlayDriver(this))
        val myJob = dispatcher.newJobBuilder()
            .setService(MyJobService::class.java)
            .setTag("my-job-tag")
            .build()
        dispatcher.schedule(myJob)
        // [END dispatch_job]
    }



    private fun notificationWithMessgae(title: String?, message: String?, partner_id: Int?, segment: String?, cta_text: String,
        list_page: Boolean){
        val intent: Intent?=null
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channelId = "f1251125"
        val channelName = "com.pusauli.user"


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(channelId, channelName, importance)
            notificationManager.createNotificationChannel(mChannel)
        }

        val builder = NotificationCompat.Builder(this, channelId)
            .setAutoCancel(true)
            .setContentTitle(title)
            .setContentIntent(pendingIntent)
            .setSound(defaultSoundUri)
            .setLights(Color.RED, 3000, 3000)
            .setPriority(Notification.PRIORITY_HIGH)
            .setVibrate(longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400))
            .setSmallIcon(R.drawable.ic_notification)
            .setContentText(message)
        builder.notification.flags = Notification.DEFAULT_LIGHTS or Notification.FLAG_AUTO_CANCEL
        notificationManager.cancel(NOTIFICATION_ID)
        if (NOTIFICATION_ID > 1073741824) {
            NOTIFICATION_ID = 0
        }
        notificationManager.notify(NOTIFICATION_ID++, builder.build())

    }

}
