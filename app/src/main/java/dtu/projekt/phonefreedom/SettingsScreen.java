package dtu.projekt.phonefreedom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class SettingsScreen extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_screen);

        AutoCompleteTextView LangSet = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(SettingsScreen.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Animation_selector));
        myAdapter.setDropDownViewResource(R.layout.dropdown_item);
        LangSet.setAdapter(myAdapter);


    }
}