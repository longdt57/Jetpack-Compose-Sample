package com.example.jetpackcompose.data.network.base.error

import com.example.jetpackcompose.util.GsonUtil
import retrofit2.HttpException

object ApiErrorUtils {

    fun getError(throwable: Throwable): ApiError {
        return when (throwable) {
            is HttpException -> getErrorFromHttpException(throwable)
            else -> getDefaultApiError(throwable)
        }
    }

    private fun getErrorFromHttpException(throwable: HttpException): ApiError {
        val errorBody: String? = throwable.response()?.errorBody()?.string()
        return GsonUtil.fromJson<ApiError>(errorBody) ?: getDefaultApiError(throwable)
    }

    private fun getDefaultApiError(throwable: Throwable) = ApiError(description = throwable.message)
}
