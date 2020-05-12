package com.pusauli.user.network


object Const {

   // On laptop, run adb reverse tcp:4000 tcp:4000
    //Now, on the mobile device, you can navigate to http://localhost:4000/

    const val BASE_URL = "http://192.168.0.100:8080"
    const val PlayStore_LINK = "https://play.google.com/store/apps/details?id="
    const val STORE_AVATAR_BASE_URL = "$BASE_URL/store/image-store/"
    const val PROFILE_AVATAR_BASE_URL = "$BASE_URL/authenticate/image-profile/"
    const val CATEGORY_AVATAR_BASE_URL = "$BASE_URL/category/image-category/"

}
