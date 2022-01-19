package dtu.projekt.phonefreedom.notification_services

import android.content.Context

import dtu.projekt.phonefreedom.notification_services.InstalledApp
import android.widget.ArrayAdapter
import dtu.projekt.phonefreedom.R
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import java.util.ArrayList

class InstalledAppsAdapter(private val dataSet: ArrayList<InstalledApp>, var mContext: Context) :
    ArrayAdapter<InstalledApp?>(
        mContext, R.layout.installed_apps_list_view_item, dataSet as List<InstalledApp?>
    ) {
    private val lastPosition = -1
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val dataModel = getItem(position)
        val viewHolder: ViewHolder
        if (convertView == null) {
            viewHolder = ViewHolder()
            val inflater = LayoutInflater.from(context)
            convertView = inflater.inflate(R.layout.installed_apps_list_view_item, parent, false)
            viewHolder.appName = convertView.findViewById<View>(R.id.app_name) as TextView
            viewHolder.appPackageName =
                convertView.findViewById<View>(R.id.app_package_name) as TextView
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
        }
        viewHolder.appName!!.text = dataModel!!.name
        viewHolder.appPackageName!!.text = dataModel.packageName
        return convertView!!
    }

    private class ViewHolder {
        var appName: TextView? = null
        var appPackageName: TextView? = null
    }
}