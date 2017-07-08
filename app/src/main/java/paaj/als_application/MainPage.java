package paaj.als_application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainPage extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
    }

    public void moveToWrite(View view) {
        Intent intent = new Intent(this, BuildStoryActivity.class);
        startActivity(intent);

    }

    public void moveToRead(View view) {
        Intent intent = new Intent(this, ReadStories.class);
        startActivity(intent);

    }


}
