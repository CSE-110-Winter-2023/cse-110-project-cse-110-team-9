package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.location.LocationManager;
import android.os.SystemClock;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import android.location.Location;

@RunWith(RobolectricTestRunner.class)
public class gpsSignalTest {
    @Test
    public void testGpsSignal() {
        Context context = ApplicationProvider.getApplicationContext();

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationManager.addTestProvider(LocationManager.GPS_PROVIDER, false, false, false, false, false, true, true, 1, 1);
        locationManager.setTestProviderEnabled(LocationManager.GPS_PROVIDER, true);

        Location mockLocation = new Location(LocationManager.GPS_PROVIDER);
        mockLocation.setLatitude(32.715736);
        mockLocation.setLongitude(-117.161087);
        mockLocation.setTime(System.currentTimeMillis());
        mockLocation.setAccuracy(1.0f);

        // Set all other properties again
        mockLocation.setAltitude(0);
        mockLocation.setBearing(0);
        mockLocation.setSpeed(0);

        locationManager.setTestProviderLocation(LocationManager.GPS_PROVIDER, mockLocation);
        // Enable GPS provider to simulate signal restoration
        locationManager.setTestProviderEnabled(LocationManager.GPS_PROVIDER, true);
        // Wait for 10 seconds
        SystemClock.sleep(10000);
        // Disable GPS provider to simulate loss of signal
        locationManager.setTestProviderEnabled(LocationManager.GPS_PROVIDER, false);

        // Wait for 20 seconds
        SystemClock.sleep(20000);

        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        // Obtain a reference to the activity's view
        scenario.onActivity(activity -> {
            TextView myTextView = activity.findViewById(R.id.lastLive);
            String offlineTimeText = myTextView.getText().toString();
            assertEquals("20 seconds", offlineTimeText);
        });

        // Enable GPS provider to simulate signal restoration
        locationManager.setTestProviderEnabled(LocationManager.GPS_PROVIDER, true);

    }
}



