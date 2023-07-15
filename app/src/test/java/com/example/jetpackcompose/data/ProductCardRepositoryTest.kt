package com.example.jetpackcompose.data

import com.example.jetpackcompose.data.database.ProductCardDao
import com.example.jetpackcompose.data.database.ProductDao
import com.example.jetpackcompose.data.repository.IProductCardRepository
import com.example.jetpackcompose.data.repository.ProductCardRepository
import com.example.jetpackcompose.helper.DataProvider
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class ProductCardRepositoryTest {

    private lateinit var repository: IProductCardRepository
    private val productDao: ProductDao = mock()
    private val productCardDao: ProductCardDao = mock()

    @Before
    fun init() {
        repository = ProductCardRepository(productDao, productCardDao)
    }

    @Test
    fun `test get item by ids`() {
        val data = DataProvider.FakeProductList
        val ids = data.map { it.name }
        whenever(productDao.getItemByIds(ids)).thenReturn(data)

        val items = repository.getLocalProducts(ids = ids)
        assertEquals(items, data)
    }
}