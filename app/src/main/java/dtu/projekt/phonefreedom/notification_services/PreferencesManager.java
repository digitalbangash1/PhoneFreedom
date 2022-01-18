package dtu.projekt.phonefreedom.notification_services;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Telephony;

import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import dtu.projekt.phonefreedom.MainActivity;

public class PreferencesManager {

    //These are all the supported apps
    private final String WHATSAPP_PACKAGE_NAME = "com.whatsapp";
    private final String MESSENGER_PACKAGE_NAME = "com.facebook.orca";
    private final String SNAPCHAT_PACKAGE_NAME = "com.snapchat.android";
    private final String TELEGRAM_PACKAGE_NAME = "org.telegram.messenger";
    private final String INSTAGRAM_PACKAGE_NAME = "com.instagram.android";
    private final String OUTLOOK_PACKAGE_NAME = "com.microsoft.office.outlook";
    private final String KEY_SMS_PACKAGE_NAME = "prefs_sms_package_name";
    private final String SIGNAL_PACKAGE_NAME = "org.thoughtcrime.securesms";
    private final String KEY_PREDEFINED_MESSAGE = "prefs_predefined_messages";

    private ArrayList<SupportedApp> supportedApps = new ArrayList<>();


    /*   *//*public final SupportedApp[] supportedApps = new SupportedApp[]{
     *//**//*new SupportedApp("WhatsApp", WHATSAPP_PACKAGE_NAME),
            new SupportedApp("Messenger", MESSENGER_PACKAGE_NAME),
            new SupportedApp("Snapchat", SNAPCHAT_PACKAGE_NAME),
            new SupportedApp("Telegram", TELEGRAM_PACKAGE_NAME),
            new SupportedApp("Instagram", INSTAGRAM_PACKAGE_NAME),
            new SupportedApp("Outlook", OUTLOOK_PACKAGE_NAME),
            new SupportedApp("Sms", SMS_Package_Name),
            new SupportedApp("Signal",SIGNAL_PACKAGE_NAME)
    };
*/
    private final String KEY_SERVICE_ENABLED = "pref_service_enabled";
    private final String KEY_GROUP_REPLY_ENABLED = "pref_group_reply_enabled";
    private final String KEY_AUTO_REPLY_TEXT = "pref_auto_reply_text";
    private final String KEY_ENABLED_APPS_PACKAGE_NAMES = "pref_enabled_apps_package_names";

    private final SharedPreferences sharedPrefs;
    private static PreferencesManager instance;
    private final Context context;

    public PreferencesManager(Context context) {
         this.context = context;
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        supportedApps.add(new SupportedApp("WhatsApp", WHATSAPP_PACKAGE_NAME));
        supportedApps.add(new SupportedApp("Messenger", MESSENGER_PACKAGE_NAME));
        supportedApps.add(new SupportedApp("Snapchat", SNAPCHAT_PACKAGE_NAME));
        supportedApps.add(new SupportedApp("Telegram", TELEGRAM_PACKAGE_NAME));
        supportedApps.add(new SupportedApp("Instagram", INSTAGRAM_PACKAGE_NAME));
        supportedApps.add(new SupportedApp("Signal", SIGNAL_PACKAGE_NAME));
        addSmsSuppertedApp(getSmsPackageName())
        ;
        //SMS_Package_Name = Telephony.Sms.getDefaultSmsPackage(context);
        initializePredefinedMessages();
    }

    private void addSmsSuppertedApp(String packageName){

        if(packageName == null || packageName.length() == 0){
            return;
        }

        int index = -1;
        for(SupportedApp app : supportedApps){
            index++;
            if(app.getPackageName().toLowerCase() == packageName.toLowerCase()){
                break;
            }
        }
        if(index != -1){
            supportedApps.remove(index);
        }
        supportedApps.add(new SupportedApp("Sms", packageName));
    }

