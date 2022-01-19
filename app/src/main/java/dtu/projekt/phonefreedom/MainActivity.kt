package dtu.projekt.phonefreedom


import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dtu.projekt.phonefreedom.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.widget.EditText
import dtu.projekt.phonefreedom.notification_services.PreferencesManager
import android.app.NotificationManager
import android.provider.Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS
import android.widget.Toast
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import androidx.annotation.RequiresApi
import dtu.projekt.phonefreedom.notification_services.InstalledAppsActivity





open class MainActivity : AppCompatActivity() {



    private lateinit var binding: ActivityMainBinding
    private lateinit var editText: EditText
    private lateinit var editTextFreeTo: TextView
    private var showtime: String = "no time"
    val requestNr= 1


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        /*val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
        startActivity(intent)*/
        setContentView(view)
        addTime()
        addClickListeners()
        appButton()
        settingsScreen()


        isNotificationServiceRunning()
        requestPermission()
    }

    private fun isNotificationServiceRunning(): Boolean {
        val contentResolver = contentResolver
        val enabledNotificationListeners =
            Settings.Secure.getString(contentResolver, "enabled_notification_listeners")
        val packageName = getPackageName()
        return enabledNotificationListeners != null && enabledNotificationListeners.contains(
            packageName)

    }

    private fun requestPermission(){

        val isNotificationServiceRunning = isNotificationServiceRunning()
        if (!isNotificationServiceRunning) {
            startActivity(Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS))
        }

    }






    private fun addClickListeners() {
        binding.buttonSelectPredefinedMessage.setOnClickListener {
            val myIntent = Intent(this, PredefinedMessagesActivity::class.java)
            this.startActivityForResult(myIntent, 1)
        }
    }

    private fun settingsScreen() {
        binding.toSettings.setOnClickListener {
            val myIntent = Intent(this, ShowSettingActivity::class.java)
           startActivity(myIntent)
        }
    }
    var SELECT_SMS_APP_RESULT = 5

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
        if (requestCode == SELECT_SMS_APP_RESULT) {
            if (resultCode == RESULT_OK) {
                val smsPackageName =
                    data?.getStringExtra(InstalledAppsActivity.SMS_APP_PACKAGE_NAME_RESULT)
                val prefs = PreferencesManager.getPreferencesInstance(this)
                prefs.setSmsPackageName(smsPackageName)
                prefs.setSmsEnabled(true);
                Toast.makeText(this, smsPackageName, Toast.LENGTH_LONG).show()
            }
            if (resultCode == RESULT_CANCELED) {
                // user cancelled
            }
        }

    }






    private fun addTime() {
        binding.textViewFreeAddTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeListen = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                showtime = SimpleDateFormat("HH:mm").format(cal.time)
                binding.editTextFreeTo.setText(showtime)

                Toast.makeText(this, "Time saved at $showtime", Toast.LENGTH_SHORT).show()
            }
            TimePickerDialog(
                this, android.R.style.Theme_Holo_Light_Dialog,
                timeListen,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }
        binding.editTextFreeTo.setOnClickListener {
            addTime()

        }

    }


    private fun appButton() {

        val prefs: PreferencesManager = PreferencesManager.getPreferencesInstance(this)
        editText = findViewById(R.id.editTextAutoText)
        editTextFreeTo = findViewById(R.id.editTextFreeTo)

        editTextFreeTo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}


            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                showtime = s.toString()
                var showMyTimeOnAutoText = findViewById<EditText>(R.id.editTextAutoText)
                showMyTimeOnAutoText.setText(editText.text.toString() + "jeg tjekker min telefon kl:" + showtime)
            }

            override fun afterTextChanged(s: Editable) {
            }
        })

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                val default = "This is default message "

                val text = s.toString().trim()
                if (s.length == 0) {

                    prefs.setAutoReplyText(default)

                } else {

                    prefs.setAutoReplyText(text)


                    // Toast.makeText(this@MainActivity, showtime,Toast.LENGTH_SHORT).show()
                }

            }

            override fun afterTextChanged(s: Editable) {


            }


        })

        binding.whatsappButton.isSelected = prefs.isWhatsAppEnabled
        binding.MessageButton.isSelected = prefs.isSmsEnabled
       // binding.whatsappButton.isSelected = prefs.isGroupReplyEnabled


        binding.whatsappButton.setOnClickListener {
            binding.whatsappButton.isSelected = !binding.whatsappButton.isSelected


            prefs.setWhatsAppEnabled(binding.whatsappButton.isSelected)
            prefs.isGroupReplyEnabled = true
        }
        binding.CallButton.setOnClickListener {
            binding.CallButton.isSelected = !binding.CallButton.isSelected
        }
        /*  binding.SnapchatButton.setOnClickListener {
              binding.SnapchatButton.isSelected = !binding.SnapchatButton.isSelected
              prefs.setSignalEnabled(binding.SnapchatButton.isSelected)
          }*/
        binding.EmailButton.setOnClickListener {
            binding.EmailButton.isSelected = !binding.EmailButton.isSelected
            prefs.setOutlookEnabled(binding.EmailButton.isSelected)
        }

        binding.messengerButton.setOnClickListener {
            binding.messengerButton.isSelected = !binding.messengerButton.isSelected
            prefs.setMessengerEnabled(binding.messengerButton.isSelected)
            prefs.isGroupReplyEnabled= true
        }
        binding.TelegramButton.setOnClickListener {
            binding.TelegramButton.isSelected = !binding.TelegramButton.isSelected
            prefs.setTelegramEnabled(binding.TelegramButton.isSelected)
        }
        binding.InstagramButton.setOnClickListener {
            binding.InstagramButton.isSelected = !binding.InstagramButton.isSelected
            prefs.setInstagramEnabled(binding.InstagramButton.isSelected)
        }
        binding.MessageButton.setOnClickListener {
            binding.MessageButton.isSelected = !binding.MessageButton.isSelected
            val prefs = PreferencesManager.getPreferencesInstance(this)
            val smsPackageName = prefs.smsPackageName
            if (smsPackageName == null || smsPackageName.length == 0) {
                val i = Intent(this, InstalledAppsActivity::class.java)
                startActivityForResult(i, SELECT_SMS_APP_RESULT)
            } else {
                prefs.setSmsEnabled(binding.MessageButton.isSelected)
            }
            /*val i = Intent(this, InstalledAppsActivity::class.java)
            startActivityForResult(i, SELECT_SMS_APP_RESULT)
            prefs.setSmsEnabled(binding.MessageButton.isSelected)
*/
        }
        binding.goandstopButton.setOnClickListener {
            binding.goandstopButton.isSelected = !binding.goandstopButton.isSelected

            val showme = ShowSettingActivity()


           // showVideo()


        }


        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        binding.SwitchOnOff.setOnCheckedChangeListener { buttonView, isChecked ->
            if (checkNotificationPolicyAccess(notificationManager)) {
                if (isChecked) {
                    notificationManager.onDOD()
                    Toast.makeText(this, "Don't-Disturb turned on.", Toast.LENGTH_SHORT).show()

                } else {
                    notificationManager.offDOD()
                    Toast.makeText(this, "Don't-Disturb turned off.", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }


    open fun showVideo() {
        if (binding.goandstopButton.isSelected) {
            val intent = Intent(this, VideoActivity2::class.java)
            this.startActivity(intent)
            binding.goandstopButton.isSelected
        }
    }




/*   private fun launchNotificationAccessSettings() {


       val result: Int =
           ContextCompat.checkSelfPermission(this, ACTION_NOTIFICATION_LISTENER_SETTINGS)

       val NOTIFICATION_LISTENER_SETTINGS: String
       if (Build.VERSION.SDK_INT >= 23) {
           NOTIFICATION_LISTENER_SETTINGS = ACTION_NOTIFICATION_LISTENER_SETTINGS



       } else {
           NOTIFICATION_LISTENER_SETTINGS =
               "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"
           Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show()

       }
       val i = Intent(NOTIFICATION_LISTENER_SETTINGS)
       startActivity(i)
   }*/




    private fun checkNotificationPolicyAccess(notificationManager: NotificationManager): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (notificationManager.isNotificationPolicyAccessGranted) {
                //toast("Notification policy access granted.")
                return true
            } else {
                Toast.makeText(this, "You need to grant notification policy access.", Toast.LENGTH_SHORT).show()
                // If notification policy access not granted for this package
                val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
                startActivity(intent)
            }
        } else {
            Toast.makeText(this, "Device does not support this feature", Toast.LENGTH_SHORT).show()
        }
        return false
    }

    fun NotificationManager.onDOD() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE)
        }
    }


    // Extension function to turn off do not disturb
    fun NotificationManager.offDOD() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL)
        }
    }





}





