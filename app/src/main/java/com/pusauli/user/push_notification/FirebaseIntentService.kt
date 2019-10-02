package com.pusauli.user.push_notification

import android.annotation.SuppressLint
import android.app.IntentService
import android.content.Intent
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*


class FirebaseIntentService : IntentService("FirebaseIntent") {

   // private val sp  by lazy { SharedPrefUtils.instance }
    //private val db by lazy { NotificationRoomDatabase.getDatabase(this) }
   // val TAG = "FirebaseTag"
   // private val notification = NotificationEntity()


    @SuppressLint("SimpleDateFormat")
    override fun onHandleIntent(intent: Intent?) {

       /* try {

            if (intent!!.getStringExtra("message") != null) {
             notification.message = intent.getStringExtra("message")
            }
            if (intent.getStringExtra("title") != null) {
                notification.title = intent.getStringExtra("title")
            }
            if (intent.getStringExtra("segment") != null) {
                notification.segment = intent.getStringExtra("segment")
            }
            if (intent.getStringExtra("url") != null) {
                notification.url = intent.getStringExtra("url")
            }
            if (intent.getStringExtra("type") != null) {

                notification.type = intent.getStringExtra("type")
            }

            if (intent.getStringExtra("server_time") != null) {
                Log.d(TAG, "FB Server Time : " + intent.getStringExtra("server_time"))
                notification.server_time = intent.getStringExtra("server_time")
            }else{
                val sdf = SimpleDateFormat("yyyy-MM-DD HH:mm:ss")
                val currentDate = sdf.format(Date())
                Log.d(TAG, "FB Server Time Null: $currentDate")
                System.out.println("C DATE is  $currentDate")
                try {
                    notification.server_time =currentDate
                } catch (e: Exception) {
                }
            }

            if(sp.isLoginStatus !=0) {
                db.notificationDao().insertNotification(notification)
            }
            Log.e(TAG, "FINISHED ADDING DATA TO DB")
        } catch (e: Exception) {
        }*/

    }

}