    public static PreferencesManager getPreferencesInstance(Context context) {
        if (instance == null) {
            instance = new PreferencesManager(context.getApplicationContext());
        }
        return instance;
    }
    public String[] getPredefinedMessages () {
        Set<String> messagesSet = sharedPrefs.getStringSet(KEY_PREDEFINED_MESSAGE, new HashSet<String>());
        String[] messages = new String[messagesSet.size()];
        messagesSet.toArray(messages);
        return messages;
    }
    public void setPredefinedMessages (String[] messages){
        Set<String> set = new HashSet<>();
        for (String message : messages){
            set.add(message);
        }
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putStringSet(KEY_PREDEFINED_MESSAGE, set);
        editor.commit();

    }
    private void initializePredefinedMessages () {
        String[] currentMessages = getPredefinedMessages();
        if (currentMessages.length>0){
            return;
        }
        String[] predefinedMessages = new String[5];
        predefinedMessages[0] = "one";
        predefinedMessages[1] = "Tow";
        predefinedMessages[2] = "Three";
        predefinedMessages[3] = "Four";
        predefinedMessages[4] = "Five";
        setPredefinedMessages(predefinedMessages);

    }



    public String getSmsPackageName() {
        return sharedPrefs.getString(KEY_SMS_PACKAGE_NAME, "");
    }

    public void setSmsPackageName(String smsPackageName) {
        addSmsSuppertedApp(smsPackageName);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(KEY_SMS_PACKAGE_NAME, smsPackageName);
        editor.commit();
    }

    /*public void setSmsPackageName(String smsPackageName) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(KEY_SMS_PACKAGE_NAME, smsPackageName);
        editor.commit();
    }
*/


    public boolean isServiceEnabled() {
        return sharedPrefs.getBoolean(KEY_SERVICE_ENABLED, true);
    }

