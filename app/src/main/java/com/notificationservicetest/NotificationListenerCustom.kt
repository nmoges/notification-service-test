package com.notificationservicetest

import android.content.Intent
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log

class NotificationListenerCustom: NotificationListenerService() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("NOTIFICATION", "onStartCommand")
        return START_STICKY
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        Log.d("NOTIFICATION", "NOTIFICATION POSTED")
        sbn?.let { displayOnLogNotificationInfo(it) }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        super.onNotificationRemoved(sbn)
        Log.d("NOTIFICATION", "NOTIFICATION REMOVED")
    }

    private fun displayOnLogNotificationInfo(sbn: StatusBarNotification) {
        Log.i("NOTIFICATION_SERVICE", "--------------------------------------------------")
        Log.i("NOTIFICATION_SERVICE", "Package name : ${sbn.packageName}")
        Log.i("NOTIFICATION_SERVICE", "Post time : ${sbn.postTime}")
        Log.i("NOTIFICATION_SERVICE", "Tag : ${sbn.tag}")
        val notification = sbn.notification
        Log.i("NOTIFICATION_SERVICE", "Title : ${notification.extras
                                                          .getCharSequence("android.title")}")
        Log.i("NOTIFICATION_SERVICE", "Title : ${notification.extras
                                                          .getCharSequence("android.text")}")
    }
}