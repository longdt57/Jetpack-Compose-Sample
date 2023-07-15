package com.example.jetpackcompose.domain

import com.example.jetpackcompose.data.model.CardAddedItem
import com.example.jetpackcompose.data.model.ProductItem
import com.example.jetpackcompose.data.repository.IProductCardRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddItemToShoppingCardUseCase @Inject constructor(
    private val repository: IProductCardRepository
) {

    fun addItemToCard(item: ProductItem): Flow<Unit> = flow {
        val cardItem = repository.getCardAddedItems()
            .firstOrNull { it.itemId == item.name }
            ?: CardAddedItem(item.name, 0)
        val addItem = cardItem.copy(count = cardItem.count + 1)
        emit(repository.addItemToCard(addItem))
    }
}
