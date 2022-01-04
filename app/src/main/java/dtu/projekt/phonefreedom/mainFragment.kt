package dtu.projekt.phonefreedom

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import dtu.projekt.phonefreedom.databinding.ActivityMainBinding
import dtu.projekt.phonefreedom.databinding.FragmentMainBinding
import java.text.SimpleDateFormat
import java.util.*


class mainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    private lateinit var binding: FragmentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentMainBinding.inflate(layoutInflater)
        val view = binding.root
        //   val navController = findNavController(R.id.settings)
        //setContentView(view)
        addTime()
        //addClickListeners()
        appButton()
        showMenu()
    }



/*****************************************/
   /* private fun addClickListeners(){
        binding.buttonSelectPredefinedMessage.setOnClickListener {
            val myIntent = Intent(this, PredefinedMessagesActivity::class.java)
            this.startActivityForResult(myIntent, 1)
        }
    }*/

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode === 1) {
            if (resultCode === AppCompatActivity.RESULT_OK) {
                val predefinedMessage: String? = data?.getStringExtra ("Extra_SelectedPredefinedMessage")
                var predefinedMessageEditText = binding.editTextAutoText
                    //view.findViewById<EditText>(R.id.editTextAutoText)
                predefinedMessageEditText.setText(predefinedMessage)
            }
            if (resultCode === AppCompatActivity.RESULT_CANCELED) {

            }
        }
    }

    // HelloAlijan
    private fun addTime() {
        binding.addTime.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeListen = TimePickerDialog.OnTimeSetListener{ timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                binding.editTextFreeTo.setText(SimpleDateFormat("HH:mm").format(cal.time))
            }
            TimePickerDialog(context, timeListen, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
            val secondTime = TimePickerDialog.OnTimeSetListener{ timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                binding.editTextFreeFrom.setText(SimpleDateFormat("HH:mm").format(cal.time))
            }
            TimePickerDialog(context, secondTime, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()

        }
    }

    private fun appButton() {
        binding.whatsappButton.setOnClickListener {
            binding.whatsappButton.isSelected = !binding.whatsappButton.isSelected
        }
        binding.CallButton.setOnClickListener {
            binding.CallButton.isSelected = !binding.CallButton.isSelected
        }
        binding.SnapchatButton.setOnClickListener {
            binding.SnapchatButton.isSelected = !binding.SnapchatButton.isSelected
        }
        binding.EmailButton.setOnClickListener {
            binding.EmailButton.isSelected = !binding.EmailButton.isSelected
        }
        binding.messengerButton.setOnClickListener {
            binding.messengerButton.isSelected = !binding.messengerButton.isSelected
        }
        binding.TelegramButton.setOnClickListener {
            binding.TelegramButton.isSelected = !binding.TelegramButton.isSelected
        }
        binding.InstagramButton.setOnClickListener {
            binding.InstagramButton.isSelected = !binding.InstagramButton.isSelected
        }
        binding.MessageButton.setOnClickListener {
            binding.MessageButton.isSelected = !binding.MessageButton.isSelected
        }


    }

    private fun showMenu() {
        binding.toSettings.setOnClickListener{
            Navigation.findNavController(requireView()).navigate(R.id.action_mainFragment_to_settingFragment)

        }
    }

    }