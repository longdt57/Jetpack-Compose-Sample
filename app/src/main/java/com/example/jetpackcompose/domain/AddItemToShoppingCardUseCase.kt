package com.example.jetpackcompose.domain

import com.example.jetpackcompose.data.model.CardItem
import com.example.jetpackcompose.data.model.ProductItem
import com.example.jetpackcompose.data.repository.IProductCardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddItemToShoppingCardUseCase @Inject constructor(
    private val repository: IProductCardRepository,
) {

    fun addItemToCard(item: ProductItem): Flow<CardItem> = flow {
        val cardItems = repository.getAllCardItems()
            .firstOrNull { it.itemId == item.name }
            ?: CardItem(item.name, 0)
        val addItem = cardItems.copy(count = cardItems.count + 1)
        repository.insertCardItem(addItem)
        emit(addItem)
    }
}
