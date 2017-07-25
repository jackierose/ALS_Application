package paaj.als_application;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.affectiva.android.affdex.sdk.Frame;
import com.affectiva.android.affdex.sdk.detector.CameraDetector;
import com.affectiva.android.affdex.sdk.detector.Detector;
import com.affectiva.android.affdex.sdk.detector.Face;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReadStories extends AppCompatActivity implements Detector.ImageListener {
    ArrayAdapter<String> adapter;
    EditText editText;
    ArrayList<String> itemList;
    private static Context context;

    CameraDetector detector;
    SurfaceView cameraPreview;

    Button currentButton;
    Button [] buttonArray;
    int buttonNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_stories);
        context = getApplicationContext();
        currentButton = (Button)findViewById(R.id.button);
        buttonArray = new Button[]{(Button)findViewById(R.id.button),(Button)findViewById(R.id.button2),
                (Button)findViewById(R.id.button4),(Button)findViewById(R.id.button5),(Button)findViewById(R.id.button6)};

        Intent intent = getIntent();
        String title = intent.getStringExtra(MainPage.EXTRA_MESSAGE);

        EditText editText = (EditText)findViewById(R.id.specialEditText);
        editText.setText(title, TextView.BufferType.EDITABLE);

        int darkColor = ContextCompat.getColor(context,R.color.darkGray);
        currentButton.setBackgroundColor(darkColor);

        intializeUI();
        detector.start();

    }

    public void pushDown() {
        int thisColor = ContextCompat.getColor(context, R.color.darkGray);
        if(currentButton != null)
            currentButton.setBackgroundColor(thisColor);
    }

    public void selectButton() {
        int thisColor = ContextCompat.getColor(context, R.color.redGray);
        if(currentButton != null)
            currentButton.setBackgroundColor(thisColor);
    }


    public void chooseStory(View view) {
        Intent intent = new Intent(this, ReadAStory.class);
        startActivity(intent);

    }

    public void resetColor() {
        int i;
        for(i = 0; i < buttonArray.length; i++) {
            int lightColor = ContextCompat.getColor(context,R.color.lightGray);
            if(buttonArray[i] != null)
                buttonArray[i].setBackgroundColor(lightColor);
        }

    }





    private void intializeUI() {

        cameraPreview = (SurfaceView)findViewById(R.id.surfaceView);
        detector = new CameraDetector(this, CameraDetector.CameraType.CAMERA_FRONT, cameraPreview);
        detector.setImageListener(this);
        detector.setDetectSmile(true);
        detector.setDetectEyeClosure(true);
        detector.setDetectBrowRaise(true);
        detector.setMaxProcessRate(10);
    }

    int count = 0;

    @Override
    public void onImageResults(List<Face> faces, Frame frame, float v) {
        if (faces.size() == 0) {
        } else {
            count++;
            if (count % 3 == 0) {
                Face face = faces.get(0);
                if (face.expressions.getSmile() > 80) {
                    selectButton();
                    chooseStory(null);
                    // Do stuff when smile
                } else if (face.expressions.getBrowRaise() > 80) {
                    resetColor();
                    if (buttonNum < buttonArray.length - 1) {
                        buttonNum++;
                    } else {
                        buttonNum = 0;
                    }
                    currentButton = buttonArray[buttonNum];
                    pushDown();
                    // Do stuff when browraise
                } else if (face.expressions.getEyeClosure() > 80) {
                    resetColor();
                    if (buttonNum == 0) {
                        buttonNum = buttonArray.length - 1;
                    } else {
                        buttonNum--;
                    }
                    currentButton = buttonArray[buttonNum];
                    pushDown();
                    // Do suff when eye close
                }
            }

        }
    }
}