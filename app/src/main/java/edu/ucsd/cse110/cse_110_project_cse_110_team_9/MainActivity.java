package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.util.Pair;

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
    private float orientation = 0f;
    private float previous_orientation = 0f;


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
        this.reobserveLocation();

//        TextView textViewLoc = (TextView) findViewById(R.id.textViewLoc);
//        locationService.getLocation().observe(this, loc -> {
//            textViewLoc.setText(Double.toString(loc.first) + " " + Double.toString(loc.second));
//        });

        timeService = TimeService.singleton();
        var timeData = timeService.getTimeData();
        timeData.observe(this, this::onTimeChanged);
        TextView textView = findViewById(R.id.textViewMain);

        CompassView compass = findViewById(R.id.compass);
        compass.setRangeDegrees(360);
//        timeService.getTime().observe(this, time -> {
//
//
//            float deg = (float) Math.toDegrees(orientation);
//
//            textView.setText(Float.toString(-deg));
//
//
////            currentOrientation = -deg;
//            compass.setDegrees(-deg, true);
//
//        });


        orientationService = OrientationService.singleton(this);
        var azimuthData = orientationService.getAzimuthData();
        azimuthData.observe(this, this::OnOrientationChanged);

    }

    private void OnOrientationChanged(Float azimuth)
    {
        CompassView compass = findViewById(R.id.compass);

        //float deg = (float) Math.toDegrees(azimuth);
        compass.setDegrees(azimuth , true);

        orientation = azimuth;

    }


    private void onLocationChanged(Pair<Double, Double> latLong) {
        TextView locationText = findViewById(R.id.locationText);
        locationText.setText(Utilities.formatLocation(latLong.first, latLong.second));


    }

    private void reobserveLocation() {
        var locationData = locationService.getLocation();
        locationData.observe(this, this::onLocationChanged);
    }

    private void onTimeChanged(Long time) {
        //TextView timeText = findViewById(R.id.timeText);
        //timeText.setText(Utilities.formatTime(time));


        ImageView img = findViewById(R.id.compassImg);
        RotateAnimation rotateAnimation = new RotateAnimation(previous_orientation,
                orientation, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(100);
        rotateAnimation.setFillAfter(true);
        img.startAnimation(rotateAnimation);

        previous_orientation = orientation;
    }

    @Override
    protected void onPause() {
        super.onPause();
        orientationService.unregisterSensorListeners();

    }

    @Override
    protected void onResume() {
        super.onResume();
        orientationService.registerSensorListeners();
    }
}