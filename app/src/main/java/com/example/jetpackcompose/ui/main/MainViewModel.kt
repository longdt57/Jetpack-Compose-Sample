package com.example.jetpackcompose.ui.main

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose.data.model.ProductItem
import com.example.jetpackcompose.di.DispatcherModule
import com.example.jetpackcompose.domain.GetCartNumberUseCase
import com.example.jetpackcompose.domain.GetProductUseCase
import com.example.jetpackcompose.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
    private val getCartNumberUseCase: GetCartNumberUseCase,
    @DispatcherModule.IODispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : BaseViewModel() {

    private val _items = MutableStateFlow<List<ProductItem>>(emptyList())
    val items: StateFlow<List<ProductItem>> get() = _items.asStateFlow()

    private val _cartNumber = MutableStateFlow(0)
    val cartNumber: StateFlow<Int> get() = _cartNumber.asStateFlow()

    init {
        observeLocal()
    }

    fun refreshData() {
        viewModelScope.launch {
            getProductUseCase.fetchRemote(Unit)
                .onStart { _loading.update { true } }
                .flowOn(ioDispatcher)
                .catch {
                    handleError(it)
                    _loading.update { false }
                }
                .onCompletion { _loading.update { false } }
                .collect()
        }
    }

    fun refreshCartNumber() {
        viewModelScope.launch(ioDispatcher) {
            _cartNumber.update {
                getCartNumberUseCase.invoke()
            }
        }
    }

    @VisibleForTesting
    fun observeLocal() {
        viewModelScope.launch {
            getProductUseCase.getLocal(Unit)
                .flowOn(ioDispatcher)
                .catch { handleError(it) }
                .collect { value ->
                    _items.update { value }
                }
        }
    }
}
