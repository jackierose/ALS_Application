package paaj.als_application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import com.affectiva.android.affdex.sdk.Frame;
import com.affectiva.android.affdex.sdk.detector.CameraDetector;
import com.affectiva.android.affdex.sdk.detector.Detector;
import com.affectiva.android.affdex.sdk.detector.Face;

import org.w3c.dom.Text;

import java.util.List;

public class MainPage extends AppCompatActivity implements Detector.ImageListener {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    CameraDetector detector;
    SurfaceView cameraPreview;
    TextView textview;

    boolean movedFromView = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        textview = (TextView)findViewById(R.id.textView);
        intializeUI();
        detector.start();
    }

    private void intializeUI() {

        cameraPreview = (SurfaceView)findViewById(R.id.cameraview);
        detector = new CameraDetector(this, CameraDetector.CameraType.CAMERA_FRONT, cameraPreview);
        detector.setImageListener(this);
        detector.setDetectSmile(true);
        detector.setDetectEyeClosure(true);
        detector.setDetectMouthOpen(true);
        detector.setDetectBrowRaise(true);
        detector.setDetectSmirk(true);
        detector.setDetectLipPress(true);
        detector.setMaxProcessRate(10);
    }




    public void moveToWrite(View view) {
        Intent intent = new Intent(this, BuildStoryActivity.class);
        startActivity(intent);

    }

    public void moveToRead(View view) {
        Intent intent = new Intent(this, ReadStories.class);
        startActivity(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        movedFromView = false;
        //if (!detector.isRunning()) {
          //  detector.start();
        //}
    }

    @Override
    public void onImageResults(List<Face> faces, Frame frame, float v) {
        String result = "";
        if (faces.size() == 0) {
        } else {
            Face face = faces.get(0);
            if (face.expressions.getSmile() > 60) {

                if (!movedFromView) {
                    movedFromView = true;
                    detector.stop();
                    moveToWrite(null);
                }


            } else if (face.expressions.getBrowRaise() > 60) {
                if (!movedFromView) {
                    movedFromView = true;
                    detector.stop();
                    moveToRead(null);
                }

            }

        }

//        textview.setText(String.format("S: %.2f, MO: %.2f, br: %.2f", detector.getDetectSmile(), detector.getDetectMouthOpen(), detector.getDetectBrowRaise()));

    }
}
