package com.notificationservicetest

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

/**
 * Handles notification creation.
 */
class NotificationCreator(private val context: Context) {

    companion object {
        private const val CHANNEL_ID = "CHANNEL_ID"
        private const val CHANNEL_NAME = "CHANNEL_NAME"
        private const val CHANNEL_DESCRIPTION = "CHANNEL_DESCRIPTION"
    }
    private val manager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE)
                                               as NotificationManager

    /**
     * Creates a new notification.
     */
    fun createNotification() {
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, CHANNEL_ID)
                              .setSmallIcon(R.drawable.ic_launcher_background)
                              .setContentTitle(context.resources.getString(R.string.notification_title))
                              .setContentText(context.resources.getString(R.string.notification_message))
                              .setAutoCancel(true) // Notification is automatically canceled when user clicks
                                                   // it in the panel
        builder.priority = NotificationCompat.PRIORITY_DEFAULT
        // For API < 26 : Define notification importance with "setPriority()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            builder.priority = NotificationCompat.PRIORITY_DEFAULT
        // For API ≥ 26 : Define notification importance with a notification channel
        else createChannel()

        manager.notify(0, builder.build())
    }

    /**
     * Defines a notification channel for devices with API ≥ 26, for notifications with a
     * default priority.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val channel = NotificationChannel(CHANNEL_ID,
                                          CHANNEL_NAME,
                                          NotificationManager.IMPORTANCE_DEFAULT).also {
            it.description = CHANNEL_DESCRIPTION
        }
        manager.createNotificationChannel(channel)
    }
}