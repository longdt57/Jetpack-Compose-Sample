package com.example.jetpackcompose.domain

import com.example.jetpackcompose.data.repository.ICartRepository
import com.example.jetpackcompose.helper.DataProvider
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class AddItemToCartUseCaseTest {

    private lateinit var useCase: AddItemToCartUseCase
    private val repository: ICartRepository = mock()

    @Before
    fun init() {
        useCase = AddItemToCartUseCase(repository)
    }

    @Test
    fun `test add existed item`() = runTest {
        val cartItems = DataProvider.FakeCartList
        val productItem = DataProvider.FakeProductItem
        whenever(repository.getAllCartItems()).thenReturn(cartItems)

        useCase.invoke(productItem)
            .collect { cartItem ->
                assertEquals(cartItem.itemId, productItem.name)
                assertEquals(cartItem.count, cartItems.first { it.itemId == cartItem.itemId }.count + 1)
            }
    }

    @Test
    fun `test add new item`() = runTest {
        val productItem = DataProvider.FakeProductItem
        whenever(repository.getAllCartItems()).thenReturn(emptyList())

        useCase.invoke(productItem)
            .collect { cartItem ->
                assertEquals(cartItem.itemId, productItem.name)
                assertEquals(cartItem.count, 1)
            }
    }
}
