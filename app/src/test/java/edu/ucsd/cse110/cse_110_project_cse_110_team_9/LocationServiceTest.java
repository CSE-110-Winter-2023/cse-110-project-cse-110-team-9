package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import android.widget.TextView;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.core.util.Pair;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import androidx.test.core.app.ActivityScenario;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.time.Instant;

import edu.ucsd.cse110.cse_110_project_cse_110_team_9.services.LocationService;
import kotlin.Triple;


@RunWith(RobolectricTestRunner.class)

public class LocationServiceTest {

   @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Rule
    public GrantPermissionRule mRuntimePermissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION);

    @Test
    public void test_location_service() {


         double latitude = 31.8801;
         double longitude = -117.2340;
        var scenario = ActivityScenario.launch(MainActivity.class);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {

           var locationService = LocationService.singleton(activity);
           var mockLocation = new MutableLiveData<Triple<Double, Double, Long>>();
          locationService.setMockLocationData(mockLocation);

          mockLocation.setValue(new Triple<>(latitude, longitude, Instant.now().getEpochSecond()));
            TextView locationView = activity.findViewById(R.id.locationText);

            var expected = Utilities.formatLocation(latitude, longitude);
          var observed = locationView.getText().toString();
          assertEquals(expected,observed);


        });
    }

}


