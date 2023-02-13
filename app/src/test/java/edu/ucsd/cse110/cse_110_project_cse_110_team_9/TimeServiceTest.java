package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import androidx.test.core.app.ActivityScenario;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class TimeServiceTest
{

    @Test
    public void test_time_service()
    {
//        MutableLiveData<Long> mockDataSource = new MutableLiveData<Long>();
//        TimeService timeService = TimeService.singleton();
//
//        timeService.setMockTimeSource(mockDataSource);
//
//        ActivityScenario<MainActivity> senario = ActivityScenario.launch(MainActivity.class);
//        senario.moveToState(Lifecycle.State.STARTED);
//        senario.onActivity(activity -> {
//            long expected = 10L;
//            mockDataSource.setValue(expected);
//            TextView textView = activity.findViewById(R.id.textViewMain);
//            Long observed = Long.parseLong(textView.getText().toString());
//
//            assert observed == expected;
//        });
    }
}
