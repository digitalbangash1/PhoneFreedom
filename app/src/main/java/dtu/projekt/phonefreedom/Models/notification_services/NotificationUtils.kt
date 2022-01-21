package dtu.projekt.phonefreedom.Models.notification_services

import android.service.notification.StatusBarNotification
import android.os.Parcelable
import dtu.projekt.phonefreedom.Models.notification_services.NotificationUtils
import dtu.projekt.phonefreedom.Models.notification_services.NotificationWear
import android.app.PendingIntent
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import java.util.*

object NotificationUtils {
    // Do not reply to notifications whose timestamp is older than 2 minutes
    private const val MAX_OLD_NOTIFICATION_CAN_BE_REPLIED_TIME_MS = 2 * 60 * 1000
    @JvmStatic
    fun getTitle(sbn: StatusBarNotification): String? {
        var title: String?
        if (sbn.notification.extras.getBoolean("android.isGroupConversation")) {
            title = sbn.notification.extras.getString("android.hiddenConversationTitle")
            //Just to avoid null cases, if by any chance hiddenConversationTitle comes null for group message
            // then extract group name from title
            if (title == null) {
                title = sbn.notification.extras.getString("android.title")
                val index = title!!.indexOf(':')
                if (index != -1) {
                    title = title.substring(0, index)
                }
            }

            //To eliminate the case where group title has number of messages count in it
            val b = sbn.notification.extras["android.messages"] as Array<Parcelable>?
            if (b != null && b.size > 1) {
                val startIndex = title!!.lastIndexOf('(')
                if (startIndex != -1) {
                    title = title.substring(0, startIndex)
                }
            }
        } else {
            title = sbn.notification.extras.getString("android.title")
        }
        return title
    }

    /*
   This method is used to avoid replying to unreplied notifications
   which are posted again when next message is received
    */
    @JvmStatic
    fun isNewNotification(sbn: StatusBarNotification): Boolean {
        //For apps targeting {@link android.os.Build.VERSION_CODES#N} and above, this time is not shown
        //by default unless explicitly set by the apps hence checking not 0
        return sbn.notification.`when` == 0L ||
                System.currentTimeMillis() - sbn.notification.`when` < MAX_OLD_NOTIFICATION_CAN_BE_REPLIED_TIME_MS
    }

    /**
     * Extract WearNotification with RemoteInputs that can be used to send a response
     */
    @JvmStatic
    fun extractWearNotification(statusBarNotification: StatusBarNotification): NotificationWear {
        //Should work for communicators such:"com.whatsapp", "com.facebook.orca", "com.google.android.talk", "jp.naver.line.android", "org.telegram.messenger"
        val wearableExtender =
            NotificationCompat.WearableExtender(statusBarNotification.notification)
        val actions = wearableExtender.actions
        val remoteInputs: MutableList<RemoteInput> = ArrayList(actions.size)
        var pendingIntent: PendingIntent? = null
        for (act in actions) {
            if (act != null && act.remoteInputs != null) {
                for (x in act.remoteInputs!!.indices) {
                    val remoteInput = act.remoteInputs!![x]
                    remoteInputs.add(remoteInput)
                    pendingIntent = act.actionIntent
                }
            }
        }
        return NotificationWear(
            statusBarNotification.packageName,
            pendingIntent,
            remoteInputs,
            statusBarNotification.notification.extras,
            statusBarNotification.tag,
            UUID.randomUUID().toString()
        )
    }

    @JvmStatic
    fun getTitleRaw(sbn: StatusBarNotification): String? {
        return sbn.notification.extras.getString("android.title")
    }
}