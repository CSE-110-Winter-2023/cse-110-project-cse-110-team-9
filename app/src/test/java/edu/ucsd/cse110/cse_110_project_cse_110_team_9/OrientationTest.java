package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import static org.junit.Assert.assertEquals;

import android.util.Pair;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import androidx.test.core.app.ActivityScenario;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class OrientationTest {

    @Test
    public void testShowNorth() {



        MutableLiveData<Float> mockDataSource = new MutableLiveData<Float>();
        Float expected = (float) Math.PI / 2;
        mockDataSource.setValue(expected);
       // LocationService locationService;
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {


           LocationService locationService = LocationService.singleton(activity);
           locationService.setMockOrientationSource(new MutableLiveData<>(new Pair<>(1d,1d)));

            OrientationService orientationService = OrientationService.singleton(activity);
            orientationService.setMockOrientationSource(mockDataSource);
            //System.out.println(expected.floatValue());
            MutableLiveData<Float> orientationLiveData = (MutableLiveData<Float>) orientationService.getOrientation();
            //System.out.println(orientationLiveData.getValue());
            assertEquals(expected, orientationLiveData.getValue());
        });

    }
}
