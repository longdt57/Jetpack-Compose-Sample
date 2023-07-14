package com.example.jetpackcompose.data.repository

import com.example.jetpackcompose.data.database.AppDatabase
import com.example.jetpackcompose.data.model.CardAddedItem
import com.example.jetpackcompose.data.model.ProductItem
import com.example.jetpackcompose.data.network.AppService
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

interface IProductRepository {
    suspend fun fetchProducts(): List<ProductItem>
    fun getLocalProducts(): Flow<List<ProductItem>>
    fun getShoppingCardProducts(): List<CardAddedItem>
    fun clearAndSaveAllProducts(items: List<ProductItem>)
}

class ProductRepository @Inject constructor(
    private val appService: AppService,
    private val appDatabase: AppDatabase
) : IProductRepository {

    private val productDao get() = appDatabase.productDao()

    override suspend fun fetchProducts(): List<ProductItem> {
        return appService.fetchProducts().items
    }

    override fun getLocalProducts(): Flow<List<ProductItem>> {
        return productDao.getAllProducts()
    }

    override fun getShoppingCardProducts(): List<CardAddedItem> {
        TODO("Not yet implemented")
    }

    override fun clearAndSaveAllProducts(items: List<ProductItem>) {
        productDao.deleteAll()
        productDao.insertAll(items)
    }

}
