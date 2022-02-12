package com.example.learningmanager.base.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

abstract class BaseUpdateUseCase<P, R> {
    protected abstract suspend fun create(params: P): R
    open suspend fun build(params: P): R = create(params)
}