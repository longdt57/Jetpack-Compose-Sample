package com.example.jetpackcompose.data.network.base.error

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
open class ApiError(
    @SerializedName("description") val description: String?,
    @SerializedName("error") val error: Boolean? = true,
    @SerializedName("message") val message: String? = description,
    @SerializedName("status") val status: Int? = -1
) {
    fun getErrorMessage() = description ?: message ?: ""
}
