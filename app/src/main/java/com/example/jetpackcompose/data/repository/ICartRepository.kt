package com.example.jetpackcompose.data.repository

import com.example.jetpackcompose.data.database.CartDao
import com.example.jetpackcompose.data.model.CartItem
import javax.inject.Inject

interface ICartRepository {

    fun insertCartItem(item: CartItem)
    fun getAllCartItems(): List<CartItem>
}

class CartRepository @Inject constructor(
    private val cartDao: CartDao,
) : ICartRepository {

    override fun insertCartItem(item: CartItem) {
        cartDao.insert(item)
    }

    override fun getAllCartItems(): List<CartItem> {
        return cartDao.getAll()
    }
}
