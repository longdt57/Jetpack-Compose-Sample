package com.example.jetpackcompose.data

import com.example.jetpackcompose.data.database.ProductDao
import com.example.jetpackcompose.data.model.ProductResponse
import com.example.jetpackcompose.data.network.AppService
import com.example.jetpackcompose.data.repository.IProductRepository
import com.example.jetpackcompose.data.repository.ProductRepository
import com.example.jetpackcompose.helper.DataProvider
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
@OptIn(ExperimentalCoroutinesApi::class)
class ProductRepositoryTest {

    private lateinit var productRepository: IProductRepository
    private val productDao: ProductDao = mock()
    private val appService: AppService = mock()

    @Before
    fun init() {
        productRepository = ProductRepository(appService, productDao)
    }

    @Test
    fun `test fetch data, api is called`() = runTest {
        val response = ProductResponse(DataProvider.FakeProductList)
        whenever(appService.fetchProducts()).thenReturn(response)
        productRepository.fetchProducts(true)
        verify(appService, times(1)).fetchProducts()
    }

    @Test
    fun `test fetch data but api is not ready, sample data is return`() = runTest {
        productRepository.fetchProducts(false)
        verify(appService, times(0)).fetchProducts()
    }

    @Test
    fun `test get item by ids`() {
        val data = DataProvider.FakeProductList
        val ids = data.map { it.name }
        whenever(productDao.getItemByIds(ids)).thenReturn(data)

        val items = productRepository.getLocalProducts(ids = ids)
        TestCase.assertEquals(items, data)
    }

    @Test
    fun `test clearAndSaveAllProducts`() {
        val items = DataProvider.FakeProductList
        productRepository.clearAndSaveAllProducts(items)
        verify(productDao, times(1)).deleteAll()
        verify(productDao, times(1)).insertAll(items)
    }

    @Test
    fun `test get local data, room is called`() {
        productRepository.getLocalProducts()
        verify(productDao, times(1)).getAll()
    }
}
