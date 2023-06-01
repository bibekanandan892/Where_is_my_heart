package com.petpack.whereismyheart.utils

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.petpack.whereismyheart.MainActivity
import com.petpack.whereismyheart.R
import com.petpack.whereismyheart.data.local.ChatsDao
import com.petpack.whereismyheart.data.model.chat.MessageEntity
import com.petpack.whereismyheart.data.model.read_receipt.MessageIdResponse
import com.petpack.whereismyheart.domain.usecase.UseCases
import com.petpack.whereismyheart.utils.Constants.WIMH
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class AppFirebaseMessagingService() : FirebaseMessagingService() {
    @Inject
    lateinit var chatsDao: ChatsDao
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    val json = Json {
        ignoreUnknownKeys = true
    }

    @Inject
    lateinit var useCases: UseCases

    // Override onNewToken to get new token
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    private val channelId = "message"
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Second case when notification payload is
        // received.
        remoteMessage.data["isDisconnectRequest"]?.let {
            clearConnectedHeartRequest()
            coroutineScope.launch {
                chatsDao.deleteAll()
            }
            showNotification(
                it, "User Disconnected"
            )

        }
        val readReceiptMessage = remoteMessage.data["receiptMessageIdResponse"]
        if (readReceiptMessage != null) {
            val messageIdResponse = json.decodeFromString<MessageIdResponse>(readReceiptMessage)
            coroutineScope.launch {
                messageIdResponse.messageIdList?.forEach { timeStamp ->
                    timeStamp?.let {
                        val messageEntityList =
                            chatsDao.getMessagesByRecipient(timestamp = it, isMine = true)
                        messageEntityList.forEach { messageEntity ->
                            messageEntity.messageStatus = "R"
                            chatsDao.update(messageEntity = messageEntity)
                        }
                    }
                }
            }
        } else {
            remoteMessage.data["title"]?.let {
                remoteMessage.data["body"]?.let { it1 ->

                    val messageEntity = json.decodeFromString<MessageEntity>(it1)
                    coroutineScope.launch {
                        chatsDao.insertChat(messageEntity = messageEntity)
                    }
                    showNotification(
                        it, messageEntity.message
                    )
                }
            }
        }


    }


    @SuppressLint("RemoteViewLayout")
    private fun getCustomDesign(
        title: String,
        message: String,
    ): RemoteViews {
        val remoteViews = RemoteViews(
            applicationContext.packageName,
            R.layout.notification
        )
        remoteViews.setTextViewText(R.id.title, title)
        remoteViews.setTextViewText(R.id.message, message)
        return remoteViews
    }


    private fun showNotification(
        title: String,
        message: String,
    ) {
        // Pass the intent to switch to the MainActivity
        val intent = Intent(this, MainActivity::class.java)
        // Assign channel ID
        // Here FLAG_ACTIVITY_CLEAR_TOP flag is set to clear
        // the activities present in the activity stack,
        // on the top of the Activity that is to be launched
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        // Pass the intent to PendingIntent to start the
        // next Activity
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )


        // Create a Builder object using NotificationCompat
        // class. This will allow control over all the flags
        var builder: NotificationCompat.Builder = NotificationCompat.Builder(
            this, channelId
        ).setSmallIcon(R.drawable.ic_jetchat).setAutoCancel(true).setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        // A customized design for the notification can be
        // set only for Android versions 4.1 and above. Thus
        // condition for the same is checked here.
        builder = builder.setContent(
            getCustomDesign(title, message)
        ).setContentTitle(title)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentText(message)
        // Create an object of NotificationManager class to
        // notify the
        // user of events that happen in the background.
        val notificationManager =
            ContextCompat.getSystemService(this, NotificationManager::class.java)
        // Check if the Android Version is greater than Oreo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId, "heart", NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager!!.createNotificationChannel(
                notificationChannel
            )
        }
        notificationManager!!.notify(Random().nextInt(), builder.build())
    }

    private fun clearConnectedHeartRequest() {
//        useCases.saveJwtTokenUseCase.invoke(token = "")
        useCases.saveConnectedHeartIdUseCase.invoke("")
        useCases.saveConnectedPhotoUseCase.invoke("")
        useCases.saveConnectedUserEmailUseCase.invoke("")
        useCases.saveConnectedUserNameUseCase.invoke("")
    }

}