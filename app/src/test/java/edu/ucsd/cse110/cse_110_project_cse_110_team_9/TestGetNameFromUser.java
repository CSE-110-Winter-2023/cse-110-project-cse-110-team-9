package edu.ucsd.cse110.cse_110_project_cse_110_team_9;


import static org.junit.Assert.assertEquals;

import android.widget.Button;
import android.widget.EditText;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.internal.bytecode.RoboCallSite;

import java.util.jar.Attributes;

@RunWith(RobolectricTestRunner.class)
public class TestGetNameFromUser
{
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Test
    public void testGetName() {
        var scenario = ActivityScenario.launch(NameActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.moveToState(Lifecycle.State.RESUMED);

        scenario.onActivity(activity -> {

            EditText enterName = activity.findViewById(R.id.enter_name);
            String name = "Jerry";
            enterName.setText(name);
            Button saveName = activity.findViewById(R.id.setNameButton);

            saveName.performClick();
           String nameFromExtra = activity.getIntent().getExtras().getString("name");

           assertEquals("name in text field and name in extra not equal", name, nameFromExtra);

        });


    }


}
