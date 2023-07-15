package com.example.jetpackcompose.domain

import androidx.annotation.VisibleForTesting
import com.example.jetpackcompose.data.model.ProductItem
import com.example.jetpackcompose.data.repository.IProductRepository
import com.example.jetpackcompose.domain.base.BaseFlowUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take

class GetProductUseCase @Inject constructor(
    private val productRepository: IProductRepository
) : BaseFlowUseCase<Unit, List<ProductItem>>() {

    override fun fetchRemote(param: Unit): Flow<List<ProductItem>> {
        return flow {
            productRepository.fetchProducts().let {
                saveLocal(it)
                emit(it)
            }
        }
    }

    override fun getLocal(param: Unit): Flow<List<ProductItem>> {
        return productRepository.getLocalProducts()
    }

    private fun saveLocal(items: List<ProductItem>) {
        productRepository.clearAndSaveAllProducts(items)
    }
}