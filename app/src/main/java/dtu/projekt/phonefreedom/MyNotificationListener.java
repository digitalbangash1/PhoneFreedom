package dtu.projekt.phonefreedom;

import android.app.Notification;
import android.app.Service;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;


    public class MyNotificationListener extends NotificationListenerService {

        private static final String TAG = "MyNotificationListener";
        private static final String WA_PACKAGE = "com.whatsapp";

        @Override
        public void onListenerConnected() {
            Log.i(TAG, "Notification Listener connected");
        }


        @Override
        public void onNotificationPosted(StatusBarNotification sbn) {
            if (!sbn.getPackageName().equals(WA_PACKAGE)) return;
            Notification notification = sbn.getNotification();
            Bundle bundle = notification.extras;
            String sender = notification.extras.getString("android.title");
            String from = bundle.getString(NotificationCompat.EXTRA_TITLE);
            String message = bundle.getString(NotificationCompat.EXTRA_TEXT);
            Toast toast=Toast.makeText(getApplicationContext(),sender,Toast.LENGTH_SHORT);
            toast.setMargin(50,50);
            toast.show();
        }
        @Override
        public void onNotificationRemoved(StatusBarNotification sbn) {
            Log.i("SevenNLS","Notification removed");
        }





    }




