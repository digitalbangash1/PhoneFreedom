package dtu.projekt.phonefreedom

import android.R.attr
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import dtu.projekt.phonefreedom.databinding.ActivityPredefinedMessagesBinding
import android.R.attr.data




class PredefinedMessagesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_predefined_messages)

        var messages = arrayOf<String>("Fordi jeg spiser", "jeg gider ikke","One", "Two", "Three","One", "Two", "Three")
        var adapter = PredefinedMessagesAdapter(this, messages)
        var listViewPredefinedMessage = findViewById<ListView>(R.id.listViewPredefinedMessage)
        listViewPredefinedMessage.isClickable = true
        listViewPredefinedMessage.adapter = adapter
        listViewPredefinedMessage.setOnItemClickListener { parent, view, position, id ->
            //Toast.makeText(this, messages[position], Toast.LENGTH_SHORT).show()
            var message = "Jeg holder mobilfri ... " + messages[position]
            val myIntent = Intent(this, MainActivity::class.java)
            myIntent.putExtra("Extra_SelectedPredefinedMessage", message)
            setResult(Activity.RESULT_OK, myIntent);
            finish();
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

