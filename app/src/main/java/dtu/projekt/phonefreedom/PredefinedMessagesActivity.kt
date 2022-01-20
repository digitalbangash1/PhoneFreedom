package dtu.projekt.phonefreedom


import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.app.Dialog
import android.view.Window
import dtu.projekt.phonefreedom.notification_services.PreferencesManager


class PredefinedMessagesActivity : AppCompatActivity() {


    /**Declare fields so they can be seen from all functions etc */
    lateinit var listViewPredefinedMessage: ListView
    lateinit var messages: Array<String>
    val prefs : PreferencesManager = PreferencesManager.getPreferencesInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_predefined_messages)
        messages = prefs.predefinedMessages

        var adapter = PredefinedMessagesAdapter(this, messages)
        listViewPredefinedMessage = findViewById<ListView>(R.id.listViewPredefinedMessage)
        listViewPredefinedMessage.isClickable = true
        listViewPredefinedMessage.adapter = adapter
        listViewPredefinedMessage.setOnItemClickListener { parent, view, position, id ->
            //Toast.makeText(this, messages[position], Toast.LENGTH_SHORT).show()
            val mobilfri = resources.getString(R.string.standart)
            val message = mobilfri + messages[position]
            val myIntent = Intent(this, MainActivity::class.java)
            myIntent.putExtra("Extra_SelectedPredefinedMessage", message)
            setResult(Activity.RESULT_OK, myIntent)
            finish()

        }
    }


    class PredefinedMessagesAdapter(
        private val context: Activity,
        private val messages: Array<String>
    ) : ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, messages) {

       /** function to get the view of predefined messages */
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var inflater: LayoutInflater = LayoutInflater.from(context)
            var view: View =
                inflater.inflate(R.layout.activity_predefined_messages_list_item, null, false)
            var message: TextView = view.findViewById(R.id.textViewMessage)
            message.setText(messages[position])
            return view
        }
    }


     /** function to edit the predefined message */
    fun editClickListner(view: View) {
        val item = view.parent as View
        var pos = listViewPredefinedMessage.getPositionForView(item);
        var messageIndex = listViewPredefinedMessage.getItemIdAtPosition(pos).toInt();
        var messageToEdit = messages[messageIndex]

        showDialogForEditingMessage(messageToEdit, messageIndex)
    }


    /** function to show the edited predefined message */
    private fun showDialogForEditingMessage(message: String, position: Int) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)

        // dialog will show this layout from xml file
        dialog.setContentView(R.layout.edit_predefined_message) // <--------- define am xml file with this id that has an editText with ok and cancel buttons

        var editText = dialog.findViewById<EditText>(R.id.editTextForEditingPredefinedMessage) // <-- with this id for editText
        editText.setText(message)


        val yesButton = dialog.findViewById<Button>(R.id.buttonYesForEditingPredefinedMessage) // <-- a button with name id
        val noButton = dialog.findViewById<Button>(R.id.buttonNoForEditingPredefinedMessage) // <-- a button with this id

        yesButton.setOnClickListener {
            messages[position] =  editText.text.toString() // update text when users clicks on ok button and then close dialog
            prefs.setPredefinedMessages(messages)
            dialog.dismiss()

        }
        noButton.setOnClickListener { dialog.dismiss() } // just close dialog and do nothing when no button is clicked

        dialog.window
        //window.setLayout((ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        //dialog.getWindow()?.setLayout((6* width)/7,LinearLayout.LayoutParams.MATCH_PARENT)
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.show()
    }

}