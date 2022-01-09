package com.example.learningmanager.base.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.learningmanager.base.utils.error.ErrorType
import com.example.learningmanager.base.utils.Resource
import kotlinx.coroutines.flow.*

// Returns a lifecycle-aware flow and invokes the gives action before each value of the upstream
// is emitted downstream
fun <T> Flow<T>.collectWith(owner: LifecycleOwner, action: suspend (T) -> Unit) {
    flowWithLifecycle(owner.lifecycle)
        .onEach(action)
        .launchIn(owner.lifecycleScope)
}

// Returns a flow that emits only a single value
fun <T> flowSingle(block: suspend () -> T): Flow<T> = flow { emit(block()) }

// Returns a flow that invokes the given action (only if the value is success)
// before each value of the upstream flow is emitted downstream
fun <R> Flow<Resource<R>>.withSuccess(action: suspend (R) -> Unit) =
    onEach { if (it is Resource.Success) action(it.data!!) }

// Returns a flow that invokes the given action (only if the value is an error)
// before each value of the upstream flow is emitted downstream
fun <R> Flow<Resource<R>>.withError(action: suspend (ErrorType) -> Unit) =
    onEach { if (it is Resource.Error) action(it.type) }

// Returns a flow containing only success values and applies the given transform
// function to each value
fun <R, T> Flow<Resource<R>>.filterSuccessMap(transform: suspend (R) -> T) =
    filter { it is Resource.Success }
        .map { (it as Resource.Success).data!! }
        .map(transform)

// Returns a flow containing only error values and applies the given transform
// function to each value
fun <R, T> Flow<Resource<R>>.filterErrorMap(transform: suspend (ErrorType) -> T) =
    filter { it is Resource.Error }
        .map { (it as Resource.Error).type }
        .map(transform)