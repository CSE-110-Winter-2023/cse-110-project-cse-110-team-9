package edu.ucsd.cse110.cse_110_project_cse_110_team_9.services;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Arrays;

public class LocationService implements LocationListener {

    final String[] REQUIRED_PERMISSIONS = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };


    // This needs to be more specific than just Activity for location permissions requesting.
    private final AppCompatActivity activity;

    private static LocationService instance;

    private MutableLiveData<Pair<Double, Double>> realLocationData;

    private MediatorLiveData<Pair<Double, Double>> locationData;

    private LiveData<Pair<Double,Double>> mockLocationData = null;


    private final LocationManager locationManager;

    public static LocationService singleton(AppCompatActivity activity) {
        if (instance == null) {
            instance = new LocationService(activity);
        }
        return instance;
    }

    /**
     * Constructor for LocationService
     *
     * @param activity Context needed to initiate LocationManager
     */
    protected LocationService(AppCompatActivity activity) {
        realLocationData = new MutableLiveData<>();
        locationData = new MediatorLiveData<>();
        locationData.addSource(realLocationData, locationData::postValue);



        this.activity = activity;
        this.locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        // Register sensor listeners
        withLocationPermissions(this::registerLocationListener);
    }

    /**
     * This will only be called when we for sure have permissions.
     */
    @RequiresPermission(anyOf = {ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION})
    private void registerLocationListener() {
        this.locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0,
                0.5f,
                LocationService.this);

        Location lastLocation = this.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (lastLocation != null) {
            this.locationData.postValue(new Pair<>(lastLocation.getLatitude(), lastLocation.getLongitude()));
        }


    }

    /**
     * Utility method for doing something with location permissions if we have them, and
     * after asking for them if we don't already.
     *
     * @param action the thing to do that needs permissions.
     */
    private void withLocationPermissions(Runnable action) {
        if (Arrays.stream(REQUIRED_PERMISSIONS).allMatch(perm -> activity.checkSelfPermission(perm) == PackageManager.PERMISSION_GRANTED)) {
            // We already have at least one of the location permissions, go ahead!
            action.run();
        } else {
            // We need to ask for permission first.
            // This is the call that requires AppCompatActivity and not just Activity!
            var launcher = activity.registerForActivityResult(new RequestMultiplePermissions(), grants -> {
                // At least one of the values in the Map<String, Boolean> grants needs to be true.
                if (grants.values().stream().noneMatch(isGranted -> isGranted)) {
                    // If you've landed here by denying it, you should grant it manually in settings or wipe data.
                   throw new IllegalStateException("App needs you to grant at least one location permission!");
                }
                // We have permission now, carry on!
                action.run();
            });
            launcher.launch(REQUIRED_PERMISSIONS);
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        this.locationData.postValue(new Pair<>(location.getLatitude(), location.getLongitude()));
    }

    private void unregisterLocationListener() {
        locationManager.removeUpdates(this);
    }

    public LiveData<Pair<Double, Double>> getLocation() {
        return this.locationData;
    }

    public void setMockLocationData(MutableLiveData<Pair<Double, Double>> mockLocationData) {
        unregisterLocationListener();
        this.mockLocationData = mockLocationData;
        locationData.removeSource(realLocationData);
        locationData.addSource(mockLocationData, locationData::postValue);
    }

    public void claerMockLocationSource(){
        withLocationPermissions(this::registerLocationListener);
        locationData.removeSource(mockLocationData);
        locationData.addSource(realLocationData, locationData::postValue);
    }
}
