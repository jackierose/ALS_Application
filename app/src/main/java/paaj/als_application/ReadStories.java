package paaj.als_application;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReadStories extends AppCompatActivity {
    ArrayAdapter<String> adapter;
    EditText editText;
    ArrayList<String> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_stories);

        Intent intent = getIntent();
        String title = intent.getStringExtra(MainPage.EXTRA_MESSAGE);

        EditText editText = (EditText)findViewById(R.id.specialEditText);
        editText.setText(title, TextView.BufferType.EDITABLE);


    }

}