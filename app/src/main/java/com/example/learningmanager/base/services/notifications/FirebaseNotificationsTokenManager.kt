package com.example.learningmanager.base.services.notifications

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Singleton
class FirebaseNotificationsTokenManager @Inject constructor() : NotificationsTokenProvider,
    NotificationTokenConsumer {

    private val _onTokenChanged = MutableSharedFlow<String>()
    override val onTokenChanged: SharedFlow<String>
        get() = _onTokenChanged

    override suspend fun retrieveLastToken(): String? = suspendCoroutine {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                it.resume(null)
            }
            val token = task.result
            it.resume(token)
            Log.d("notificationtoken", "TOKENNotification: ${token}")
        }
    }

    override suspend fun consumeToken(token: String) {
        _onTokenChanged.emit(token)
        Log.d("notificationtoken", "ConsumeTOKENNotification: ${token}")
    }
}