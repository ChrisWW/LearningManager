package com.example.learningmanager.base.domain


import com.example.learningmanager.base.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext

// Use case that represents an API call. By default runs on Dispatchers.IO
abstract class BaseApiRequestUseCase<P, R>(defaultCoroutineContext: CoroutineContext = Dispatchers.IO) :
    BaseFlowUseCase<P, R>(defaultCoroutineContext) {

    fun buildWithState(params: P): Flow<Resource<R>> {
        return build(params)
            .map { onSuccessfullyReceivedResponse(it) }
            .onStart { emit(onRequestStarted()) }
//            .catch { emit(onErrorReceived(it)) }
            .flowOn(defaultContext)
    }

    protected open suspend fun onRequestStarted(): Resource<R> {
        return Resource.Loading()
    }

    protected open suspend fun onSuccessfullyReceivedResponse(response: R): Resource<R> {
        return Resource.Success(response)
    }

//    protected open suspend fun onErrorReceived(throwable: Throwable): Resource<R> {
//        FirebaseCrashlytics.getInstance().recordException(throwable)
//        val type = when (throwable) {
//            is NetworkNoConnectivityOrInternetException -> ErrorType.NO_INTERNET
//            else -> ErrorType.OTHER
//        }
//        return Resource.Error(type = type)
//    }
}