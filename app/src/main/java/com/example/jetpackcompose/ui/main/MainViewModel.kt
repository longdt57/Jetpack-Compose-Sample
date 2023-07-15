package com.example.jetpackcompose.ui.main

import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose.data.model.ProductItem
import com.example.jetpackcompose.data.network.base.error.getApiError
import com.example.jetpackcompose.di.DispatcherModule
import com.example.jetpackcompose.domain.GetProductUseCase
import com.example.jetpackcompose.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
    @DispatcherModule.IODispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel() {

    private val _items = MutableStateFlow<List<ProductItem>>(emptyList())
    val items: StateFlow<List<ProductItem>> get() = _items.asStateFlow()

    fun refreshData() {
        viewModelScope.launch {
            _loading.update { true }
            getProductUseCase.fetchRemoteFallbackLocal(Unit)
                .flowOn(ioDispatcher)
                .catch { _error.emit(it.getApiError().getErrorMessage()) }
                .collect { value ->
                    _items.update { value }
                }
            _loading.update { false }
        }
    }
}