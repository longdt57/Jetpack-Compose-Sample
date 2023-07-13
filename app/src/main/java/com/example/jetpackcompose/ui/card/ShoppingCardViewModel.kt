package com.example.jetpackcompose.ui.card

import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose.data.model.ProductItem
import com.example.jetpackcompose.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ShoppingCardViewModel @Inject constructor() : BaseViewModel() {

    private val _items = MutableStateFlow<List<ShoppingProductItem>>(emptyList())
    val items: StateFlow<List<ShoppingProductItem>> get() = _items.asStateFlow()

    init {
        loadAddedItem()
    }

    private fun loadAddedItem() {
        viewModelScope.launch {
            _loading.update { true }
            // Todo load added item
            _loading.update { false }
        }
    }

    data class ShoppingProductItem(
        val item: ProductItem,
        val count: Int
    )
}