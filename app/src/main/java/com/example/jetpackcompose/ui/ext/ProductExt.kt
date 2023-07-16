package com.example.jetpackcompose.ui.ext

import com.example.jetpackcompose.data.model.CartItem
import com.example.jetpackcompose.data.model.ProductItem
import com.example.jetpackcompose.ui.cart.ShoppingItem

fun Pair<List<CartItem>, List<ProductItem>>.toShoppingItems(): List<ShoppingItem> {
    val cartItems = first
    val productItems = second
    val shoppingItems = productItems.map { item ->
        val count = cartItems.first { it.itemId == item.name }.count
        ShoppingItem(item, count)
    }
    return shoppingItems
}
