/*
package dtu.projekt.phonefreedom;

import android.app.Notification;

import android.content.ComponentName;
import android.content.Intent;


import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.net.URLEncoder;


public class MyNotificationListener extends NotificationListenerService {

    private static final String TAG = "MyNotificationListener";
    private static final String WA_PACKAGE = "com.whatsapp";

    @Override
    public void onListenerConnected() {
        Log.i(TAG, "Notification Listener connected");
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);

        if (!sbn.getPackageName().equals(WA_PACKAGE)) return;

        Notification notification = sbn.getNotification();
        Bundle bundle = notification.extras;
        String sender = notification.extras.getString("android.title");
        String from = bundle.getString(NotificationCompat.EXTRA_TITLE);
        String message = bundle.getString(NotificationCompat.EXTRA_TEXT);



*/
/*
        String phone = "+4542243712";
        String messages = "Hello World";
        PackageManager packageManager = this.getPackageManager();
        Intent i = new Intent(Intent.ACTION_VIEW);

       i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        try {
            String url = "https://api.whatsapp.com/send?phone=" + phone + "&text=" + URLEncoder.encode(messages, "UTF-8");
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));

            if (i.resolveActivity(packageManager) != null) {
                this.startActivity(i);
                Toast toast = Toast.makeText(getApplicationContext(), "start activity", Toast.LENGTH_SHORT);
                toast.show();

            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(getApplicationContext(), from, Toast.LENGTH_SHORT);
            toast.show();
        }*//*


        */
/* Not working

         PackageManager pm = getPackageManager();
        try {
            String toNumber = "+4542243712"; // Replace with mobile phone number without +Sign or leading zeros, but with country code.
            Intent sendIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("smsto:" + "" + toNumber + "?body=" + ""));
            sendIntent.setPackage("com.whatsapp");
            startActivity(sendIntent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), from, Toast.LENGTH_LONG).show();
        }*//*


        Intent myIntent = new Intent("android.intent.action.Send");
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        myIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
        myIntent.putExtra("abc", PhoneNumberUtils.stripSeparators("+4542243712") + "@s.whatsapp.net");
        startActivity(myIntent);


    }













           */
/* Toast toast=Toast.makeText(getApplicationContext(),sender,Toast.LENGTH_SHORT);
            toast.setMargin(50,50);
            toast.show()*//*
;


}

*/
