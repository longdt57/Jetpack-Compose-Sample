package com.example.jetpackcompose.domain

import com.example.jetpackcompose.data.repository.ICartRepository
import com.example.jetpackcompose.data.repository.IProductRepository
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
class GetCartUseCaseTest {

    private lateinit var useCase: GetCartUseCase
    private val cartRepository: ICartRepository = mock()
    private val productRepository: IProductRepository = mock()

    @Before
    fun init() {
        useCase = GetCartUseCase(cartRepository, productRepository)
    }

    @Test
    fun `test get cart items`() = runTest {
        val cartItems = DataProvider.FakeCartList
        val productItems = DataProvider.FakeProductList
        val ids = cartItems.map { it.itemId }
        whenever(cartRepository.getAllCartItems()).thenReturn(cartItems)
        whenever(productRepository.getLocalProducts(ids)).thenReturn(productItems)

        val expectedResult = Pair(cartItems, productItems)
        useCase.invoke()
            .collect {
                assertEquals(it, expectedResult)
            }
    }
}
