package com.notificationservicetest

import android.app.NotificationManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.notificationservicetest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        private const val NOTIFICATION_PERMISSION =
                                            "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"
        private const val TABLE_NAME = "enabled_notification_listeners"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var notificationCreator: NotificationCreator
    private lateinit var manager: NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        manager = getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager
        notificationCreator = NotificationCreator(this)
        handleButtons()
    }

    override fun onResume() {
        super.onResume()
        updateTextPermissionColor(checkNotificationListenerServiceAccess())
    }

    /**
     * Handles click events on buttons.
     */
    private fun handleButtons() {
        binding.buttonRequestAccess.setOnClickListener {
            val intentAccess = Intent(NOTIFICATION_PERMISSION)
            startActivity(intentAccess)
        }
        binding.buttonNotification.setOnClickListener { notificationCreator.createNotification() }
    }

    /**
     * Checks if application has access to the notification settings.
     * @return : access status
     */
    private fun checkNotificationListenerServiceAccess(): Boolean {
        // Get component "NotificationListenerCustom" service
        val componentService = ComponentName(this, NotificationListenerCustom::class.java)

        // Gets applications using notification settings access
        // getString returns format : [package name]/[Component name]
        val apps = Settings.Secure.getString(contentResolver, TABLE_NAME)

        return apps.contains(componentService.flattenToString())
    }

    /**
     * Updates color text.
     */
    @Suppress("Deprecation")
    private fun updateTextPermissionColor(status: Boolean) {
        fun updateWithResources(@ColorRes color: Int, @StringRes text: Int) {
            binding.textPermission.apply {
                setText(text)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    setTextColor(resources.getColor(color, null))
                else setTextColor(resources.getColor(color))
            }
        }
        if (status) updateWithResources(R.color.green, R.string.notification_access_granted)
        else updateWithResources(R.color.red, R.string.notification_access_denied)
    }
}