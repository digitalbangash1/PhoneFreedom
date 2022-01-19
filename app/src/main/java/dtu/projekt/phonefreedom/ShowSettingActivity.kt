package dtu.projekt.phonefreedom

import android.app.Activity
import android.app.AlertDialog
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import java.util.*

class ShowSettingActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLocate()
        setContentView(R.layout.activity_show_setting)

        val actionBar = supportActionBar
        actionBar!!.title = resources.getString(R.string.app_name)


        val btn_changeLang = findViewById<Button>(R.id.btn_changelang)
        btn_changeLang.setOnClickListener {
            showChangeLang()
        }


        val btn_animation= findViewById<Button>(R.id.Animation1)
        btn_animation.setOnClickListener {
                showAnimation()
        }


        val btn_DoNot = findViewById<Button>(R.id.btn_DontDisturb)
        btn_DoNot.setOnClickListener {
            val settingdnd = Intent()
            settingdnd.component = ComponentName("com.android.settings", "com.android.settings.Settings\$ZenModeSettingsActivity")
            startActivity(settingdnd)
        }

    }




    private fun showChangeLang(){

        val listItems = arrayOf("العربية", "中國人","Dansk","English","Français","Deutsch","Español")

        val mBuilder = AlertDialog.Builder(this)
        mBuilder.setTitle("Choose Language")
        mBuilder.setSingleChoiceItems(listItems,-1) { dialog, which ->
            if (which == 0) {
                setLocate("ar")

                recreate()
                Toast.makeText(this, "تم اختيار اللغة العربية", Toast.LENGTH_SHORT).show()
                val refresh = Intent(this, MainActivity::class.java)
                startActivity(refresh)

            } else if (which == 1) {
                setLocate("zh")
                recreate()
                Toast.makeText(this, "Chinese selected.", Toast.LENGTH_SHORT).show()
                val refresh = Intent(this, MainActivity::class.java)
                startActivity(refresh)

            } else if (which == 2) {
                setLocate("da")
                recreate()
                Toast.makeText(this, "Danish selected.", Toast.LENGTH_SHORT).show()
                val refresh = Intent(this, MainActivity::class.java)
                startActivity(refresh)

            } else if (which == 3) {
                setLocate("en")
                recreate()
                Toast.makeText(this, "English selected.", Toast.LENGTH_SHORT).show()
                val refresh = Intent(this, MainActivity::class.java)
                startActivity(refresh)

            } else if (which == 4) {
                setLocate("fr")
                recreate()
                Toast.makeText(this, "French selected.", Toast.LENGTH_SHORT).show()
                val refresh = Intent(this, MainActivity::class.java)
                startActivity(refresh)

            } else if (which == 5) {
                setLocate("ge")
                recreate()
                Toast.makeText(this, "Deutsch selected.", Toast.LENGTH_SHORT).show()
                val refresh = Intent(this, MainActivity::class.java)
                startActivity(refresh)

            } else if (which == 6) {
                setLocate("es")
                recreate()
                Toast.makeText(this, "Spanish selected.", Toast.LENGTH_SHORT).show()
                val refresh = Intent(this, MainActivity::class.java)
                startActivity(refresh)

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
        //loadLocate()
    }

    private fun loadLocate() {
        val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("My Lang", "")
        if (language != null) {
            setLocate(language)
        }
    }







    fun showAnimation(){

        val listItems = arrayOf("ON","OFF")

        val mBuilder = AlertDialog.Builder(this)
        mBuilder.setTitle("Animation")
        mBuilder.setSingleChoiceItems(listItems,-1) { dialog, tilstand ->
            when (tilstand) {
                0 -> {
                        Toast.makeText(this, "Animation turned on.", Toast.LENGTH_SHORT).show()
                    val refresh = Intent(this, MainActivity::class.java)
                    startActivity(refresh)
                }
                1 -> {
                    Toast.makeText(this, "Animation turned off.", Toast.LENGTH_SHORT).show()
                    val refresh = Intent(this, MainActivity::class.java)
                    startActivity(refresh)
                }
            }
            dialog.dismiss()
        }
        val mDialog = mBuilder.create()

        mDialog.show()
    }





}