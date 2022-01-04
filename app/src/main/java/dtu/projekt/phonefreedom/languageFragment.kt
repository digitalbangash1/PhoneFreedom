package dtu.projekt.phonefreedom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.navigation.ui.AppBarConfiguration
import dtu.projekt.phonefreedom.databinding.FragmentLanguageBinding
import java.util.*

@Suppress("DEPRECATION")
class languageFragment : Fragment() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: FragmentLanguageBinding

    private lateinit var spinner: Spinner
    private lateinit var locale: Locale
    private var currentLanguage = "da"
    private var currentLang: String? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_language, container, false)
    }




    override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currentLanguage = getActivity()?.getIntent()?.getStringExtra(currentLang).toString()

        spinner = view.findViewById(R.id.spinner)
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


}