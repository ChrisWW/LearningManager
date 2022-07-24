package com.example.learningmanager.base.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

abstract class BaseFlowOneParameterUseCase <O, P, R>(protected val defaultContext: CoroutineContext = Dispatchers.Main) {
    protected abstract fun create(name: O, params: P): Flow<R>
    open fun build(name: O, params: P): Flow<R> = create(name, params)
}