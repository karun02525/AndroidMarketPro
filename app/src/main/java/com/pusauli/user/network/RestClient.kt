package com.pusauli.user.network

import com.pusauli.user.network.Const.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RestClient {

    private var retrofit: Retrofit? = null
    private var restClient: RestClient? = null

    private val retrofitInstance: Retrofit
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!
        }


    private val okHttpClient: OkHttpClient
        get() = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(ConnectivityInterceptor())
            .addInterceptor(HeaderAdder())
            .addInterceptor(AuthInterceptor())
            .build()

    private val httpLoggingInterceptor: HttpLoggingInterceptor
        get() {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            return httpLoggingInterceptor
        }

    fun webServices(): ApiService {
        return getRestClient().retrofitInstance.create(ApiService::class.java)
    }

    private fun getRestClient(): RestClient {
        if (restClient == null) {
            restClient = RestClient
        }
        return restClient!!
    }
}

