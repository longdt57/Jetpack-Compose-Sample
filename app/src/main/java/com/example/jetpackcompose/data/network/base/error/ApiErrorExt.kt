package com.example.jetpackcompose.data.network.base.error

fun Throwable.getApiError(): ApiError {
    return ApiErrorUtils.getError(this)
}