package com.example.jetpackcompose.ui.cart

import com.example.jetpackcompose.data.model.ProductItem

data class ShoppingItem(
    val item: ProductItem,
    val count: Int,
)
