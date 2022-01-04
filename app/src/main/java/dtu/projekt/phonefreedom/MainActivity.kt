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