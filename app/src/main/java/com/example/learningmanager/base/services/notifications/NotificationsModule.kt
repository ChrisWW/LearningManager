package com.example.learningmanager.base.services.notifications

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NotificationsModule {
    @Binds
    @Singleton
    abstract fun bindNotificationTokenProvider(firebaseTokenManager: FirebaseNotificationsTokenManager): NotificationsTokenProvider

    @Binds
    @Singleton
    abstract fun bindNotificationTokenConsumer(firebaseTokenManager: FirebaseNotificationsTokenManager): NotificationTokenConsumer
}