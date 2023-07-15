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
class GetShoppingCardUseCaseTest {

    private lateinit var useCase: GetShoppingCardUseCase
    private val repository: IProductCardRepository = mock()

    @Before
    fun init() {
        useCase = GetShoppingCardUseCase(repository)
    }

    @Test
    fun `test get card count`() = runTest {
        val cardItems = DataProvider.FakeCardList
        val expectedCount = cardItems.sumOf { it.count }

        whenever(repository.getAllCardItems()).thenReturn(cardItems)
        val actualCount = useCase.getCardItemCount()
        assertEquals(expectedCount, actualCount)
    }

    @Test
    fun `test get card items`() = runTest {
        val cardItems = DataProvider.FakeCardList
        val productItems = DataProvider.FakeProductList
        val ids = cardItems.map { it.itemId }
        whenever(repository.getAllCardItems()).thenReturn(cardItems)
        whenever(repository.getLocalProducts(ids)).thenReturn(productItems)

        val expectedResult = Pair(cardItems, productItems)
        useCase.getCardItems()
            .collect {
                assertEquals(it, expectedResult)
            }
    }
}