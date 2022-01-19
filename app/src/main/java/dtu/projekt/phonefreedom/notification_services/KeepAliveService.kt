package dtu.projekt.phonefreedom.notification_services

import dtu.projekt.phonefreedom.notification_services.NotificationHelper.Companion.getInstance
import android.content.Intent
import android.os.IBinder
import dtu.projekt.phonefreedom.notification_services.PreferencesManager
import dtu.projekt.phonefreedom.notification_services.NotificationServiceRestartReceiver
import dtu.projekt.phonefreedom.notification_services.NotificationService
import android.app.ActivityManager
import android.app.Service
import android.util.Log
import dtu.projekt.phonefreedom.notification_services.NotificationHelper
import dtu.projekt.phonefreedom.notification_services.KeepAliveService

class KeepAliveService : Service() {
    override fun onCreate() {
        Log.d("DEBUG", "KeepAliveService onCreate")
        super.onCreate()
        startForeground(this)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
//        super.onStartCommand(intent, flags, startId);
        Log.d("DEBUG", "KeepAliveService onStartCommand")
        startNotificationService()
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.d("DEBUG", "KeepAliveService onBind")
        return null
    }

    override fun onUnbind(intent: Intent): Boolean {
        Log.d("DEBUG", "KeepAliveService onUnbind")
        tryReconnectService()
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("DEBUG", "KeepAliveService onDestroy")
        tryReconnectService()
    }

    override fun onTaskRemoved(rootIntent: Intent) {
        super.onTaskRemoved(rootIntent)
        Log.d("DEBUG", "KeepAliveService onTaskRemoved")
        tryReconnectService()
    }

    fun tryReconnectService() {
        if (PreferencesManager.getPreferencesInstance(applicationContext).isServiceEnabled) {
            Log.d("DEBUG", "KeepAliveService tryReconnectService")
            //Send broadcast to restart service
            val broadcastIntent =
                Intent(applicationContext, NotificationServiceRestartReceiver::class.java)
            broadcastIntent.action = "Watomatic-RestartService-Broadcast"
            sendBroadcast(broadcastIntent)
        }
    }

    private fun startNotificationService() {
        if (!isMyServiceRunning) {
            Log.d("DEBUG", "KeepAliveService startNotificationService")
            val mServiceIntent = Intent(this, NotificationService::class.java)
            startService(mServiceIntent)
        }
    }

    private val isMyServiceRunning: Boolean
        private get() {
            val manager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
            for (service in manager.getRunningServices(Int.MAX_VALUE)) {
                service.service.className
            }
            Log.i("isMyServiceRunning?", false.toString() + "")
            return false
        }

    private fun startForeground(service: Service) {
        Log.e("DEBUG", "startForeground")
        val notificationBuilder = getInstance(applicationContext)!!
            .getForegroundServiceNotification(service)
        service.startForeground(FOREGROUND_NOTIFICATION_ID, notificationBuilder.build())
    }

    companion object {
        private const val FOREGROUND_NOTIFICATION_ID = 10
    }
}