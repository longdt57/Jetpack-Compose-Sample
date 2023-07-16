package com.example.jetpackcompose.data.repository

import com.example.jetpackcompose.data.database.ProductCardDao
import com.example.jetpackcompose.data.database.ProductDao
import com.example.jetpackcompose.data.model.CardItem
import com.example.jetpackcompose.data.model.ProductItem
import javax.inject.Inject

interface IProductCardRepository {

    fun getLocalProducts(ids: List<String>): List<ProductItem>
    fun insertCardItem(item: CardItem)
    fun getAllCardItems(): List<CardItem>
}

class ProductCardRepository @Inject constructor(
    private val productDao: ProductDao,
    private val productCardDao: ProductCardDao,
) : IProductCardRepository {

    override fun getLocalProducts(ids: List<String>): List<ProductItem> {
        return productDao.getItemByIds(ids)
    }

    override fun insertCardItem(item: CardItem) {
        productCardDao.insert(item)
    }

    override fun getAllCardItems(): List<CardItem> {
        return productCardDao.getAll()
    }
}
