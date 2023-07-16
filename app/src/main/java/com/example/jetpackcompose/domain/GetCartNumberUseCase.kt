package com.example.jetpackcompose.domain

import com.example.jetpackcompose.data.repository.ICartRepository
import javax.inject.Inject

class GetCartNumberUseCase @Inject constructor(private val repository: ICartRepository) {

    operator fun invoke(): Int {
        return repository.getAllCartItems().sumOf { it.count }
    }
}
