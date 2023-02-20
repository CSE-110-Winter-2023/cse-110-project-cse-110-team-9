package edu.ucsd.cse110.cse_110_project_cse_110_team_9.services;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

public class OrientationService implements SensorEventListener {

    private static OrientationService instance;
    private final SensorManager sensorManager;

    private float[] accelerometerReading;
    private float[] magnetometerReading;

    private float[] mMatrixR = new float[9];
    private float[] mMatrixValues = new float[3];


    //LiveData variable which we return to clients of this service
    private MutableLiveData<Float> azimuth;

    private final MediatorLiveData<Float> azimuthData;

    private LiveData<Float> mockAzimuthData = null;


    protected OrientationService(Activity activity) {

        this.azimuth = new MutableLiveData<>();

        azimuthData = new MediatorLiveData<>();
        azimuthData.addSource(azimuth, azimuthData::postValue);
        this.sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        this.registerSensorListeners();

    }

    public void registerSensorListeners() {
        sensorManager.registerListener(this, sensorManager
                        .getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    public static OrientationService singleton(Activity activity) {
        if (instance == null) {
            instance = new OrientationService(activity);
        }
        return instance;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            SensorManager.getRotationMatrixFromVector(mMatrixR, event.values);

            SensorManager.getOrientation(mMatrixR, mMatrixValues);
            var val = (float) Math.toDegrees(mMatrixValues[0]); // orientation
            val = (val + 360) % 360;
            this.azimuth.postValue(val);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    public void unregisterSensorListeners() {
        sensorManager.unregisterListener(this);
    }

    public LiveData<Float> getOrientation() {
        return this.azimuth;
    }

    public void setMockOrientationSource(MutableLiveData<Float> mockAzimuthData) {
        this.mockAzimuthData = mockAzimuthData;
        unregisterSensorListeners();

        this.azimuth = mockAzimuthData;
        azimuthData.removeSource(azimuth);
        azimuthData.addSource(mockAzimuthData, azimuthData::postValue);
    }

    public void clearMockAzimuthData() {
        registerSensorListeners();
        azimuthData.removeSource(mockAzimuthData);
        azimuthData.addSource(azimuth, azimuthData::postValue);

    }

    public LiveData<Float> getAzimuthData() {
        return this.azimuthData;
    }

}


