package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

import android.content.Context;
import android.location.LocationManager;

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

import java.time.Duration;
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
public class gpsTimeOfflineTest {

    private SocialCompassDao dao;
    private SocialCompassDatabase db;

    private SocialCompassRepository repo;

    private LifecycleOwner lifeCycleOwner;
    private LifecycleRegistry lifecycle;


    @Before
    public void createDb() {
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
    public void testGPSTimeAndDot(){
        String public_uid1 = UUID.randomUUID().toString();
        String private_code1 = UUID.randomUUID().toString();
        User insertedUser = new User("Kalam", private_code1, public_uid1,
                69.0d, 69.0d, Instant.now().getEpochSecond());

        dao.upsertUser(insertedUser);

        // Retrieve user again from database
        User retrievedUser = dao.getUser();

        //check for 0.0 min
        long timeNow = Instant.now().getEpochSecond();
        long diff = timeNow - retrievedUser.getUpdated_at();
        double timeInMinutes =  Math.floor((double) diff/30) / 2;

        Double expectedTime = 0.0;
        assertEquals(expectedTime, timeInMinutes, 0.01);

        //check for 0.5 min
        retrievedUser.setUpdated_at(Instant.now().getEpochSecond()-30);
        timeNow = Instant.now().getEpochSecond();
        diff = timeNow - retrievedUser.getUpdated_at();
        timeInMinutes =  Math.floor((double) diff/30) / 2;

        expectedTime = 0.5;
        assertEquals(expectedTime, timeInMinutes, 0.01);

        //check for 1.0 min
        retrievedUser.setUpdated_at(Instant.now().getEpochSecond()-60);
        timeNow = Instant.now().getEpochSecond();
        diff = timeNow - retrievedUser.getUpdated_at();
        timeInMinutes =  Math.floor((double) diff/30) / 2;

        expectedTime = 1.0;
        assertEquals(expectedTime, timeInMinutes, 0.01);

        //check for 2.0 min
        retrievedUser.setUpdated_at(Instant.now().getEpochSecond()-120);
        timeNow = Instant.now().getEpochSecond();
        diff = timeNow - retrievedUser.getUpdated_at();
        timeInMinutes =  Math.floor((double) diff/30) / 2;

        expectedTime = 2.0;
        assertEquals(expectedTime, timeInMinutes, 0.01);

        // Check that the imageView's visibility is set to View.VISIBLE after the GPS signal loss
        //ImageView redDotImageView = activity.findViewById(R.id.gpsnotLive);
        //assertEquals(View.VISIBLE, redDotImageView.getVisibility());
    }
}