package com.example.jetpackcompose.ui.cart

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose.di.DispatcherModule
import com.example.jetpackcompose.domain.GetCartUseCase
import com.example.jetpackcompose.ui.base.BaseViewModel
import com.example.jetpackcompose.ui.ext.toShoppingItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getShoppingCartUseCase: GetCartUseCase,
    @DispatcherModule.IODispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : BaseViewModel() {

    private val _items = MutableStateFlow<List<ShoppingItem>>(emptyList())
    val items: StateFlow<List<ShoppingItem>> get() = _items.asStateFlow()

    init {
        getCartItems()
    }

    @VisibleForTesting
    fun getCartItems() {
        viewModelScope.launch {
            getShoppingCartUseCase.invoke()
                .flowOn(ioDispatcher)
                .catch { handleError(it) }
                .map { it.toShoppingItems() }
                .collect { items ->
                    _items.update { items }
                }
        }
    }
}
