package dtu.projekt.phonefreedom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dtu.projekt.phonefreedom.R
import android.widget.AutoCompleteTextView
import android.widget.ArrayAdapter
import android.content.Intent
import android.content.ComponentName
import android.view.View
import android.widget.Button

class SettingsScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_screen)
        val LangSet = findViewById<View>(R.id.autoCompleteTextView) as AutoCompleteTextView
        val myAdapter = ArrayAdapter(
            this@SettingsScreen,
            android.R.layout.simple_list_item_1, resources.getStringArray(R.array.Languages)
        )
        myAdapter.setDropDownViewResource(R.layout.dropdown_item)
        LangSet.setAdapter(myAdapter)
        val DoNot = findViewById<View>(R.id.Do_not_disturb) as Button
        DoNot.setOnClickListener {
            val settingdnd = Intent()
            settingdnd.component = ComponentName(
                "com.android.settings",
                "com.android.settings.Settings\$ZenModeSettingsActivity"
            )
            startActivity(settingdnd)
        }
    }
}