package com.example.learningmanager.base.services.notifications

interface NotificationTokenConsumer {
    suspend fun consumeToken(token: String)
}