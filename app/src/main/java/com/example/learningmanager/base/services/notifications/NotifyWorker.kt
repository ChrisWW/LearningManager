package com.example.learningmanager.base.services.notifications

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject


@HiltWorker
class NotifyWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val notificationsService: NotificationsService
) :
    CoroutineWorker(appContext, workerParams) {
// getActivity().getApplciationConetxt()
    override suspend fun doWork(): Result {
        Log.d("notificationservice", "DoWOrk works before inputString")
        val inputString = inputData.getString("NotificationString")
            ?: return Result.failure()

        sendNotification("Your goals need progress.", inputString)
        Log.d("notificationservice", "DoWOrk works")
        return Result.success()
    }

    private fun sendNotification(title: String, description: String) {
        notificationsService.createNotificationChannel()
        val notification = notificationsService.buildNotification(title, description)
        notificationsService.showNotification(notification)
        Log.d("notificationservice", "AfterShowNotification")
    }

}

//@HiltWorker
//class NotifyWorker @AssistedInject constructor(
//    @Assisted appContext: Context,
//    @Assisted workerParams: WorkerParameters,
//    private val notificationsService: NotificationsService
//) :
//    CoroutineWorker(appContext, workerParams) {
//
//    override suspend fun doWork(): Result {
//        Log.d("notificationservice", "DoWOrk works before inputString")
//        val inputString = inputData.getString("NotificationString")
//            ?: return Result.failure()
//
//        sendNotification("Your goals need progress.", inputString)
//        Log.d("notificationservice", "DoWOrk works")
//        return Result.success()
//    }
//
//    private fun sendNotification(title: String, description: String) {
//        notificationsService.createNotificationChannel()
//        val notification = notificationsService.buildNotification(title, description)
//        notificationsService.showNotification(notification)
//        Log.d("notificationservice", "AfterShowNotification")
//    }
//}