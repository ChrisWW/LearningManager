package com.example.learningmanager.base.services.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.learningmanager.MainActivity
import com.example.learningmanager.R
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class NotificationsService @Inject constructor(): FirebaseMessagingService(), CoroutineScope {
    @Inject
    lateinit var tokenConsumer: NotificationTokenConsumer

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    // TODO
    private val channelId: String
        get() = resources.getString(R.string.notification_channel_id)

    private val channelName: String
        get() = resources.getString(R.string.notification_channel_name)

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
        launch { tokenConsumer.consumeToken(newToken)
        Log.d("notificationtoken", "NEWTOKENNotification: ${newToken}")
        }
    }

    fun getTime(unixSeconds: Long): String {
        val date = Date(unixSeconds * 1000L)
        val simpleDateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return simpleDateFormat.format(date)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { Log.d("tokendisplay", "TOKEN notification: ${it.result}") }
        Log.d("onmessagereceived", "Message data payload BEFORE: ${message.data}")
            val title = message.notification?.title.toString()
            val content = message.notification?.body.toString()
            Log.d("onmessagereceived", "Message data payload: ${message.data}+ ALSO title: $title + also content: $content")
        // build notification
        val notification = buildNotification(title, content)
        showNotification(notification)

        // prepare and send data to WorkManager Service

//        val jsonMap = message.data.toMutableMap() ?: ""
//        val jsonMapString = Gson().toJson(jsonMap).toString()
//        val obj = Json { ignoreUnknownKeys = true }.decodeFromString<JsonDataParserNotification>(
//            jsonMapString
//        )
//        val time = getTime(BigDecimal(obj.expirationTime).toLong())
//        val title = obj.title
//        val content = "It expires at $time." ?: ""
//        val notification = buildNotification(title, content)
//        showNotification(notification)
//        val notificationData = NotificationItemsResponse.NotificationItemData(
//            obj.name + " " + obj.surname,
//            obj.product,
//            obj.creationTime,
//            obj.creationTime,
//            "SimpleText",
//            "information",
//            obj.leadId.toInt(),
//            isRead = false,
//            isArchived = false,
//            isAccepted = false
//        )
//        val sentStringData = Gson().toJson(notificationData)
//        val data: Data.Builder = Data.Builder()
//        data.putString("NotificationString", sentStringData)
//        val syncWorkRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java)
//            .setInputData(data.build())
//            .build()
//        WorkManager.getInstance(applicationContext).enqueue(syncWorkRequest)
    }

    fun buildNotification(title: String, content: String) =
        NotificationCompat.Builder(this, channelId).apply {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.putExtra("notification", getString(R.string.notification_value))
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            val pendingIntent = PendingIntent.getActivity(
                applicationContext,
                1,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            val titleBold: Spannable = SpannableString(title)
            titleBold.setSpan(
                StyleSpan(Typeface.BOLD),
                0,
                title.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSmallIcon(R.color.dark_grey) // TODO: replace with an actual icon
            setContentTitle(titleBold)
            setContentText(content)
            priority = NotificationCompat.PRIORITY_DEFAULT
            setContentIntent(pendingIntent)
            setAutoCancel(true)
        }.build()

    fun showNotification(notification: Notification) =
        with(NotificationManagerCompat.from(this)) {
            notify(NOTIFICATION_ID, notification)
            Log.d("notificationservice", "AfterShowNotification with ID: ${NOTIFICATION_ID}")
        }

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance)
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

//    override fun handleIntent(intent: Intent) {
//        try {
//            if (intent.extras != null) {
//                val builder = RemoteMessage.Builder("MessagingService")
//                onMessageReceived(builder.build())
//            } else {
//                super.handleIntent(intent)
//            }
//        } catch (e: Exception) {
//            super.handleIntent(intent)
//        }
//    }

    companion object {
        private const val NOTIFICATION_ID = 0
    }
}
