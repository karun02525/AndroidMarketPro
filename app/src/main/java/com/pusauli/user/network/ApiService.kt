package com.pusauli.user.network

import com.pusauli.user.model.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*


interface ApiService {


    @POST("/api/authenticate/login")
    fun login(@Body map: HashMap<String, String>): Observable<ReponseSignUp>

    @POST("/api/authenticate/create-user")
    fun signUp(@Body map: HashMap<String, String>): Observable<ReponseSignUp>

    @POST("/api/authenticate/register-device")
    fun registerDevices(@Body map: HashMap<String, String>): Observable<ReponseSignUp>

    @POST("/api/authenticate/forgot-password")
    fun forgotPassword(@Body map: HashMap<String, String>): Observable<ReponseSignUp>

    @POST("/api/authenticate/change-password")
    fun changePassword(@Body map: HashMap<String, String>): Observable<ReponseSignUp>

    @Multipart
    @POST("/api/authenticate/edit-profile")
    fun uploadProfile(
        @Part storeFile: MultipartBody.Part?,
        @Part("uid") uid: RequestBody
    ): Observable<ResponseUpdateProfile>


    @GET("/store/check-vender")
    fun venderVerify(@Query("uid") uid: String?): Observable<ResponseVenderVerify>


    @POST("/store/vender-register")
    fun venderRegister(@Body map: HashMap<String, String>): Observable<ResponseVenderVerify>


    @Multipart
    @POST("/store/shop-register")
    fun postData(
        @Part storeFile: MultipartBody.Part?,
        @Part("store_data") store_data: HashMap<String, Any>
    ): Observable<ResponseModel>


    /*Show Store Category wise*/
    @GET("/store/get-store")
    fun getStoreList(@Query("category") category: String?): Observable<ResponseStoreList>

    @GET("/store/get-store-details")
    fun getStoreDetails(@Query("vender_id") vender_id: String): Observable<ResponseStoreDetails>

    @Multipart
    @POST("/store/gallery-upload")
    fun uploadGallery(
        @Part storeFile: MultipartBody.Part?,
        @Part("vender_id") vender_id: RequestBody
    ): Observable<ResponseUpdateProfile>


    @GET("/store/get-gallery")
    fun getStoreGallery(@Query("vender_id") venderId: String?): Observable<ResponseGalleryModel>

    @GET("/store/remove-gallery")
    fun getRemoveGallery(
        @Query("avatar_id") avatar_id: String?,
        @Query("vender_id") vender_id: String?
    ): Observable<ResponseGalleryModelMessage>

    @GET("/store/get-vender")
    fun getVenderList(@Query("user_id") user_id: Int?): Observable<ResponseVenderModel>

    @GET("/store/nofifications")
    fun getNotifications(@Query("uid") uid: String?): Observable<ResponseNotification>


    @GET("/nearby/get-nearby")
    fun getNearBy(
        @Query("uid") uid: String,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): Observable<ResponseNearby>


}