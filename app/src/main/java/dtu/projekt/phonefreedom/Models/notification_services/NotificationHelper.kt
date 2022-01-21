package dtu.projekt.phonefreedom.Models.notification_services

import dtu.projekt.phonefreedom.Models.notification_services.NotificationHelper
import org.json.JSONException
import android.os.Build
import android.app.NotificationManager
import android.app.NotificationChannel
import android.content.Intent
import dtu.projekt.phonefreedom.Models.notification_services.NotificationIntentActivity
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import androidx.core.app.NotificationCompat
import dtu.projekt.phonefreedom.R
import org.json.JSONObject

/**
 * Notification helper
 * This class was taken from free opensource project on github(watomatic) and modified for use.
 * @property appContext
 * @constructor Create empty Notification helper
 */
class NotificationHelper private constructor(private val appContext: Context) {
    private fun setNotificationSummaryShown(packageName: String) {
        var packageName: String? = packageName
        if (packageName != null) {
            packageName = packageName.replace("watomatic-", "")
            try {
                appsList.put(packageName, true)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }

    fun markNotificationDismissed(packageName: String) {
        var packageName = packageName
        packageName = packageName.replace("watomatic-", "")
        try {
            appsList.put(packageName, false)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    fun getForegroundServiceNotification(service: Service?): NotificationCompat.Builder {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notificationChannel = NotificationChannel(
                Constants.NOTIFICATION_CHANNEL_ID,
                Constants.NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val intent = Intent(appContext, NotificationIntentActivity::class.java)
        intent.action =
            java.lang.Long.toString(System.currentTimeMillis()) //This is needed to generate unique pending intents, else when we create multiple pending intents they will be overwritten by last one
        val pIntent =
            PendingIntent.getActivity(appContext, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        val notificationBuilder: NotificationCompat.Builder
        notificationBuilder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NotificationCompat.Builder(
                service!!,
                Constants.NOTIFICATION_CHANNEL_ID
            ) //.setSmallIcon(R.drawable.ic_logo_full)
                .setContentTitle(appContext.getString(R.string.app_name))
                .setContentText(appContext.getString(R.string.running_in_the_background))
                .setPriority(NotificationManager.IMPORTANCE_HIGH)
                .setContentIntent(pIntent)
        } else {
            NotificationCompat.Builder(
                service!!,
                Constants.NOTIFICATION_CHANNEL_ID
            ) //.setSmallIcon(R.drawable.ic_logo_full)
                .setContentTitle(appContext.getString(R.string.app_name))
                .setContentText(appContext.getString(R.string.running_in_the_background))
                .setContentIntent(pIntent)
        }
        return notificationBuilder
    }

    companion object {
        private var _INSTANCE: NotificationHelper? = null
        private val notificationManager: NotificationManager? = null
        private val appsList = JSONObject()
        @JvmStatic
        fun getInstance(context: Context): NotificationHelper? {
            if (_INSTANCE == null) {
                _INSTANCE = NotificationHelper(context)
            }
            return _INSTANCE
        }
    }
}