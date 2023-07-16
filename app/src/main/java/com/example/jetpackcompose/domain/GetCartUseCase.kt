package com.example.jetpackcompose.domain

import com.example.jetpackcompose.data.repository.ICartRepository
import com.example.jetpackcompose.data.repository.IProductRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCartUseCase @Inject constructor(
    private val cartRepository: ICartRepository,
    private val productRepository: IProductRepository,
) {

    operator fun invoke() = flow {
        val cartItems = cartRepository.getAllCartItems()
        val cartItemIds = cartItems.map { it.itemId }
        val productItems = productRepository.getLocalProducts(cartItemIds)
        emit(Pair(cartItems, productItems))
    }
}
