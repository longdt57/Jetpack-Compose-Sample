package com.example.jetpackcompose.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ProductItem(
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("status")
    val status: String,
) : Parcelable {
    fun isAvailable() = status == "available"
    fun isOutOfStock() = status == "out-of-stock"
    fun isCommingSoon() = status == "comming-soon"
}
