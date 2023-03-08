package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import androidx.annotation.Nullable;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.robolectric.RobolectricTestRunner;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import edu.ucsd.cse110.cse_110_project_cse_110_team_9.database.Friend;
import edu.ucsd.cse110.cse_110_project_cse_110_team_9.database.ServerAPI;
import edu.ucsd.cse110.cse_110_project_cse_110_team_9.database.User;



@RunWith(MockitoJUnitRunner.class)
public class TestServerApi {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Test
    public void testServer() {


        String public_uid = "point-nemo";
        String public_uid2 = "testingpublic";
        String public_uid3 ="bf714fa7-713a-4b6c-90d7-30c40f6dd2cd"; // these are listed in public registers
        String public_uid4 = "33bf8329-2b5f-4f3e-a43d-fe8145eb9ab7";
        String public_uid5 = "3ae690fa-2467-476e-b81c-0f445470bd8d";
        String public_uid6 = "40fdc330-62dc-4061-bd1e-c0136071dc94";
        String public_uid7= "2e5e835a-de53-4a72-b56e-7c6a62b73a02";



        var friend = ServerAPI.provide().getFriend(public_uid);

        var result = friend.toJSON();


        System.out.println(result);
        System.out.println(friend.latitude);


        User user = new User("test", "private", "test415", 100.0, 100.0);




        final Observer<Friend> friendObserver = new Observer<Friend>() {
            @Override
            public void onChanged(Friend friend) {
                System.out.println("observer1 a friend change: " + friend.toJSON());
            }

        };

        final Observer<Friend> friendObserver2 = new Observer<Friend>() {
            @Override
            public void onChanged(Friend friend) {
                System.out.println("observer2 a friend change: " + friend.toJSON());
            }
        };
        var lifeCycleOwner = Mockito.mock(LifecycleOwner.class);
        var lifecycle = new LifecycleRegistry(Mockito.mock(LifecycleOwner.class));
        lifecycle.setCurrentState(Lifecycle.State.RESUMED);

        Mockito.when(lifeCycleOwner.getLifecycle()).thenReturn(lifecycle);

        ServerAPI.provide().getFriendUpdates(public_uid).observe(lifeCycleOwner, friendObserver);
        ServerAPI.provide().getFriendUpdates(public_uid2).observe(lifeCycleOwner, friendObserver);
        ServerAPI.provide().getFriendUpdates(public_uid3).observe(lifeCycleOwner, friendObserver);
        ServerAPI.provide().getFriendUpdates(public_uid4).observe(lifeCycleOwner, friendObserver);
        ServerAPI.provide().getFriendUpdates(public_uid5).observe(lifeCycleOwner, friendObserver);
        ServerAPI.provide().getFriendUpdates(public_uid6).observe(lifeCycleOwner, friendObserver);
        ServerAPI.provide().getFriendUpdates(public_uid7).observe(lifeCycleOwner, friendObserver);



        try {
            Thread.sleep(30000);
            ServerAPI.provide().shutDownPool();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}
