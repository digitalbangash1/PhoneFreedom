package dtu.projekt.phonefreedom

import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dtu.projekt.phonefreedom.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.media.MediaPlayer

import android.widget.EditText



class MainActivity : AppCompatActivity() {

    //TODO Forbind API Til Messenger          (Ali J)
    //TODO Forbind API Til Telegram           /*ALi D*/
    //TODO Forbind API Til Snapchat
    //TODO Forbind API Til Whatsapp          /*salim*/
    //TODO Forbind API Til Instagram         /*Thomas*/
    //TODO Forbind API Til Besked            /*salim*/
    //TODO Forbind API Til Opkald
    //TODO Forbind API Til E-mail             ( Ali J)
    //TODO Settings fragment

    //TODO Settings -> Sprogindstillinger
    //TODO Settings -> Animationer ON / OFF
    //TODO Som bruger, ønsker jeg at kunne slå appen til med en On / Off funktion så appen er let at anvende.
    //TODO Ny urfunktion i henhold til PO's ønske på discord
    //TODO Billede / Logo med link i auto reply, som bruges til at videresende til enten App store / Play store.
    //TODO Slå alle applikationer til / fr  /*salim*/

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
        showMenu()
        //sendWhatsapp()
        //sendWhatsapp()
        launchNotificationAccessSettings()
        showVideo()
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

    private fun showMenu() {
        binding.toSettings.setOnClickListener {
            val intent = Intent(this, LanguageActivity::class.java)
            this.startActivity(intent)
        }
    }

   private fun launchNotificationAccessSettings() {
        val NOTIFICATION_LISTENER_SETTINGS: String
        NOTIFICATION_LISTENER_SETTINGS =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS
            } else {
                "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"
            }
        val i = Intent(NOTIFICATION_LISTENER_SETTINGS)
        startActivity(i)
    }

    private fun sendWhatsapp(message: String) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, "this is ")
        sendIntent.type = "text/plain"
        sendIntent.setPackage("com.whatsapp")
        if (sendIntent.resolveActivity(packageManager) != null) {
            startActivity(sendIntent)
        }
    }

    private fun sendWhatsapp() {
        binding.whatsappSend.setOnClickListener {

            val sendIntent = Intent() // this works
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
            sendIntent.type = "text/plain"
           sendIntent.setPackage("com.whatsapp")
            startActivity(sendIntent)

           /* val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, "this is a test")
            sendIntent.type = "text/plain"
            sendIntent.setPackage("com.whatsapp")
            if (sendIntent.resolveActivity(packageManager) != null) {
                startActivity(sendIntent)
            }*/
        }


//Here we tell android to only send it to whatsapp by setting the package to whatsapp's package.
//This will not open the app selection dialog as we specifically send to whatsapp


//Here we tell android to only send it to whatsapp by setting the package to whatsapp's package.
//This will not open the app selection dialog as we specifically send to whatsapp

            // sendIntent.setPackage("com.whatsapp") // whatsapp
            //sendIntent.setPackage("com.whatsapp") // whatsapp
            // sendIntent.setPackage("com.facebook.orca") // facebook messeger
            //sendIntent.setPackage("org.telegram.messenger") // telegram
            // sendIntent.setPackage("com.snapchat.android") //snapchat
            //sendIntent.setPackage("com.instagram.android") //instagram
            //  sendIntent.setPackage("com.samsung.android.messaging") // messages
            //sendIntent.setPackage("com.android.server.telecom") Phone call not working // telefon
            //


            // startActivity(sendIntent)
        }
    private fun showVideo() {
        if (!binding.goandstopButton.isSelected) {
            binding.goandstopButton.setOnClickListener {
                val intent = Intent(this, VideoClipActivity::class.java)
                this.startActivity(intent)
                binding.goandstopButton.isSelected
            }
        }
    }



    }









