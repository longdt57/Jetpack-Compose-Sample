package com.example.jetpackcompose.viewmodel

import app.cash.turbine.test
import com.example.jetpackcompose.data.model.ProductItem
import com.example.jetpackcompose.data.network.base.error.getApiError
import com.example.jetpackcompose.domain.GetProductUseCase
import com.example.jetpackcompose.domain.GetShoppingCardUseCase
import com.example.jetpackcompose.helper.DataProvider
import com.example.jetpackcompose.helper.MainDispatcherRule
import com.example.jetpackcompose.ui.main.MainViewModel
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
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    private val getProductUseCase: GetProductUseCase = mock()
    private val cardUseCase: GetShoppingCardUseCase = mock()
    private lateinit var viewModel: MainViewModel

    @Before
    fun init() {
        viewModel = MainViewModel(getProductUseCase, cardUseCase, StandardTestDispatcher())
    }

    @Test
    fun `test fetch data success`() = runTest {
        val data = DataProvider.FakeListData
        whenever(getProductUseCase.fetchRemote(Unit)).thenReturn(flowOf(data))
        whenever(getProductUseCase.getLocal(Unit)).thenReturn(flowOf(data))
        viewModel.refreshData()
        advanceUntilIdle()

        assertEquals(viewModel.items.value, data)
        verify(getProductUseCase, times(1)).fetchRemote(Unit)
    }

    @Test
    fun `test fetch data failed, error is thrown`() = runTest {
        val error = DataProvider.getErrorHttp401()
        whenever(getProductUseCase.fetchRemote(Unit)).thenReturn(flow { throw error })

        advanceUntilIdle()

        viewModel.error.test {
            viewModel.refreshData()
            assertEquals(awaitItem(), error.getApiError().getErrorMessage())
            assertEquals(viewModel.items.value, emptyList<ProductItem>())
        }
    }

    @Test
    fun `test observe local data`() = runTest {
        val data1 = DataProvider.FakeListData
        val data2 = listOf(DataProvider.FakeItem)
        whenever(getProductUseCase.getLocal(Unit)).thenReturn(flowOf(data1, data2))

        viewModel.items.test {
            viewModel.observeLocal()
            skipItems(1)
            advanceUntilIdle()
            assertEquals(expectMostRecentItem(), data2)
        }
    }

    @SuppressWarnings("MagicNumber")
    @Test
    fun `test get card number`() = runTest {
        val number = 6
        whenever(cardUseCase.getCardAddedItemCount()).thenReturn(number)
        viewModel.refreshCardNumber()
        advanceUntilIdle()

        assertEquals(viewModel.cardNumber.value, number)
    }
}