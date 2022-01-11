package dtu.projekt.phonefreedom

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import dtu.projekt.phonefreedom.databinding.ActivityLanguageBinding
import java.util.*
import kotlin.system.exitProcess
@Suppress("DEPRECATION")
class LanguageActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityLanguageBinding

    private lateinit var spinner: Spinner
    private lateinit var locale: Locale
    private var currentLanguage = "da"
    private var currentLang: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language)
        title = "Change Language"
        currentLanguage = intent.getStringExtra(currentLang).toString()
        spinner = findViewById(R.id.spinner)
        val list = ArrayList<String>()
        list.add("Select Language")
        list.add("English")
        list.add("Danish")

        val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                when (position) {
                    0 -> {
                    }
                    1 -> setLocale("en")
                    2 -> setLocale("da")
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }


    }
    private fun setLocale(localeName: String) {
        if (localeName != currentLanguage) {
            locale = Locale(localeName)
            val res = resources
            val dm = res.displayMetrics
            val conf = res.configuration
            conf.locale = locale
            res.updateConfiguration(conf, dm)
            val refresh = Intent(this, LanguageActivity::class.java)
            refresh.putExtra(currentLang, localeName)
            //startActivity(refresh)
        } else {
            Toast.makeText(
                this, "Language, , already, , selected)!", Toast.LENGTH_SHORT).show();
        }
    }

    override fun onBackPressed() {
        val myIntent = Intent(this, MainActivity::class.java)
        myIntent.addCategory(Intent.CATEGORY_HOME)
        myIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        finish()
//        val myIntent = Intent(this, MainActivity::class.java)
//        myIntent.addCategory(Intent.CATEGORY_HOME)
//        myIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
//        startActivity(myIntent)
//        finish()
//        exitProcess(0)
    }
}