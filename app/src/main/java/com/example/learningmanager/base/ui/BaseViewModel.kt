package com.example.learningmanager.base.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learningmanager.base.navigation.Navigator
import com.example.learningmanager.base.navigation.ScreenKey
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel(), Navigator {

    private val destination = MutableSharedFlow<ScreenKey>()
    val navDestination = destination.asSharedFlow()

    private val shouldNavigationBack = MutableSharedFlow<Unit>()
    val shouldNavBack = shouldNavigationBack.asSharedFlow()

    private val _onAlertEvent = MutableSharedFlow<Alert>()
    val onAlertEvent = _onAlertEvent.asSharedFlow()

    private val _onLoading = MutableStateFlow(false)
    val shouldShowProgressDialog = _onLoading.filter { it }
    val shouldHideProgressDialog = _onLoading.filter { it }

    override fun navigateTo(key: ScreenKey) {
        viewModelScope.launch {
            destination.emit(key)
        }
    }

    override fun navigateBack() {
        viewModelScope.launch {
            shouldNavigationBack.emit(Unit)
        }
    }

    protected fun showAlert(alert: Alert) {
        viewModelScope.launch {
            _onAlertEvent.emit(alert)
        }
    }

    protected fun <T> Flow<T>.doToggleOnLoading() =
        onStart { _onLoading.value = true }
            .onCompletion { _onLoading.value = false }
}