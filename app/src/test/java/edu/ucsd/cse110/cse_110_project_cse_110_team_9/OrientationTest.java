package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import androidx.test.core.app.ActivityScenario;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import edu.ucsd.cse110.cse_110_project_cse_110_team_9.services.OrientationService;


@RunWith(RobolectricTestRunner.class)
public class OrientationTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Rule
    public GrantPermissionRule mRuntimePermissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION);
    @Test
    public void test_orientation_service() {
        float testValue = 180;

        var scenario = ActivityScenario.launch(NameActivity.class);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            var orientationService = OrientationService.singleton(activity);

            var mockOrientation = new MutableLiveData<Float>();
            orientationService.setMockOrientationSource(mockOrientation);

            var latch = new CountDownLatch(1);

            mockOrientation.observe(activity, val ->{
                assertEquals(val, testValue, 0);
                latch.countDown();
            });

            mockOrientation.setValue(testValue);

            try {
                var hitZeroWithoutTimingOut = latch.await(100, TimeUnit.MILLISECONDS);
                if (!hitZeroWithoutTimingOut) {
                    fail("Did not get update from LiveData.");
                }
            } catch (InterruptedException e) {
                fail("Test interrupted.");
            }


            //TextView textView = activity.findViewById(R.id.orientationText);

            //var expected = Utilities.formatOrientation(testValue);
           // var observed = textView.getText().toString();

//            var observed = compassView.getDegrees();
//            assertEquals(testValue, observed, 0);
        });
    }
}
