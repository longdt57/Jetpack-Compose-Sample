package com.example.jetpackcompose.data.network

import com.example.jetpackcompose.data.model.ProductResponse
import retrofit2.http.GET

interface AppService {

    @GET("products")
    suspend fun fetchProducts(): ProductResponse
}
