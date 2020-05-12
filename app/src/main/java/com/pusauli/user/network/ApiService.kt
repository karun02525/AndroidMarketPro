package com.pusauli.user.network

import com.pusauli.user.model.*
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*


interface ApiService {


    @POST("/authenticate/login")
    suspend fun login(@Body map: HashMap<String, String>): ReponseSignUp

    @POST("/authenticate/register")
    fun signUp(@Body map: HashMap<String, String>): Single<ReponseSignUp>

    @POST("/authenticate/register-device")
    suspend fun registerDevice(@Header("Authorization") token:String,@Body map: HashMap<String, String>): ResponseRegisterDevice

    fun loginDetails(it:ReponseSignUp): Single<ReponseSignUp>

    @GET("/category/get-category")
    suspend fun getCategorys(@Header("Authorization") token:String): CategoryModel

    @GET("/category/get-category")
    fun getCategory(): Observable<CategoryModel>

    @GET("/authenticate/vender_verify")
    fun venderVerify(): Observable<ResponseVenderVerify>

    @GET("/authenticate/vender_register/{category_id}/{category_name}")
    fun venderRegister(@Path("category_id") category_id:String,
                       @Path("category_name") category_name:String) : Observable<ResponseVenderVerify>

    @GET("/authenticate/notifications")
    fun getNotifications(): Observable<ResponseNotification>





    @POST("/api/authenticate/send-otp")
    fun onSendOtp(@Body map: HashMap<String, String>): Observable<OTPModel>

    @POST("/api/authenticate/verify-otp")
    fun onSendVerityOtp(@Body map: HashMap<String, String>): Observable<OTPModel>


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




    @Multipart
    @POST("/store/shop-register")
    fun postData(
        @Part storeFile: MultipartBody.Part?,
        @Part("store_data") store_data: HashMap<String, Any>
    ): Observable<ResponseModel>


    /*Show Store Category wise*/
    @GET("/store/get-store")
    fun getStoreList(@Query("category_id") category: String?): Observable<ResponseStoreList>

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


    @GET("/nearby/get-nearby")
    fun getNearBy(
        @Query("uid") uid: String,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): Observable<ResponseNearby>


}