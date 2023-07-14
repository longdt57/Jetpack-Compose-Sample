package com.example.jetpackcompose.data.model

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("items")
    val items: List<ProductItem>
)
