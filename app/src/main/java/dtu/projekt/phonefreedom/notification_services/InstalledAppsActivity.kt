package dtu.projekt.phonefreedom.notification_services


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dtu.projekt.phonefreedom.R
import dtu.projekt.phonefreedom.notification_services.InstalledApp
import android.content.pm.PackageManager
import android.content.pm.ApplicationInfo
import dtu.projekt.phonefreedom.notification_services.InstalledAppsActivity.InstalledAppComparator
import dtu.projekt.phonefreedom.notification_services.InstalledAppsAdapter
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView
import android.content.Intent
import dtu.projekt.phonefreedom.notification_services.InstalledAppsActivity
import android.app.Activity
import android.view.View
import android.widget.ListView
import java.util.*

class InstalledAppsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_installed_apps)
        val apps = ArrayList<InstalledApp>()
        val pm = packageManager
        val installedApps = pm.getInstalledApplications(PackageManager.GET_META_DATA)
        for (appInfo in installedApps) {
            apps.add(InstalledApp(appInfo.loadLabel(pm).toString(), appInfo.packageName))
        }
        Collections.sort(apps, InstalledAppComparator())
        val adapter = InstalledAppsAdapter(apps, this)
        val installedAppsListView = findViewById<View>(R.id.installed_apps) as ListView
        installedAppsListView.adapter = adapter
        installedAppsListView.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                val (_, packageName) = apps[position]
                val returnIntent = Intent()
                returnIntent.putExtra(SMS_APP_PACKAGE_NAME_RESULT, packageName)
                setResult(RESULT_OK, returnIntent)
                finish()
            }
    }

    private class InstalledAppComparator : Comparator<InstalledApp> {
        override fun compare(app1: InstalledApp, app2: InstalledApp): Int {
            return app1.name.compareTo(app2.name)
        }
    }

    companion object {
        const val SMS_APP_PACKAGE_NAME_RESULT = "smsAppPackageName"
    }
}