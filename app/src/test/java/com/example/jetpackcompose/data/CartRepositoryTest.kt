package com.example.jetpackcompose.data

import com.example.jetpackcompose.data.database.CartDao
import com.example.jetpackcompose.data.model.CartItem
import com.example.jetpackcompose.data.repository.CartRepository
import com.example.jetpackcompose.data.repository.ICartRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class CartRepositoryTest {

    private lateinit var repository: ICartRepository
    private val cartDao: CartDao = mock()

    @Before
    fun init() {
        repository = CartRepository(cartDao)
    }

    @Test
    fun `test insert cart item, room is called`() {
        val cartItem = CartItem("test", 1)
        repository.insertCartItem(cartItem)
        verify(cartDao, times(1)).insert(cartItem)
    }

    @Test
    fun `test get all cart items, room is called`() {
        repository.getAllCartItems()
        verify(cartDao, times(1)).getAll()
    }
}
