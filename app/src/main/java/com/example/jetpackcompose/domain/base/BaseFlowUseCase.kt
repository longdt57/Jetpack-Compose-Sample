package com.example.jetpackcompose.domain.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll

abstract class BaseFlowUseCase<in I, O> {

    fun fetchRemoteFallbackLocal(param: I) = fetchRemote(param).catch {
        emitAll(getLocal(param))
    }

    abstract fun fetchRemote(param: I): Flow<O>
    abstract fun getLocal(param: I): Flow<O>
}
