package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import static org.junit.Assert.assertEquals;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import androidx.test.core.app.ActivityScenario;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import edu.ucsd.cse110.cse_110_project_cse_110_team_9.services.TimeService;

@RunWith(RobolectricTestRunner.class)
public class TimeServiceTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Rule
    public GrantPermissionRule mRuntimePermissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION);

    @Test
    public void test_time_service() {
        var testValue = 12312312L;

        // Update the singleton instance to use the mockDataSource
        var scenario = ActivityScenario.launch(MainActivity.class);

        // You need to have the Activity in STARTED state for the
        // LiveData observer to be active.
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            var timeService = TimeService.singleton();

            var mockTime = new MutableLiveData<Long>();
            timeService.setMockTimeSource(mockTime);
            // No need to worry about telling the activity to refresh where it gets data.

            mockTime.setValue(testValue);
//            TextView textView = activity.findViewById(R.id.timeText);
//

           Long observered=  timeService.getTimeData().getValue();
           assert observered == testValue;
//            var observed = textView.getText().toString();
//
//            assertEquals(expected, observed);
        });
    }
}
