package com.pusauli.user.network

import android.annotation.SuppressLint
import android.util.Log
import com.pusauli.user.ui.authentication.LoginActivity.Companion.isSessionExp
import com.pusauli.user.utils.clearAllDataAndLogOut
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class AuthInterceptor : Interceptor {

    @SuppressLint("CheckResult")
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        try {
            if (response.code() == 401) {
                isSessionExp=true
                clearAllDataAndLogOut()
                Log.d("TAG","AuthInterceptor => Code: "+response.code())
            }
        } catch (e: Exception) { }
        return response
    }
}


/*
    try{


        if (response.code() == 401) {

            HashMap<String,String>payload=new HashMap<>();
            String refresh_token=SharedPrefUtils.getRefreshToken(VendorApplication.getAppContext());
            Log.e(TAG,"Current/old refresh token: "+refresh_token);
            payload.put("refresh_token",refresh_token);


            retrofit2.Response<ApiResponse<AuthTokenData>> resp = RestClient.webAuthServices().refreshToken(payload).execute();

            if (resp.isSuccessful()) {
                Log.e(TAG,"Refresh Token called and is successful");
                String accessToken=resp.body().getData().getAuthorization().getAccess_token();
                String refreshToken=resp.body().getData().getAuthorization().getRefresh_token();
                Log.e(TAG,"NEW ACCESS TOKEN: "+accessToken+" NEW REFRESH TOKEN: "+refreshToken);

                SharedPrefUtils.setAuthToken(VendorApplication.getAppContext(),accessToken );
                SharedPrefUtils.setRefreshToken(VendorApplication.getAppContext(),refreshToken);

                Request requestNew = chain.request().newBuilder()
                        .removeHeader("Authorization")
                        .addHeader("Authorization", "Bearer " +accessToken).build();
                Log.e(TAG, "new refreshed token: "+resp.body().getData().getAuthorization());
                //updated request

                return chain.proceed(requestNew);
            } else if (resp.code() == 401) {
                ValidationUtils.clearAllDataAndLogOut(true);

            }
        }

    }


*/