    public void setServiceEnabled(boolean enabled) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putBoolean(KEY_SERVICE_ENABLED, enabled);
        editor.commit();
    }

    public boolean isGroupReplyEnabled() {
        return sharedPrefs.getBoolean(KEY_GROUP_REPLY_ENABLED, false);
    }

    public void setGroupReplyEnabled(boolean enabled) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putBoolean(KEY_GROUP_REPLY_ENABLED, enabled);
        editor.commit();
    }

    public String getAutoReplyText() {

        return sharedPrefs.getString(KEY_AUTO_REPLY_TEXT, "This is a default automated message.");
    }

    public void setAutoReplyText(String text) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(KEY_AUTO_REPLY_TEXT, text);
        editor.commit();

    }

    //Returns all supported apps that are enabled
    public SupportedApp[] getEnabledApps() {
        Set<String> enabledAppsPackageNames =
                sharedPrefs.getStringSet(KEY_ENABLED_APPS_PACKAGE_NAMES, new HashSet<String>());
        Set<SupportedApp> enabledApps = new HashSet<>();
        for (String packageName : enabledAppsPackageNames) {
            SupportedApp supportedApp = getSupportedApp(packageName);
            if (supportedApp == null) {
                continue;
            }
            enabledApps.add(supportedApp);
        }

        SupportedApp[] allEnabledApps = new SupportedApp[enabledApps.size()];
        enabledApps.toArray(allEnabledApps);
        return allEnabledApps;
    }
    public void setEnabledApp(String packageName) {
        if(packageName == null || packageName.length() == 0){
            return;
        }

        Set<String> enabledAppsPackageNames =
                sharedPrefs.getStringSet(KEY_ENABLED_APPS_PACKAGE_NAMES, new HashSet<>());
        enabledAppsPackageNames.add(packageName);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putStringSet(KEY_ENABLED_APPS_PACKAGE_NAMES, enabledAppsPackageNames);
        editor.commit();
    }

    public void setDisabledApp(String packageName) {
        if(packageName == null || packageName.length() == 0){
            return;
        }

        Set<String> enabledAppsPackageNames =
                sharedPrefs.getStringSet(KEY_ENABLED_APPS_PACKAGE_NAMES, new HashSet<>());
        enabledAppsPackageNames.remove(packageName);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putStringSet(KEY_ENABLED_APPS_PACKAGE_NAMES, enabledAppsPackageNames);
        editor.commit();
    }
    //Add these new methods
    public boolean isSmsEnabled() {
        return isSupportedAppEnabled(getSmsPackageName());
    }

    public void setSmsEnabled(boolean enabled) {
        setAppEnabled(getSmsPackageName(), enabled);
    }



   /* public void setEnabledApp(String packageName) {
        Set<String> enabledAppsPackageNames =
                sharedPrefs.getStringSet(KEY_ENABLED_APPS_PACKAGE_NAMES, new HashSet<>());
        enabledAppsPackageNames.add(packageName);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putStringSet(KEY_ENABLED_APPS_PACKAGE_NAMES, enabledAppsPackageNames);
        editor.commit();
    }

    public void setDisabledApp(String packageName) {
        Set<String> enabledAppsPackageNames =
                sharedPrefs.getStringSet(KEY_ENABLED_APPS_PACKAGE_NAMES, new HashSet<>());
        enabledAppsPackageNames.remove(packageName);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putStringSet(KEY_ENABLED_APPS_PACKAGE_NAMES, enabledAppsPackageNames);
        editor.commit();
    }
*/
    public boolean isSupportedAppEnabled(String packageName) {
        SupportedApp[] enabledApps = getEnabledApps();
        for (SupportedApp enabledApp : enabledApps) {
            if (enabledApp.getPackageName().equalsIgnoreCase(packageName)) {
                return true;
            }
        }
        return false;
    }

    public boolean isWhatsAppEnabled() {
        return isSupportedAppEnabled(WHATSAPP_PACKAGE_NAME);
    }

    public boolean isMessengerEnabled() {
        return isSupportedAppEnabled(MESSENGER_PACKAGE_NAME);
    }

    public boolean isSnapchatEnabled() {
        return isSupportedAppEnabled(SNAPCHAT_PACKAGE_NAME);
    }

    public boolean isTelegramEnabled() {
        return isSupportedAppEnabled(TELEGRAM_PACKAGE_NAME);
    }

    public boolean isInstagramEnabled() {
        return isSupportedAppEnabled(INSTAGRAM_PACKAGE_NAME);
    }

    /*public boolean isSMSEnabled() {
        return isSupportedAppEnabled(KEY_SMS_PACKAGE_NAME);
    }*/

    public boolean isSignalEnabled() {
        return isSupportedAppEnabled(SIGNAL_PACKAGE_NAME);
    }

    public boolean isOutlookEnabled() {
        return isSupportedAppEnabled(OUTLOOK_PACKAGE_NAME);
    }


    public void setWhatsAppEnabled(boolean enabled) {
        setAppEnabled(WHATSAPP_PACKAGE_NAME, enabled);
    }

    public void setMessengerEnabled(boolean enabled) {
        setAppEnabled(MESSENGER_PACKAGE_NAME, enabled);
    }

    public void setSnapchatEnabled(boolean enabled) {
        setAppEnabled(SNAPCHAT_PACKAGE_NAME, enabled);
    }

    public void setTelegramEnabled(boolean enabled) {
        setAppEnabled(TELEGRAM_PACKAGE_NAME, enabled);
    }

    public void setInstagramEnabled(boolean enabled) {
        setAppEnabled(INSTAGRAM_PACKAGE_NAME, enabled);
    }

   /* public void setSmsEnabled(boolean enabled) {
        setAppEnabled(KEY_SMS_PACKAGE_NAME, enabled);
    }*/

    public void setSignalEnabled(boolean enabled) {
        setAppEnabled(SIGNAL_PACKAGE_NAME, enabled);
    }


    public void setOutlookEnabled(boolean enabled) {
        setAppEnabled(OUTLOOK_PACKAGE_NAME, enabled);
    }

    public void setAppEnabled(String packageName, boolean enabled) {
        if (enabled) {
            setEnabledApp(packageName);
        } else {
            setDisabledApp(packageName);
        }
    }

    public SupportedApp getSupportedApp(String packageName) {
        for (SupportedApp app : supportedApps) {
            if (app.getPackageName().equalsIgnoreCase(packageName)) {
                return app;
            }
        }
        return null;
    }
}
