package com.example.jetpackcompose.domain

import com.example.jetpackcompose.data.repository.IProductCardRepository
import com.example.jetpackcompose.helper.DataProvider
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class AddItemToShoppingCardUseCaseTest {

    private lateinit var useCase: AddItemToShoppingCardUseCase
    private val repository: IProductCardRepository = mock()

    @Before
    fun init() {
        useCase = AddItemToShoppingCardUseCase(repository)
    }

    @Test
    fun `test add existed item`() = runTest {
        val cardItems = DataProvider.FakeCardList
        val productItem = DataProvider.FakeProductItem
        whenever(repository.getAllCardItems()).thenReturn(cardItems)

        useCase.addItemToCard(productItem)
            .collect { cardItem ->
                assertEquals(cardItem.itemId, productItem.name)
                assertEquals(cardItem.count, cardItems.first { it.itemId == cardItem.itemId }.count + 1)
            }
    }

    @Test
    fun `test add new item`() = runTest {
        val productItem = DataProvider.FakeProductItem
        whenever(repository.getAllCardItems()).thenReturn(emptyList())

        useCase.addItemToCard(productItem)
            .collect { cardItem ->
                assertEquals(cardItem.itemId, productItem.name)
                assertEquals(cardItem.count, 1)
            }
    }
}