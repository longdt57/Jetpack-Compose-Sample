package com.example.jetpackcompose.domain

import com.example.jetpackcompose.DataProvider
import com.example.jetpackcompose.data.repository.IProductRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class GetProductUseCaseTest {

    private lateinit var productUseCase: GetProductUseCase
    private val productRepository: IProductRepository = mock()

    @Before
    fun init() {
        productUseCase = GetProductUseCase(productRepository)
    }

    @Test
    fun `test fetch product successfully, remote is called`() = runTest {
        val items = DataProvider.FakeListData
        whenever(productRepository.fetchProducts()).thenReturn(items)
        productUseCase.fetchRemote(Unit).collect()

        verify(productRepository, times(1)).fetchProducts()
        verify(productRepository, times(1)).clearAndSaveAllProducts(items)
    }

    @Test
    fun `test get local data, local is called`() = runTest {
        whenever(productRepository.getLocalProducts()).thenReturn(flowOf())
        productUseCase.getLocal(Unit).collect()
        verify(productRepository, times(1)).getLocalProducts()
    }

    @Test
    fun `test fetch product failed, local is called`() = runTest {
        whenever(productRepository.fetchProducts()).thenThrow(DataProvider.getErrorHttp401())
        whenever(productRepository.getLocalProducts()).thenReturn(flowOf())

        productUseCase.fetchRemoteFallbackLocal(Unit).collect()
        verify(productRepository, times(1)).fetchProducts()
        verify(productRepository, times(1)).getLocalProducts()
    }
}