package dtu.projekt.phonefreedom.notification_services

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    override fun attachBaseContext(newBase: Context) {
//        PreferencesManager prefs = PreferencesManager.getPreferencesInstance(newBase);
//        ContextWrapper contextWrapper = ContextWrapper.wrap(newBase, prefs.getSelectedLocale());
//        super.attachBaseContext(contextWrapper);
//
//        //Fix language changing bug on API L to N_MR1, caused by AndroidX
//        //REF: https://stackoverflow.com/a/61572489/5525931
//        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N_MR1)
//            applyOverrideConfiguration(contextWrapper.getResources().getConfiguration());
    }
}