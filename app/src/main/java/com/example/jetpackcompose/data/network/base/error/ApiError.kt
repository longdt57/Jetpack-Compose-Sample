package com.example.jetpackcompose.data.network.base.error

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
open class ApiError(
    @SerializedName("message") val message: String?,
    @SerializedName("status") val status: Int? = -1,
) {
    fun getErrorMessage() = message ?: ""
}
