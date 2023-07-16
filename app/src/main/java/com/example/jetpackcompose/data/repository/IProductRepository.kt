package com.example.jetpackcompose.data.repository

import com.example.jetpackcompose.data.database.ProductDao
import com.example.jetpackcompose.data.model.ProductItem
import com.example.jetpackcompose.data.network.AppService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface IProductRepository {
    suspend fun fetchProducts(isApiAvailable: Boolean = false): List<ProductItem>
    fun getLocalProducts(): Flow<List<ProductItem>>
    fun clearAndSaveAllProducts(items: List<ProductItem>)
}

class ProductRepository @Inject constructor(
    private val appService: AppService,
    private val productDao: ProductDao,
) : IProductRepository {

    @SuppressWarnings("MagicNumber")
    override suspend fun fetchProducts(isApiAvailable: Boolean): List<ProductItem> {
        return if (isApiAvailable) {
            appService.fetchProducts().items
        } else {
            // Todo remove this code when api is available
            delay(1000)
            ApiFakeData.getDummyProducts()?.items.orEmpty()
        }
    }

    override fun getLocalProducts(): Flow<List<ProductItem>> {
        return productDao.getAll()
    }

    override fun clearAndSaveAllProducts(items: List<ProductItem>) {
        productDao.deleteAll()
        productDao.insertAll(items)
    }
}
