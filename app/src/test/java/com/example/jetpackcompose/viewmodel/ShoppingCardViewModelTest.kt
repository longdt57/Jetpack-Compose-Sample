package com.example.jetpackcompose.viewmodel

import com.example.jetpackcompose.domain.GetShoppingCardUseCase
import com.example.jetpackcompose.helper.DataProvider
import com.example.jetpackcompose.helper.MainDispatcherRule
import com.example.jetpackcompose.ui.card.ShoppingCardViewModel
import com.example.jetpackcompose.ui.ext.toShoppingItems
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class ShoppingCardViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    private val useCase: GetShoppingCardUseCase  = mock()
    private lateinit var viewModel: ShoppingCardViewModel

    @Before
    fun init() {
        viewModel = ShoppingCardViewModel(useCase, StandardTestDispatcher())
    }

    @Test
    fun `test add to card success`() = runTest {
        val productItems = DataProvider.FakeProductList
        val cardItems = DataProvider.FakeCardList
        val shoppingData = Pair(cardItems, productItems)
        whenever(useCase.getCardItems()).thenReturn(flowOf(shoppingData))
        val expectedResult = shoppingData.toShoppingItems()

        viewModel.getCardItems()
        advanceUntilIdle()
        assertEquals(viewModel.items.value, expectedResult)
    }
}