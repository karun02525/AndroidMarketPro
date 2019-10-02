package com.pusauli.user.push_notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

import org.json.JSONObject

class FirebaseBroadcastReceiver : BroadcastReceiver() {

    lateinit var serviceIntent: Intent

    override fun onReceive(context: Context, intent: Intent) {
        try {
            var isCheckType = ""
            var isCheckTypeMessage = ""
            var isCheckTypeTitle = ""



            try {
                isCheckType = intent.extras!!.getString("type")!!
            } catch (e: java.lang.Exception) { }

            try {
                isCheckTypeMessage = intent.extras!!.getString("message")!!
            } catch (e: java.lang.Exception) { }

           try {
               isCheckTypeTitle = intent.extras!!.getString("title")!!
            } catch (e: java.lang.Exception) { }




            if(isCheckType=="url" && isCheckTypeMessage !="" && isCheckTypeTitle !=""  ) {

            serviceIntent = Intent(context, FirebaseIntentService::class.java)
            if (intent.extras != null) {
                var url: String
                for (key in intent.extras!!.keySet()) {
                    val value = intent.extras!!.get(key)
                    Log.e(TAG, "Here is the data: Key: $key Value: $value")

                       when (key.trim()) {

                           "message" -> serviceIntent.putExtra("message", value!!.toString() + "")
                           "segment" -> serviceIntent.putExtra("segment", value!!.toString() + "")
                           "title" -> serviceIntent.putExtra("title", value!!.toString() + "")
                           "type" -> serviceIntent.putExtra("type", value!!.toString() + "")


                           "server_time" -> {
                               serviceIntent.putExtra("server_time", value!!.toString() + "")
                           }
                           "payload" ->

                               try {
                                   val jsonObject = JSONObject(value!!.toString())
                                   url = jsonObject.getString("url")
                                   Log.e(TAG, "Here is the data: url : $url")
                                   serviceIntent.putExtra("url", url)

                               } catch (e: Exception) {

                                   Log.e(TAG, e.message)
                               }

                       }
                   }

                }
                context.startService(serviceIntent)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    companion object {
        private val TAG = "FirebaseTag"
    }

}
