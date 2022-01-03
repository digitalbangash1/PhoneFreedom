package dtu.projekt.phonefreedom

import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import dtu.projekt.phonefreedom.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*
//dsasrafsd
class MainActivity : AppCompatActivity() {

    //TODO Forbind API Til Messenger
    //TODO Forbind API Til Telegram
    //TODO Forbind API Til Snapchat
    //TODO Forbind API Til Whatsapp
    //TODO Forbind API Til Instagram
    //TODO Forbind API Til Besked
    //TODO Forbind API Til Opkald
    //TODO Forbind API Til E-mail

    //TODO Slå alle applikationer til / fra

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        val navController = findNavController(R.id.settings)
        setContentView(view)
        addTime()
    }

    private fun addTime() {
        binding.addTime.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeListen = TimePickerDialog.OnTimeSetListener{timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                binding.end.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeListen, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
            val secondTime = TimePickerDialog.OnTimeSetListener{timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                binding.start.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, secondTime, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()

        }
    }

    fun goToSetting() {

    }


}