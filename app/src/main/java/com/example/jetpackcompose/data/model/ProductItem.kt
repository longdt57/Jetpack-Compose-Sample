package com.example.jetpackcompose.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
@Entity
data class ProductItem(
    @SerializedName("name")
    @PrimaryKey
    val name: String,

    @SerializedName("price")
    @ColumnInfo(name = "price")
    val price: String,


    @SerializedName("content")
    @ColumnInfo(name = "content")
    val content: String?,

    @SerializedName("status")
    @ColumnInfo(name = "status")
    val status: String,
) : Parcelable {
    fun isAvailable() = status == "available"
    fun isOutOfStock() = status == "out-of-stock"
    fun isCommingSoon() = status == "comming-soon"
}
