package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private TimeService timeService;
    private OrientationService orientationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeService = TimeService.singleton();
        TextView textView = findViewById(R.id.textViewMain);
        timeService.getTime().observe(this, time -> {
            textView.setText(Long.toString(time));
        });


        orientationService = OrientationService.singleton(this);

        orientationService.getOrientation().observe(this, orientation -> {
            textView.setText(Float.toString(orientation));
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