package dtu.projekt.phonefreedom

import android.app.Activity
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dtu.projekt.phonefreedom.R
import android.widget.AutoCompleteTextView
import android.widget.ArrayAdapter
import android.content.Intent
import android.content.ComponentName
import android.content.Context
import android.content.res.Configuration
import android.view.View
import android.widget.Button
import java.util.*

class SettingsScreen : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_screen)
/*        val LangSet = findViewById<View>(R.id.autoCompleteTextView) as AutoCompleteTextView*//*
        val myAdapter = ArrayAdapter(
            this@SettingsScreen,
            android.R.layout.simple_list_item_1, resources.getStringArray(R.array.)
        )*//*
       *//* myAdapter.setDropDownViewResource(R.layout.dropdown_item)
        LangSet.setAdapter(myAdapter)
        val DoNot = findViewById<View>(R.id.Do_not_disturb) as Button
       DoNot.setOnClickListener {
            val settingdnd = Intent()
            settingdnd.component = ComponentName(
                "com.android.settings",
                "com.android.settings.Settings\$ZenModeSettingsActivity"
            )
            startActivity(settingdnd)
        } *//*

        val actionBar = supportActionBar
        actionBar!!.title = resources.getString(R.string.app_name)
        val ChangeLang = findViewById<Button>(R.id.myChangeLang)

        ChangeLang.setOnClickListener {
            showChangeLang()
            // loadLocate() // Call Locate
        }


    }

        private fun showChangeLang(){

            val listItems = arrayOf("English","Danish")

            val mBuilder = AlertDialog.Builder(this)
            mBuilder.setTitle("Choose Language")
            mBuilder.setSingleChoiceItems(listItems,-1) { dialog, which ->
                if (which == 0) {
                    setLocate("en")
                    recreate()
                } else if (which == 1) {
                    setLocate("da")
                    recreate()
                }

                dialog.dismiss()

            }
            val mDialog = mBuilder.create()

            mDialog.show()
        }

        private fun setLocate(Lang: String) {

            val locale = Locale(Lang)

            Locale.setDefault(locale)
            val config = Configuration()
            config.locale = locale
            baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

            val editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
            editor.putString("My Lang", Lang)
            editor.apply()
            loadLocate()
        }

        private fun loadLocate() {
            val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
            val language = sharedPreferences.getString("My_Lang", "")
            if (language != null) {
                setLocate(language)
            }
        }*/


    }
}