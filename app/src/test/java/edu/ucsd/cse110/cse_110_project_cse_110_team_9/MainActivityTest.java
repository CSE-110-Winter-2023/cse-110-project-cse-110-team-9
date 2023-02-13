package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import static org.junit.Assert.assertEquals;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {
    @Test
    public void test1(){
        assertEquals(10,10);
    }


//    @Test
//    public void testViewModel()
//    {
//        try(ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
//            scenario.onActivity(activity -> {
//                EditText pointlabel = activity.findViewById(R.id.enterPointLabel);
//                pointlabel.setText("Test");
//                EditText lat = activity.findViewById(R.id.enterLatitudeBox);
//                lat.setText("32.8801");
//                EditText longitude = activity.findViewById(R.id.enterLongitudeBox);
//
//
//            });
//        }
//
//        assertEquals(10,10);
//    }
}
