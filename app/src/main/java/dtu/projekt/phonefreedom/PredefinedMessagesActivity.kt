package dtu.projekt.phonefreedom

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import dtu.projekt.phonefreedom.databinding.ActivityPredefinedMessagesBinding

class PredefinedMessagesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_predefined_messages)

        var messages = arrayOf<String>("One", "Two", "Three","One", "Two", "Three","One", "Two", "Three")
        var adapter = PredefinedMessagesAdapter(this, messages)
        var listViewPredefinedMessage = findViewById<ListView>(R.id.listViewPredefinedMessage)
        listViewPredefinedMessage.isClickable = true
        listViewPredefinedMessage.adapter = adapter
        listViewPredefinedMessage.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this, messages[position], Toast.LENGTH_SHORT).show()
        }
    }

class PredefinedMessagesAdapter (
    private val context: Activity,
    private val messages: Array<String>)
    : ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, messages) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var inflater : LayoutInflater = LayoutInflater.from(context)
        var view : View = inflater.inflate(R.layout.activity_predefined_messages_list_item, null, false)
        var message : TextView = view.findViewById(R.id.textViewMessage)
        message.setText(messages[position])
        return view
    }


}

}

