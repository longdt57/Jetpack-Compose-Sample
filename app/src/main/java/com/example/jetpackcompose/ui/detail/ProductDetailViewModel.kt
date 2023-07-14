package com.example.jetpackcompose.ui.detail

import com.example.jetpackcompose.data.model.ProductItem
import com.example.jetpackcompose.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor() : BaseViewModel() {

    fun addToCard(item: ProductItem) {
        // Todo implement
    }
}