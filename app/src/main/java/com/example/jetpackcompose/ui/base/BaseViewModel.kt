package com.example.jetpackcompose.ui.base

import androidx.lifecycle.ViewModel
import com.example.jetpackcompose.data.network.base.error.getApiError
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

open class BaseViewModel : ViewModel() {

    protected val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading.asStateFlow()

    protected val _error = MutableSharedFlow<String>(
        extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val error: SharedFlow<String>
        get() = _error.asSharedFlow()

    protected open suspend fun handleError(error: Throwable) {
        _error.emit(error.getApiError().getErrorMessage())
    }
}