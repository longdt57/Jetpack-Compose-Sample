package com.example.jetpackcompose.domain

import com.example.jetpackcompose.data.model.CartItem
import com.example.jetpackcompose.data.model.ProductItem
import com.example.jetpackcompose.data.repository.ICartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddItemToCartUseCase @Inject constructor(
    private val repository: ICartRepository,
) {

    operator fun invoke(item: ProductItem): Flow<CartItem> = flow {
        val cartItems = repository.getAllCartItems()
            .firstOrNull { it.itemId == item.name }
            ?: CartItem(item.name, 0)
        val addItem = cartItems.copy(count = cartItems.count + 1)
        repository.insertCartItem(addItem)
        emit(addItem)
    }
}
