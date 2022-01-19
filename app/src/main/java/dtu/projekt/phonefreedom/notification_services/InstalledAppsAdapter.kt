package dtu.projekt.phonefreedom.notification_services;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import dtu.projekt.phonefreedom.R;

public class InstalledAppsAdapter extends ArrayAdapter<InstalledApp> {

    private ArrayList<InstalledApp> dataSet;
    Context mContext;

    public InstalledAppsAdapter(ArrayList<InstalledApp> data, Context context) {
        super(context, R.layout.installed_apps_list_view_item, data);
        this.dataSet = data;
        this.mContext = context;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstalledApp dataModel = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.installed_apps_list_view_item, parent, false);
            viewHolder.appName = (TextView) convertView.findViewById(R.id.app_name);
            viewHolder.appPackageName = (TextView) convertView.findViewById(R.id.app_package_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.appName.setText(dataModel.getName());
        viewHolder.appPackageName.setText(dataModel.getPackageName());
        return convertView;
    }

    private static class ViewHolder {
        TextView appName;
        TextView appPackageName;
    }
}
