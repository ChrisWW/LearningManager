package com.example.learningmanager.base.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

abstract class BaseFlowUseCase<P, R>(protected val defaultContext: CoroutineContext = Dispatchers.Main) {
    protected abstract fun create(params: P): Flow<R>
    open fun build(params: P): Flow<R> = create(params)
}