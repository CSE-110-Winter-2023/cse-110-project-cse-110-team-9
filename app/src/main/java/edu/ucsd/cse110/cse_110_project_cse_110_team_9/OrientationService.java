package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

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
//        sensorManager.registerListener(this,
//                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
//        sensorManager.registerListener(this,
//                sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_GAME);

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

//        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
//            // If we only have this sensor, we can't compute the orientation with it alone.
//            // But we should still save it for later.
//            accelerometerReading = event.values;
//

//
//        }
//        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
//            // If we only have this sensor, we can't compute the orientation with it alone.
//            // But we should still save it for later.
//            magnetometerReading = event.values;
//
//        }
//        if (accelerometerReading != null && magnetometerReading != null) {
//            // We have both sensors, so we can compute the orientation!
//            onBothSensorDataAvailable();
//        }

        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            SensorManager.getRotationMatrixFromVector(mMatrixR, event.values);

            SensorManager.getOrientation(mMatrixR, mMatrixValues);

//            mAzimuth[0] = alpha * mAzimuth[0] + (1 - alpha)
//                    * mMatrixValues[0];

            var val = (float) Math.toDegrees(mMatrixValues[0]); // orientation
            val = (val + 360) % 360;
            this.azimuth.postValue(val);

            // Use this value in degrees
            // mAzimuth = Math.toDegrees(mMatrixValues[0]);
        }

    }

//    private void onBothSensorDataAvailable() {
//        // Discount contract checking. Think Design by Contract!
//        if (accelerometerReading == null || magnetometerReading == null) {
//            throw new IllegalStateException("Both sensors must be available to compute orientation.");
//        }
//
//        var r = new float[9];
//        var i = new float[9];
//        // Now we do some linear algebra magic using the two sensor readings.
//        var success = SensorManager.getRotationMatrix(r, i, accelerometerReading, magnetometerReading);
//        // Did it work?
//        if (success) {
//            // Ok we're good to go!
//            var orientation = new float[3];
//            SensorManager.getOrientation(r, orientation);
//
//            // Orientation now contains in order: azimuth, pitch and roll.
//            // These are coordinates in a 3D space commonly used by aircraft...
//            // but we only care about azimuth.
//            // Azimuth is the angle between the magnetic north pole and the y-axis,
//            // around the z-axis (-π to π).
//            // An azimuth of 0 means that the device is pointed north, and π means it's pointed south.
//            // π/2 means it's pointed east, and 3π/2 means it's pointed west.
//            var val = (float) Math.toDegrees(orientation[0]); // orientation
//             val = (val + 360) % 360;
//            this.azimuth.postValue(val);
//        }
//    }

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


