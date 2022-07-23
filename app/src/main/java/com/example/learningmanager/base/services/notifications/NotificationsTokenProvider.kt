package com.example.learningmanager.base.services.notifications

import kotlinx.coroutines.flow.SharedFlow

interface NotificationsTokenProvider {
    suspend fun retrieveLastToken(): String?
    val onTokenChanged: SharedFlow<String>
}