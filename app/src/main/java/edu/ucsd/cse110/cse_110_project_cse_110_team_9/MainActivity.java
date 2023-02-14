package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {

    private TimeService timeService;
    private OrientationService orientationService;
    private float currentOrientation = 0f;
    private float orientation = 0f;

    private LocationService locationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Main Activity", "main activity launched");

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 200);
        }

        locationService = LocationService.singleton(this);

        TextView textViewLoc = (TextView) findViewById(R.id.textViewLoc);
        locationService.getLocation().observe(this, loc -> {
            textViewLoc.setText(Double.toString(loc.first) + " " + Double.toString(loc.second));
        });

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
            this.orientation = orientation;


        });
//        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
//        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);
//    }
    }

    @Override
    protected void onPause() {
        super.onPause();
        orientationService.unregisterSensorListeners();
        locationService.unregisterLocationListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        orientationService.registerSensorListeners();
        locationService.registerLocationListener();
    }
}