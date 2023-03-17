package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import static org.junit.Assert.assertEquals;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowLooper;

import edu.ucsd.cse110.cse_110_project_cse_110_team_9.view.RingView;

@RunWith(RobolectricTestRunner.class)
public class ringZoomTest {
    @Test
    public void testSomething() {
        var scenario = ActivityScenario.launch(MainActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            RingView ringView = activity.findViewById(R.id.ringView);

            // Test zoom out button
            Constants.scale initialZoomLevel = ringView.getZoomLevel();
            ImageView zoomOutButton = activity.findViewById(R.id.zoom_out);
            zoomOutButton.performClick(); //click zoom out button

            //wait till UI is updated
            ShadowLooper.runUiThreadTasksIncludingDelayedTasks();

            Constants.scale expectedZoomLevel = Constants.scale.values()[initialZoomLevel.ordinal() + 1];
            Constants.scale zoomLevel = ringView.getZoomLevel();

            assertEquals(expectedZoomLevel, zoomLevel);

            // Test zoom in button
            ImageView zoomInButton = activity.findViewById(R.id.zoom_in);
            zoomInButton.performClick(); //click zoom out button

            //wait till UI is updated
            ShadowLooper.runUiThreadTasksIncludingDelayedTasks();

            Constants.scale expectedZoomLevel2 = Constants.scale.values()[zoomLevel.ordinal() - 1];
            Constants.scale zoomLevel2 = ringView.getZoomLevel();

            assertEquals(expectedZoomLevel2, zoomLevel2);
        });

    }

}

