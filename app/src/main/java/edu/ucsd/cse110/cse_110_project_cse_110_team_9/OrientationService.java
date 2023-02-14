package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.nio.channels.MulticastChannel;

public class OrientationService implements SensorEventListener {

    private static OrientationService instace;
    private final SensorManager sensorManager;

    private float[] accelerometerReading;
    private float[] magetometerReading;

    private MutableLiveData<Float> azimuth;

    protected OrientationService(Activity activity) {
        this.azimuth = new MutableLiveData<>();
        this.sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        this.registerSensorListeners();

    }

    public void registerSensorListeners() {
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);

    }

    public static OrientationService singleton(Activity activity){
        if (instace == null){
            instace = new OrientationService(activity);
        }
        return instace;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            accelerometerReading = event.values;
        }
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            magetometerReading = event.values;
        }
        if (accelerometerReading != null && magetometerReading != null){
            onBothSensorDataAvailabe();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        Log.d("Accuracy Change", Integer.toString(i));

        if (sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
        {
            Log.d("Accuracy Change", Integer.toString(i));
        }
    }


    private void onBothSensorDataAvailabe(){
        if (accelerometerReading == null || magetometerReading == null){
            throw new IllegalStateException("Both sensors must be avaible");
        }

        float[] r = new float[9];
        float[] i = new float[9];

        boolean success = SensorManager.getRotationMatrix(r,i, accelerometerReading, magetometerReading);
        if (success) {
            float[] orientation = new float[3];
            SensorManager.getOrientation(r, orientation);

            this.azimuth.postValue(orientation[0]);
        }
    }

    public void unregisterSensorListeners(){
        sensorManager.unregisterListener(this);
    }
    public LiveData<Float> getOrientation(){
        return this.azimuth;
    }

    public void setMockOrientationSource(MutableLiveData<Float> mockDataSource){
        unregisterSensorListeners();
        this.azimuth = mockDataSource;
    }


}


