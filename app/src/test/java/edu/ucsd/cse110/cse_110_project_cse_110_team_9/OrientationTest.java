package edu.ucsd.cse110.cse_110_project_cse_110_team_9;


import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import androidx.test.core.app.ActivityScenario;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import static org.junit.Assert.assertEquals;

import java.sql.Time;

@RunWith(RobolectricTestRunner.class)
public class OrientationTest {





    @Test
    public void testShowNorth() {
        MutableLiveData<Float> mockDataSource = new MutableLiveData<Float>();

        // OrientationService orientationService = OrientationService.singleton();

      //TimeService timeService = TimeService.singleton();
     //   MutableLiveData<Long> mockTimeSource = new MutableLiveData<Long>();
       // timeService.setMockTimeSource(mockTimeSource);


        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {

                    OrientationService orientationService = OrientationService.singleton(activity);
                    orientationService.setMockOrientationSource(mockDataSource);
                    Float expected = (float) Math.PI / 2;
                    System.out.println(expected);

                    mockDataSource.setValue(expected);

                   // mockTimeSource.setValue(100L);
                   // Float observed = Float.parseFloat(textView.getText().toString());
                    // assertEquals(((float) Math.toDegrees(expected)
                });
    }

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






