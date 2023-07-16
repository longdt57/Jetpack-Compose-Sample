package com.example.jetpackcompose.viewmodel

import app.cash.turbine.test
import com.example.jetpackcompose.data.model.CartItem
import com.example.jetpackcompose.data.network.base.error.getApiError
import com.example.jetpackcompose.domain.AddItemToCartUseCase
import com.example.jetpackcompose.helper.DataProvider
import com.example.jetpackcompose.helper.MainDispatcherRule
import com.example.jetpackcompose.ui.detail.ProductDetailViewModel
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
class ProductDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    private val addUseCase: AddItemToCartUseCase = mock()
    private lateinit var viewModel: ProductDetailViewModel

    @Before
    fun init() {
        viewModel = ProductDetailViewModel(addUseCase, StandardTestDispatcher())
    }

    @Test
    fun `test add to cart success`() = runTest {
        val productItem = DataProvider.FakeProductItem
        val cartItem = CartItem(productItem.name, 1)
        whenever(addUseCase.invoke(productItem)).thenReturn(flowOf(cartItem))

        viewModel.addToCart(productItem)
        advanceUntilIdle()
        assertEquals(viewModel.addSuccess.value, true)
    }

    @Test
    fun `test add to cart failed`() = runTest {
        val productItem = DataProvider.FakeProductItem
        val error = IllegalStateException("Illegal State")
        whenever(addUseCase.invoke(productItem)).thenReturn(flow { throw error })

        viewModel.error.test {
            viewModel.addToCart(productItem)
            assertEquals(awaitItem(), error.getApiError().getErrorMessage())
        }
    }
}
