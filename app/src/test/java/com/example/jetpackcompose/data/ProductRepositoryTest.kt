package com.example.jetpackcompose.data

import com.example.jetpackcompose.DataProvider
import com.example.jetpackcompose.data.database.ProductDao
import com.example.jetpackcompose.data.repository.IProductRepository
import com.example.jetpackcompose.data.repository.ProductRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class ProductRepositoryTest {

    private lateinit var productRepository: IProductRepository
    private val productDao: ProductDao = mock()

    @Before
    fun init() {
        productRepository = ProductRepository(mock(), productDao)
    }

    @Test
    fun `test clearAndSaveAllProducts`() {
        val items = DataProvider.FakeListData
        productRepository.clearAndSaveAllProducts(items)
        verify(productDao, times(1)).deleteAll()
        verify(productDao, times(1)).insertAll(items)
    }
}