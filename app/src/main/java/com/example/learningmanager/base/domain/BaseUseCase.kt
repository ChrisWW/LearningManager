package com.example.learningmanager.base.domain

abstract class BaseUseCase<P, R> {
    protected abstract suspend fun create(params: P): R
    open suspend fun build(params: P): R = create(params)
}