package dtu.projekt.phonefreedom.notification_services

import dtu.projekt.phonefreedom.notification_services.NotificationUtils.isNewNotification
import dtu.projekt.phonefreedom.notification_services.NotificationUtils.extractWearNotification
import dtu.projekt.phonefreedom.notification_services.NotificationUtils.getTitle
import dtu.projekt.phonefreedom.notification_services.NotificationUtils.getTitleRaw
import android.service.notification.NotificationListenerService
import dtu.projekt.phonefreedom.notification_services.NotificationService
import android.service.notification.StatusBarNotification
import dtu.projekt.phonefreedom.notification_services.NotificationUtils
import dtu.projekt.phonefreedom.notification_services.PreferencesManager
import android.content.Intent
import dtu.projekt.phonefreedom.notification_services.NotificationWear
import android.os.Bundle
import android.app.PendingIntent.CanceledException
import android.text.SpannableString
import android.util.Log
import androidx.core.app.RemoteInput

class NotificationService : NotificationListenerService() {
    private val TAG = NotificationService::class.java.simpleName

    //private DbUtils dbUtils;
    override fun onNotificationPosted(sbn: StatusBarNotification) {
        super.onNotificationPosted(sbn)

        /*  //TEST only
        PreferencesManager prefs = PreferencesManager.getPreferencesInstance(this);
        prefs.setWhatsAppEnabled(true);*/if (canReply(sbn) && shouldReply(sbn)) {
            sendReply(sbn)
        }
    }

    private fun canReply(sbn: StatusBarNotification): Boolean {
        return isServiceEnabled &&
                isSupportedPackage(sbn) &&
                isNewNotification(sbn) &&
                isGroupMessageAndReplyAllowed(sbn) &&
                canSendReplyNow(sbn)
    }

    private fun shouldReply(sbn: StatusBarNotification): Boolean {
        val prefs = PreferencesManager.getPreferencesInstance(this)
        val isGroup = sbn.notification.extras.getBoolean("android.isGroupConversation")
        return if (isGroup && !prefs.isGroupReplyEnabled) {
            false
        } else true

//        boolean isContactReplyEnabled = true;
//        //Check contact based replies
//        if (isContactReplyEnabled && !isGroup) {
//            //Title contains sender name (at least on WhatsApp)
//            String senderName = sbn.getNotification().extras.getString("android.title");
//            //Check if should reply to contact
//            boolean isNameSelected =
//                    (ContactsHelper.getInstance(this).hasContactPermission()
//                            && prefs.getReplyToNames().contains(senderName)) ||
//                            prefs.getCustomReplyNames().contains(senderName);
//            if ((isNameSelected && prefs.isContactReplyBlacklistMode()) ||
//                    !isNameSelected && !prefs.isContactReplyBlacklistMode()) {
//                //If contact is on the list and contact reply is on blacklist mode,
//                // or contact is not in the list and reply is on whitelist mode,
//                // we don't want to reply
//                return false;
//            }
//        }

        //Check more conditions on future feature implementations

        //If we got here, all conditions to reply are met
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        //START_STICKY  to order the system to restart your service as soon as possible when it was killed.
        return START_STICKY
    }

    private fun sendReply(sbn: StatusBarNotification) {
        val prefs = PreferencesManager.getPreferencesInstance(this)
        val (_, pendingIntent, remoteInputs1) = extractWearNotification(sbn)
        // Possibly transient or non-user notification from WhatsApp like
        // "Checking for new messages" or "WhatsApp web is Active"
        if (remoteInputs1.isEmpty()) {
            return
        }

        //customRepliesData = CustomRepliesData.getInstance(this);
        val myText = prefs.autoReplyText
        val remoteInputs = arrayOfNulls<RemoteInput>(remoteInputs1.size)
        val localIntent = Intent()
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val localBundle = Bundle() //notificationWear.bundle;
        var i = 0
        for (remoteIn in remoteInputs1) {
            remoteInputs[i] = remoteIn
            // This works. Might need additional parameter to make it for Hangouts? (notification_tag?)
            localBundle.putCharSequence(remoteInputs[i]!!.resultKey, myText)
            i++
        }
        RemoteInput.addResultsToIntent(remoteInputs, localIntent, localBundle)
        try {
            if (pendingIntent != null) {
//                if (dbUtils == null) {
//                    dbUtils = new DbUtils(getApplicationContext());
//                }
//                dbUtils.logReply(sbn, NotificationUtils.getTitle(sbn));
                pendingIntent.send(this, 0, localIntent)
                //if (PreferencesManager.getPreferencesInstance(this).isShowNotificationEnabled()) {
                //NotificationHelper.getInstance(getApplicationContext()).sendNotification(sbn.getNotification().extras.getString("android.title"), sbn.getNotification().extras.getString("android.text"), sbn.getPackageName());
                //}
                cancelNotification(sbn.key)
                //                if (canPurgeMessages()) {
//                    dbUtils.purgeMessageLogs();
//                    PreferencesManager.getPreferencesInstance(this).setPurgeMessageTime(System.currentTimeMillis());
//                }
            }
        } catch (e: CanceledException) {
            Log.e(TAG, "replyToLastNotification error: " + e.localizedMessage)
        }
    }

    private fun isSupportedPackage(sbn: StatusBarNotification): Boolean {
        val prefs = PreferencesManager.getPreferencesInstance(this)
        return prefs.isSupportedAppEnabled(sbn.packageName)
    }

    private fun canSendReplyNow(sbn: StatusBarNotification): Boolean {
        // Do not reply to consecutive notifications from same person/group that arrive in below time
        // This helps to prevent infinite loops when users on both end uses Watomatic or similar app
        val DELAY_BETWEEN_REPLY_IN_MILLISEC = 10 * 1000
        val title = getTitle(sbn)
        val selfDisplayName = sbn.notification.extras.getString("android.selfDisplayName")
        return if (title != null && title.equals(selfDisplayName,
                ignoreCase = true)
        ) { //to protect double reply in case where if notification is not dismissed and existing notification is updated with our reply
            false
        } else true
        //        if (dbUtils == null) {
//            dbUtils = new DbUtils(getApplicationContext());
//        }
        //long timeDelay = PreferencesManager.getPreferencesInstance(this).getAutoReplyDelay();
        //long timeDelay = 10 * 1000; // 10 seconds
        //return (System.currentTimeMillis() - dbUtils.getLastRepliedTime(sbn.getPackageName(), title) >= max(timeDelay, DELAY_BETWEEN_REPLY_IN_MILLISEC));
    }

    private fun isGroupMessageAndReplyAllowed(sbn: StatusBarNotification): Boolean {
        val rawTitle = getTitleRaw(sbn)
        //android.text returning SpannableString
        val rawText = SpannableString.valueOf("" + sbn.notification.extras["android.text"])
        // Detect possible group image message by checking for colon and text starts with camera icon #181
        val isPossiblyAnImageGrpMsg =
            (rawTitle != null && (": ".contains(rawTitle) || "@ ".contains(rawTitle))
                    && rawText != null && rawText.toString().contains("\uD83D\uDCF7"))
        return if (!sbn.notification.extras.getBoolean("android.isGroupConversation")) {
            !isPossiblyAnImageGrpMsg
        } else {
            //return PreferencesManager.getPreferencesInstance(this).isGroupReplyEnabled();
            true
        }
    }

    private val isServiceEnabled: Boolean
        private get() = PreferencesManager.getPreferencesInstance(this).isServiceEnabled
}