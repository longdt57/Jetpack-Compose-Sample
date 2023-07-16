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
class GetCartNumberUseCaseTest {

    private lateinit var useCase: GetCartNumberUseCase
    private val repository: ICartRepository = mock()

    @Before
    fun init() {
        useCase = GetCartNumberUseCase(repository)
    }

    @Test
    fun `test get cart count`() = runTest {
        val cartItems = DataProvider.FakeCartList
        val expectedCount = cartItems.sumOf { it.count }

        whenever(repository.getAllCartItems()).thenReturn(cartItems)
        val actualCount = useCase.invoke()
        assertEquals(expectedCount, actualCount)
    }
}
