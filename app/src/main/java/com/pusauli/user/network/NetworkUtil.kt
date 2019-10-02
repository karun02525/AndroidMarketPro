package com.pusauli.user.network

import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException


object NetworkUtil {
    fun isHttpStatusCode(e: Throwable): String{
        var errorMess = ""

        if (e is HttpException) {
            val responseBody = e.response().errorBody()
            errorMess = (getErrorMessage(responseBody!!)!!)
            when {
                e.code() == 403 -> errorMess = "Something went wrong 403"
                e.code() == 404 -> errorMess = "Something went wrong 404"
                e.code() == 500 -> errorMess ="Something went wrong 500"
                e.code() == 502 -> errorMess = "Something went wrong 502"
            }
        }
        return errorMess
    }

}

   private fun getErrorMessage(responseBody: ResponseBody): String? {
    return try {
        val jsonObject = JSONObject(responseBody.string())
        jsonObject.getString("message")
    } catch (e: Exception) {
        e.message
    }
  }