/*private fun sendWhatsapp(message: String) {
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

          *//* val sendIntent = Intent()
             sendIntent.action = Intent.ACTION_SEND
             sendIntent.putExtra(Intent.EXTRA_TEXT, "this is a test")
             sendIntent.type = "text/plain"
             sendIntent.setPackage("com.whatsapp")
             if (sendIntent.resolveActivity(packageManager) != null) {
                 startActivity(sendIntent)
             }*//*
        }


//Here we tell android to only send it to whatsapp by setting the package to whatsapp's package.
//This will not open the app selection dialog as we specifically send to whatsapp


//Here we tell android to only send it to whatsapp by setting the package to whatsapp's package.
//This will not open the app selection dialog as we specifically send to whatsapp

            //sendIntent.setPackage("com.whatsapp") // whatsapp
            // sendIntent.setPackage("com.facebook.orca") // facebook messeger
            //sendIntent.setPackage("org.telegram.messenger") // telegram
            // sendIntent.setPackage("com.snapchat.android") //snapchat
            //sendIntent.setPackage("com.instagram.android") //instagram
            //  sendIntent.setPackage("com.samsung.android.messaging") // messages
            //sendIntent.setPackage("com.android.server.telecom") Phone call not working // telefon
            //


            //startActivity(sendIntent)
        }*/


/* val result: Int
     result = ContextCompat.checkSelfPermission(this, ACTION_NOTIFICATION_LISTENER_SETTINGS)
     if (result == PERMISSION_GRANTED) {
         return
     }

     val NOTIFICATION_LISTENER_SETTINGS: String
     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
         NOTIFICATION_LISTENER_SETTINGS = ACTION_NOTIFICATION_LISTENER_SETTINGS

     } else {
         NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"

     }
     val i = Intent(NOTIFICATION_LISTENER_SETTINGS)
     startActivity(i)

     NOTIFICATION_LISTENER_SETTINGS =
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
             Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS
         } else {
             "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"
         }
     val i = Intent(NOTIFICATION_LISTENER_SETTINGS)
     startActivity(i)*/



