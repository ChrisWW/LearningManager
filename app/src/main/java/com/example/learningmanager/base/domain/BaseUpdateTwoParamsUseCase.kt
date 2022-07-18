package com.example.learningmanager.base.domain

abstract class BaseUpdateTwoParamsUseCase<O, P, R> {
    protected abstract suspend fun create(id: O, params: P): R
    open suspend fun build(id: O, params: P): R = create(id, params)
}