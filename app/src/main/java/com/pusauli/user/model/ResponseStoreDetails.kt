package com.pusauli.user.model
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class ResponseStoreDetails(
    @SerializedName("message")
    val message: String? = "",
    @SerializedName("data")
    val result:ResultStoreDetails,
    @SerializedName("status")
    val status: Boolean? = false
)

data class ResultStoreDetails(
    @SerializedName("category")
    val category: String? = "",
    @SerializedName("latitude")
    val latitude: String? = "",
    @SerializedName("longitude")
    val longitude: String? = "",
    @SerializedName("shop_owner_email")
    val owerEmail: String? = "",
    @SerializedName("shop_owner_mobile")
    val owerMobile: String? = "",
    @SerializedName("shop_ownername")
    val owerName: String? = "",
    @SerializedName("shop_address")
    val shopAddress: String? = "",
    @SerializedName("store_avatar")
    val shopAvatar: String? = "",
    @SerializedName("shop_color")
    val shopColor: String? = "",
    @SerializedName("shop_email")
    val shopEmail: String? = "",
    @SerializedName("shop_mobile")
    val shopMobile: String? = "",
    @SerializedName("shop_name")
    val shopName: String? = "",
    @SerializedName("shop_nearby")
    val shopNearBy: String? = "",
    @SerializedName("shop_pincode")
    val shopZip: String? = "",
    @SerializedName("user_avatar")
    val userAvatar: String? = "",
    @SerializedName("user_id")
    val userId: String? = "",
    @SerializedName("vender_id")
    val venderId: String? = "",
    @SerializedName("gallery_list")
    val galleryList: List<GalleryDetails>? = listOf()
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(category)
        parcel.writeString(latitude)
        parcel.writeString(longitude)
        parcel.writeString(owerEmail)
        parcel.writeString(owerMobile)
        parcel.writeString(owerName)
        parcel.writeString(shopAddress)
        parcel.writeString(shopAvatar)
        parcel.writeString(shopColor)
        parcel.writeString(shopEmail)
        parcel.writeString(shopMobile)
        parcel.writeString(shopName)
        parcel.writeString(shopNearBy)
        parcel.writeString(shopZip)
        parcel.writeString(userAvatar)
        parcel.writeString(userId)
        parcel.writeString(venderId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ResultStoreDetails> {
        override fun createFromParcel(parcel: Parcel): ResultStoreDetails {
            return ResultStoreDetails(parcel)
        }

        override fun newArray(size: Int): Array<ResultStoreDetails?> {
            return arrayOfNulls(size)
        }
    }
}

data class GalleryDetails(
    @SerializedName("gallery_avatar")
    val galleryAvatar: String? = ""
):Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(galleryAvatar)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GalleryDetails> {
        override fun createFromParcel(parcel: Parcel): GalleryDetails {
            return GalleryDetails(parcel)
        }

        override fun newArray(size: Int): Array<GalleryDetails?> {
            return arrayOfNulls(size)
        }
    }
}