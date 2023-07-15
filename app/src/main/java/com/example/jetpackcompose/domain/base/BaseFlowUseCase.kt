package com.example.jetpackcompose.domain.base

import kotlinx.coroutines.flow.Flow

abstract class BaseFlowUseCase<in I, O> {

    abstract fun fetchRemote(param: I): Flow<O>
    abstract fun getLocal(param: I): Flow<O>
}
