package com.example.jetpackcompose.ui.card

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose.di.DispatcherModule
import com.example.jetpackcompose.domain.GetShoppingCardUseCase
import com.example.jetpackcompose.ui.base.BaseViewModel
import com.example.jetpackcompose.ui.ext.toShoppingItems
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
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

@HiltViewModel
class ShoppingCardViewModel @Inject constructor(
    private val getShoppingCardUseCase: GetShoppingCardUseCase,
    @DispatcherModule.IODispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel() {

    private val _items = MutableStateFlow<List<ShoppingItem>>(emptyList())
    val items: StateFlow<List<ShoppingItem>> get() = _items.asStateFlow()

    init {
        getCardItems()
    }

    @VisibleForTesting
    fun getCardItems() {
        viewModelScope.launch {
            getShoppingCardUseCase.getCardItems()
                .flowOn(ioDispatcher)
                .catch { handleError(it) }
                .map { it.toShoppingItems() }
                .collect { items ->
                    _items.update { items }
                }
        }
    }

}