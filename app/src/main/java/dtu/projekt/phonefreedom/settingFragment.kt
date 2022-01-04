 package dtu.projekt.phonefreedom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation



 class settingFragment : Fragment() {
     lateinit var languages: Button


     override fun onCreateView(


         inflater: LayoutInflater, container: ViewGroup?,
         savedInstanceState: Bundle?
     ): View? {
         // Inflate the layout for this fragment
         return inflater.inflate(R.layout.fragment_setting, container, false)
     }

     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         super.onViewCreated(view, savedInstanceState)

         languages = view.findViewById(R.id.LanguagesButton)
         languages.setOnClickListener {
             Navigation.findNavController(view)
                 .navigate(R.id.action_settingFragment_to_languageFragment2)
         }

     }
 }



