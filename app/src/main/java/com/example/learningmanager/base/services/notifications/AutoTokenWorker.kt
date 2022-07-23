package com.example.learningmanager.base.services.notifications

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltWorker
class AutoTokenWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
//    private val getNotificationTokenUseCase: GetNotificationTokenUseCase,
//    private val authSharedContext: AuthSharedContext,
//    private val notifyBackendTokenChangedUseCase: NotifyBackendTokenChangedUseCase,

    ) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {
//        if (!isPost) {
//            isPost = true
//        } else {
//            consumeNotificationsIfUserSignedIn()
//        }
        return Result.success()
    }
//
//
//    fun consumeNotificationsIfUserSignedIn() {
//        authSharedContext.isUserSignedIn
//            .filter { it }
//            .apply {
//                getLastNotificationToken()
//            }
//            .launchIn(CoroutineScope(Dispatchers.Main))
//    }
//
//    fun getLastNotificationToken() {
//        getNotificationTokenUseCase.build(Unit)
//            .onEach { notifyNotificationTokenChanged(it) }
//            .launchIn(CoroutineScope(Dispatchers.Main))
//    }
//
//    fun notifyNotificationTokenChanged(token: String) {
//        val params = NotifyBackendTokenChangedUseCase.Params(token)
//        notifyBackendTokenChangedUseCase.buildWithState(params)
//            .launchIn(CoroutineScope(Dispatchers.Main))
//    }
//
//    companion object {
//        var isPost = false
//    }
}
