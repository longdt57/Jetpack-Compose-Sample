package com.example.jetpackcompose.viewmodel

import app.cash.turbine.test
import com.example.jetpackcompose.data.network.base.error.getApiError
import com.example.jetpackcompose.domain.GetCartUseCase
import com.example.jetpackcompose.helper.DataProvider
import com.example.jetpackcompose.helper.MainDispatcherRule
import com.example.jetpackcompose.ui.cart.CartViewModel
import com.example.jetpackcompose.ui.ext.toShoppingItems
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class CartViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    private val useCase: GetCartUseCase = mock()
    private lateinit var viewModel: CartViewModel

    @Before
    fun init() {
        viewModel = CartViewModel(useCase, StandardTestDispatcher())
    }

    @Test
    fun `test get cart data success`() = runTest {
        val productItems = DataProvider.FakeProductList
        val cartItems = DataProvider.FakeCartList
        val shoppingData = Pair(cartItems, productItems)
        whenever(useCase.invoke()).thenReturn(flowOf(shoppingData))
        val expectedResult = shoppingData.toShoppingItems()

        viewModel.getCartItems()
        advanceUntilIdle()
        assertEquals(viewModel.items.value, expectedResult)
    }

    @Test
    fun `test get cart data failed`() = runTest {
        val error = IllegalStateException("Illegal State")
        whenever(useCase.invoke()).thenReturn(flow { throw error })
        val expectedResult = error.getApiError().getErrorMessage()

        viewModel.error.test {
            viewModel.getCartItems()
            advanceUntilIdle()
            assertEquals(expectMostRecentItem(), expectedResult)
        }
    }
}
