package com.example.jetpackcompose.data.repository

import com.example.jetpackcompose.BuildConfig
import com.example.jetpackcompose.data.database.ProductDao
import com.example.jetpackcompose.data.model.ProductItem
import com.example.jetpackcompose.data.network.AppService
import com.example.jetpackcompose.util.ProductItemPreviewData
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

interface IProductRepository {
    suspend fun fetchProducts(): List<ProductItem>
    fun getLocalProducts(): Flow<List<ProductItem>>
    fun clearAndSaveAllProducts(items: List<ProductItem>)
}

class ProductRepository @Inject constructor(
    private val appService: AppService,
    private val productDao: ProductDao
) : IProductRepository {

    override suspend fun fetchProducts(): List<ProductItem> {
        return if (BuildConfig.DEBUG)
            ProductItemPreviewData.FakeListData // Todo replace with api
        else
            appService.fetchProducts().items
    }

    override fun getLocalProducts(): Flow<List<ProductItem>> {
        return productDao.getAll()
    }

    override fun clearAndSaveAllProducts(items: List<ProductItem>) {
        productDao.deleteAll()
        productDao.insertAll(items)
    }

}
