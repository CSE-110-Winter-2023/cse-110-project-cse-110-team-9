package edu.ucsd.cse110.cse_110_project_cse_110_team_9;


import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

import android.content.Context;
import android.location.LocationManager;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import edu.ucsd.cse110.cse_110_project_cse_110_team_9.database.SocialCompassRepository;
import edu.ucsd.cse110.cse_110_project_cse_110_team_9.database.User;
import edu.ucsd.cse110.cse_110_project_cse_110_team_9.database.SocialCompassDao;
import edu.ucsd.cse110.cse_110_project_cse_110_team_9.database.SocialCompassDatabase;

import org.robolectric.Robolectric;
import org.robolectric.shadows.ShadowLocationManager;
import org.robolectric.shadows.ShadowSystemClock;

@RunWith(AndroidJUnit4.class)
public class gpsSignalTest {

    private SocialCompassDao dao;
    private SocialCompassDatabase db;

    private SocialCompassRepository repo;

    private LifecycleOwner lifeCycleOwner;
    private LifecycleRegistry lifecycle;


    @Before
    public void createDb()
    {
        Context context = ApplicationProvider.getApplicationContext();

        db = Room.inMemoryDatabaseBuilder(context, SocialCompassDatabase.class)
                .allowMainThreadQueries().build();
        dao = db.getDao();

        repo = new SocialCompassRepository(dao);
        lifeCycleOwner = Mockito.mock(LifecycleOwner.class);
        lifecycle = new LifecycleRegistry(Mockito.mock(LifecycleOwner.class));
        lifecycle.setCurrentState(Lifecycle.State.RESUMED);
        Mockito.when(lifeCycleOwner.getLifecycle()).thenReturn(lifecycle);
    }

    @Test
    public void testGPS(){
        String public_uid1 = UUID.randomUUID().toString();
        String private_code1 = UUID.randomUUID().toString();
        User insertedUser = new User("Kalam", private_code1, public_uid1,
                69.0d, 69.0d, Instant.now().getEpochSecond());

        dao.upsertUser(insertedUser);

        // Simulate GPS signal loss
        LocationManager locationManager = (LocationManager)
                ApplicationProvider.getApplicationContext()
                        .getSystemService(Context.LOCATION_SERVICE);
        ShadowLocationManager shadowLocationManager = shadowOf(locationManager);
        shadowLocationManager.setProviderEnabled(LocationManager.GPS_PROVIDER, false);

        User retrievedUser = dao.getUser();

        // Check that the retrieved user has the same location as the inserted user
        assertEquals(insertedUser.getLongitude(), retrievedUser.getLongitude(), 0.01);
        assertEquals(insertedUser.getLatitude(), retrievedUser.getLatitude(), 0.01);

        // Check that the retrieved user's location was not updated after the GPS signal loss
        long lastUpdatedTime = retrievedUser.getUpdated_at();
        shadowOf(Looper.getMainLooper()).idle();
        assertEquals(lastUpdatedTime, retrievedUser.getUpdated_at());

    }

    @Test
    public void testTimerAndDot() throws InterruptedException {
        // Create a new instance of MainActivity
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).create().visible().get();

        // Get the TextView and ImageView from the activity
        TextView timerTextView = activity.findViewById(R.id.lastLive);
        ImageView gpsStatusImageView = activity.findViewById(R.id.gpsnotLive);

        String public_uid1 = UUID.randomUUID().toString();
        String private_code1 = UUID.randomUUID().toString();
        User insertedUser = new User("Kalam", private_code1, public_uid1,
                69.0d, 69.0d, Instant.now().getEpochSecond());

        dao.upsertUser(insertedUser);

        LocationManager locationManager = (LocationManager)
                ApplicationProvider.getApplicationContext()
                        .getSystemService(Context.LOCATION_SERVICE);
        ShadowLocationManager shadowLocationManager = shadowOf(locationManager);
        shadowLocationManager.setProviderEnabled(LocationManager.GPS_PROVIDER, false);
        System.out.println(insertedUser.getUpdated_at());
        // Call the onTimeChanged method to update the UI
        activity.onTimeChanged(Instant.now().plusSeconds(10).getEpochSecond());
        insertedUser.setUpdated_at(Instant.now().minusSeconds(10).getEpochSecond());


        System.out.println(insertedUser.getUpdated_at());


        // Check that the timer TextView shows the correct time
        assertEquals("0.0 minutes", timerTextView.getText().toString());

        // Check that the GPS status ImageView shows a red dot
        assertEquals(R.drawable.reddot, shadowOf(gpsStatusImageView.getDrawable()).getCreatedFromResId());

    }
}



