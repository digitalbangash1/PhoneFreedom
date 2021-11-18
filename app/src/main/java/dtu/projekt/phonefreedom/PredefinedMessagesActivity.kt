package dtu.projekt.phonefreedom

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import dtu.projekt.phonefreedom.databinding.ActivityPredefinedMessagesBinding

class PredefinedMessagesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPredefinedMessagesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPredefinedMessagesBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_predefined_messages)

        var messages = arrayListOf<String>("One", "Two", "Three")
        binding.listViewPredefinedMessage.isClickable = true
        binding.listViewPredefinedMessage.adapter = PredefinedMessagesAdapter(this, messages)


    }
}

class PredefinedMessagesAdapter (
    private val context: Activity,
    private val messages: ArrayList<String>)
    : ArrayAdapter<String>(context, R.layout.activity_predefined_messages_list_item, messages){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var inflater : LayoutInflater = LayoutInflater.from(context)
        var view : View = inflater.inflate(R.layout.activity_predefined_messages_list_item, null)
        var message : TextView = view.findViewById(R.id.textViewMessage)
        message.setText(messages[position])

        return super.getView(position, convertView, parent)
    }
}