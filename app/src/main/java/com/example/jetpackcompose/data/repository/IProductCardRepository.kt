package com.example.jetpackcompose.data.repository

import com.example.jetpackcompose.data.database.ProductCardDao
import com.example.jetpackcompose.data.database.ProductDao
import com.example.jetpackcompose.data.model.CardAddedItem
import com.example.jetpackcompose.data.model.ProductItem
import javax.inject.Inject

interface IProductCardRepository {

    fun getLocalProducts(ids: List<String>): List<ProductItem>
    fun addItemToCard(item: CardAddedItem)
    fun getCardAddedItems(): List<CardAddedItem>
}

class ProductCardRepository @Inject constructor(
    private val productDao: ProductDao,
    private val productCardDao: ProductCardDao
) : IProductCardRepository {

    override fun getLocalProducts(ids: List<String>): List<ProductItem> {
        return productDao.getItemByIds(ids)
    }

    override fun addItemToCard(item: CardAddedItem) {
        productCardDao.insert(item)
    }

    override fun getCardAddedItems(): List<CardAddedItem> {
        return productCardDao.getAll()
    }
}