package com.example.jetpackcompose.ui.main

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
class MainViewModel @Inject constructor() : BaseViewModel() {

    private val _items = MutableStateFlow<List<ProductItem>>(emptyList())
    val items: StateFlow<List<ProductItem>> get() = _items.asStateFlow()

    fun refreshData() {
        viewModelScope.launch {
            _loading.update { true }
            // Todo load data
            _loading.update { false }
        }
    }
}