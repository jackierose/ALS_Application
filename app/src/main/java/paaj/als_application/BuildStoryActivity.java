package paaj.als_application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    public char[][] layer = {{'a', 'b', 'c','a', 'b', 'c','a', 'b', 'c','a'},
                            {'d', 'e', 'f', 'g', 'h','d', 'e', 'f', 'g', 'h'},
                            {'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p','i', 'j'} ,
                            {'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'}};
    public char[] layer1 = {'a', 'b', 'c'};
    public char[] layer2 = {'d', 'e', 'f', 'g', 'h'};
    public char[] layer3 = {'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p'};
    public char[] layer4 = {'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    String[] words = {"my", "as", "the", "is", "foo", "name", "bar"};

    public int state = 0;
    boolean direction = false;
    boolean imageListnerController = true;

    CameraDetector detector;
    SurfaceView cameraPreview;

    TextView help;
    TextView help2;

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
        detector.setMaxProcessRate(10);
        help = (TextView)findViewById(R.id.help);
        help2 = (TextView)findViewById(R.id.help2);
    }

    protected void sendText(View view) {
       // resultArray += curr_char;
        Log.d("mytag", "RES ==== " + resultArray + "\n");
        x = 0;
        y = 0;

        if (result.length() == 0) {
            result += words[z];
        } else {
            result += " " + words[z];
        }
        help2.setText(result);
        z = 0;
        //imageListnerController = true;
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

        help.setText(words[z]);
        help2.setText(result);
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        if (faces.size() == 0) {

        } else {
            count++;
            Log.d("mytag", "Ps55555ttt ==== \n");
           if (count % 3 == 0) {
               Log.d("mytag", "Psttt ==== \n");
                Face face = faces.get(0);

                if (face.expressions.getBrowRaise() > 80) {
                   // imageListnerController = false;
                    Log.d("mytag", "brow\n");
                    moveRight();

            //        imageListnerController = false;
                } else if (face.expressions.getSmile() > 80) {
                   // imageListnerController = false;
                    sendText(null);
                    state = 0;
          //          imageListnerController = false;
                } else if (face.expressions.getEyeClosure() > 80) {
                    Log.d("mytag", "eye\n");
                    //  imageListnerController = false;
                    moveLeft();
         //           imageListnerController = false;
                } else if (face.expressions.getMouthOpen() > 90) {
                    libraryMove(null);
                }

           }

        }
    }

    public void moveRight() {
        z++;
        if (z >= words.length) {
            z = 0;
        }

        help.setText(words[z]);
        help2.setText(result);
    }

    public void moveLeft() {
        z--;
        if (z < 0) {
            z = words.length - 1;
        }

        help.setText(words[z]);
        help2.setText(result);
    }

}
