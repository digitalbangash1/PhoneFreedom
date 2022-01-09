package dtu.projekt.phonefreedom

import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dtu.projekt.phonefreedom.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

import android.content.Intent
import android.media.MediaPlayer
import android.view.View
import android.widget.Button

import android.widget.EditText
import androidx.fragment.app.Fragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
        startActivity(intent)
        //   val navController = findNavController(R.id.settings)
        setContentView(view)
        addTime()
        addClickListeners()
        appButton()
        sendWhatsapp()

        val ButtonOpen : Button = findViewById(R.id.btn_opensettings)
        ButtonOpen.setOnClickListener {
            val myFragment = Settings()
            val fragment : Fragment? =
            supportFragmentManager.findFragmentByTag(Settings::class.java.simpleName)
            if ( fragment !is Settings){
                supportFragmentManager.beginTransaction().add(R.id.btn_opensettings, myFragment, Settings::class.java.simpleName).commit()
            }
            ButtonOpen.visibility = View.GONE

        }
    }


    private fun addClickListeners() {
        binding.buttonSelectPredefinedMessage.setOnClickListener {
            val myIntent = Intent(this, PredefinedMessagesActivity::class.java)
            this.startActivityForResult(myIntent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode === 1) {
            if (resultCode === RESULT_OK) {
                val predefinedMessage: String? =
                    data?.getStringExtra("Extra_SelectedPredefinedMessage")
                var predefinedMessageEditText = findViewById<EditText>(R.id.editTextAutoText)
                predefinedMessageEditText.setText(predefinedMessage)
            }
            if (resultCode === RESULT_CANCELED) {

            }
        }
    }

    // HelloAlijan
    private fun addTime() {
        binding.textViewFreeAddTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeListen = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                binding.editTextFreeTo.setText(SimpleDateFormat("HH : mm").format(cal.time))
            }
            TimePickerDialog(
                this,android.R.style.Theme_Holo_Dialog,
                timeListen,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()

            binding.editTextFreeTo.setOnClickListener {
                val cal = Calendar.getInstance()
                val timeListen = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                    cal.set(Calendar.HOUR_OF_DAY, hour)
                    cal.set(Calendar.MINUTE, minute)

                    binding.editTextFreeTo.setText(SimpleDateFormat("HH : mm").format(cal.time))
                }
                TimePickerDialog(
                    this,android.R.style.Theme_Holo_Dialog,
                    timeListen,
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE),
                    true
                ).show()


            }

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
        binding.goandstopButton.setOnClickListener {
            binding.goandstopButton.isSelected = !binding.goandstopButton.isSelected
        }



    }



    private fun sendWhatsapp() {
        binding.whatsappSend.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                "This is the testing of the new intent that sends intent to the selected app package"
            )
            sendIntent.type = "text/plain"


//Here we tell android to only send it to whatsapp by setting the package to whatsapp's package.
//This will not open the app selection dialog as we specifically send to whatsapp


//Here we tell android to only send it to whatsapp by setting the package to whatsapp's package.
//This will not open the app selection dialog as we specifically send to whatsapp

             sendIntent.setPackage("com.whatsapp") // whatsapp
            // sendIntent.setPackage("com.facebook.orca") // facebook messeger
            //sendIntent.setPackage("org.telegram.messenger") // telegram
            // sendIntent.setPackage("com.snapchat.android") //snapchat
            //sendIntent.setPackage("com.instagram.android") //instagram
            //  sendIntent.setPackage("com.samsung.android.messaging") // messages
             //sendIntent.setPackage("com.android.server.telecom") Phone call not working // telefon
            //


            startActivity(sendIntent)
        }



    }


    /*fun MediaPlayer() {
        var videoView : VideoView? =null
        var mediaController : MediaController? = null
       // videoView = findViewById()

        binding.goandstopButton.setOnClickListener {

        }

    }
    fun GoStopKnap(){
    var videoView : VideoView? =null
    var mediaController : MediaController? = null
    binding.goandstopButton.setOnClickListener {

    }
    }*/







}
