package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private TimeService timeService;
    private OrientationService orientationService;
    private float currentOrientation = 0f;
    public float orientation = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        timeService = TimeService.singleton();
        TextView textView = findViewById(R.id.textViewMain);

        timeService.getTime().observe(this, time -> {


            float deg = (float) Math.toDegrees(orientation);

            textView.setText(Float.toString(-deg));

            RotateAnimation rotateAnimation = new RotateAnimation(currentOrientation,
                    -deg, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(50);
            rotateAnimation.setFillAfter(true);
            ImageView img = findViewById(R.id.imageView);
            img.startAnimation(rotateAnimation);
            currentOrientation = -deg;

        });


        orientationService = OrientationService.singleton(this);

        orientationService.getOrientation().observe(this, orientation -> {
            // TextView view = findViewById(R.id.orientationView);
           // view.setText(Float.toString(orientation));
            this.orientation = orientation;



        });
//        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
//        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);
//    }
    }

        @Override
        protected void onPause ()
        {
            super.onPause();
            orientationService.unregisterSensorListeners();
        }
    }