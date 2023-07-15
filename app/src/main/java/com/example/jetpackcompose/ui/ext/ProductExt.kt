package com.example.jetpackcompose.ui.ext

import com.example.jetpackcompose.data.model.CardAddedItem
import com.example.jetpackcompose.data.model.ProductItem
import com.example.jetpackcompose.ui.card.ShoppingItem

fun Pair<List<CardAddedItem>, List<ProductItem>>.toShoppingItems(): List<ShoppingItem> {
    val cardItems = first
    val productItems = second
    val shoppingItems = productItems.map { item ->
        val count = cardItems.first { it.itemId == item.name }.count
        ShoppingItem(item, count)
    }
    return shoppingItems
}