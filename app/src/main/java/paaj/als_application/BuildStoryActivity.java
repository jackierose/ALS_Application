package paaj.als_application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.affectiva.android.affdex.sdk.Frame;
import com.affectiva.android.affdex.sdk.detector.CameraDetector;
import com.affectiva.android.affdex.sdk.detector.Detector;
import com.affectiva.android.affdex.sdk.detector.Face;

import java.util.List;



public class BuildStoryActivity extends AppCompatActivity implements Detector.ImageListener {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    String storyTitle = "bibbity bobbety boo she said";

    int x,y,z = 0;

    public String[][] layer = {
            {"Ways", "Five", "M","To", "CS", "Teach","Me", "D"},
            {"P", "Y", "R", "X", "F","W", "B", "C"},
            {"T", "S", "G", "Q", "V", "S", "L", "H"} ,
            {"E", "I", "J", "K", "O", "N", "U", "A"}};

    //String[] words = {"my", "as", "the", "is", "foo", "name", "bar"};

    public int state = 0;
    boolean direction = false;
    boolean imageListnerController = true;

    CameraDetector detector;
    SurfaceView cameraPreview;

    TextView help;
    EditText help2;

    String curr_char = "";
    String resultArray = "";
    String result = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_story);

        intializeUI();
        detector.start();

        //keyboard_layer();
    }


    private void intializeUI() {

        cameraPreview = (SurfaceView)findViewById(R.id.camerapreview);
        detector = new CameraDetector(this, CameraDetector.CameraType.CAMERA_FRONT, cameraPreview);
        detector.setImageListener(this);
        detector.setDetectSmile(true);
        detector.setDetectEyeClosure(true);
        detector.setDetectMouthOpen(true);
        detector.setDetectBrowRaise(true);
        detector.setDetectSmirk(true);
        detector.setDetectLipPress(true);
        detector.setMaxProcessRate(10);
        help = (TextView)findViewById(R.id.help);
        help2 = (EditText) findViewById(R.id.help2);
    }


    protected void sendText(View view) {
        // resultArray += curr_char;
        Log.d("mytag", "RES ==== " + resultArray + "\n");
        //x = 0;
        //y = 0;

        if (result.length() == 0) {
            result += layer[x][y];
        } else {
            result += " " + layer[x][y];
        }

        help2.setText(result);
        help2.setSelection(result.length());
    }


    public void libraryMove(View view) {
        TextView txtView = (TextView) findViewById(R.id.help2);
        storyTitle = txtView.getText().toString();

        if(storyTitle.length() <= 20) {
            storyTitle = storyTitle.concat("...");
        } else {
            storyTitle = storyTitle.substring(0, 20);
            storyTitle = storyTitle.concat("...");
        }

        Intent intent = new Intent(this, ReadStories.class);
        intent.putExtra(EXTRA_MESSAGE, storyTitle);
        startActivity(intent);
    }


    int count = 0;

    @Override
    public void onImageResults(List<Face> faces, Frame frame, float v) {

        help.setText(layer[x][y]);
        //help2.setText(result);
        if (faces.size() == 0) {

        } else {
            count++;
            Log.d("mytag", "Ps55555ttt ==== \n");
            if (count % 3 == 0) {
                Log.d("mytag", "Psttt ==== \n");
                Face face = faces.get(0);

                if (face.expressions.getBrowRaise() > 80) {
                    Log.d("mytag", "brow\n");
                    moveRight();
                } else if (face.expressions.getSmile() > 80) {
                    Log.d("mytag", "smile\n");
                    sendText(null);
                } else if (face.expressions.getEyeClosure() > 80) {
                    Log.d("mytag", "eye\n");
                    moveVertical();
                } else if (face.expressions.getMouthOpen() > 90) {
                    Log.d("mytag", "mouth\n");
                    moveLeft();
                } else if (face.expressions.getLipPress() > 80) {
                    Log.d("mytag", "lippres\n");
                    detector.stop();
                    libraryMove(null);
                }

            }

        }
    }

    public void moveRight() {
        y++;
        if (y >= layer[0].length) {
            y = 0;
        }

        help.setText(layer[x][y]);
        //help2.setText(result);
    }

    public void moveLeft() {
        y--;
        if (y < 0) {
            y = layer[0].length - 1;
        }

        help.setText(layer[x][y]);
        //help2.setText(result);
    }

    public void moveVertical()
    {
        x++;
        if (x >= layer.length)
        {
            x = 0;
        }

        help.setText(layer[x][y]);
        //help2.setText(result);
    }

}
