package dtu.projekt.phonefreedom.notification_services;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import dtu.projekt.phonefreedom.R;

public class InstalledAppsActivity extends AppCompatActivity {

    public static final String SMS_APP_PACKAGE_NAME_RESULT = "smsAppPackageName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_installed_apps);

        ArrayList<InstalledApp> apps = new ArrayList<>();
        PackageManager pm = getPackageManager();
        List<ApplicationInfo> installedApps = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo appInfo : installedApps) {
            apps.add(new InstalledApp(appInfo.loadLabel(pm).toString(), appInfo.packageName));
        }

        Collections.sort(apps, new InstalledAppComparator());
        InstalledAppsAdapter adapter = new InstalledAppsAdapter(apps, this);
        ListView installedAppsListView = (ListView) findViewById(R.id.installed_apps);
        installedAppsListView.setAdapter(adapter);
        installedAppsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InstalledApp app = apps.get(position);
                Intent returnIntent = new Intent();
                returnIntent.putExtra(SMS_APP_PACKAGE_NAME_RESULT, app.getPackageName());
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }

    private static class InstalledAppComparator implements Comparator<InstalledApp> {
        public int compare(InstalledApp app1, InstalledApp app2) {
            return app1.getName().compareTo(app2.getName());
        }
    }

}
