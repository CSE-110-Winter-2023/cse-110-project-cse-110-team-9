package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.SdkSuppress;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

import java.io.IOException;


@RunWith(AndroidJUnit4.class)
public class UiTests {

    private static final String BASIC_SAMPLE_PACKAGE = "edu.ucsd.cse110.cse_110_project_cse_110_team_9";

    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String STRING_TO_BE_TYPED = "UiAutomator";
    private UiDevice device;




    @Test
    public void runTests() throws UiObjectNotFoundException {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        Context context = getInstrumentation().getContext();
    Intent intent = context.getPackageManager()
            .getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    context.startActivity(intent);
    device.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)),LAUNCH_TIMEOUT);

        UiObject editName = device.findObject(new UiSelector().resourceId("edu.ucsd.cse110.cse_110_project_cse_110_team_9:id/enter_name"));
        editName.setText("Duke");

        UiObject saveName = device.findObject(new UiSelector().resourceId("edu.ucsd.cse110.cse_110_project_cse_110_team_9:id/setNameButton"));

        saveName.click();

        UiObject acceptPermButton = device.findObject(
                new UiSelector().resourceId("com.android.permissioncontroller:id/permission_allow_foreground_only_button"));

        if (acceptPermButton.exists())
        {
            acceptPermButton.click();
        }

        UiObject public_uid_text_view = device.findObject(new UiSelector()
                .resourceId("edu.ucsd.cse110.cse_110_project_cse_110_team_9:id/public_uid_textView"));

        assertNotEquals("null", public_uid_text_view.getText());
    }
}

