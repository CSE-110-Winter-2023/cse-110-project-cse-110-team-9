package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
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


    private ArrayList<User> testUsers;
    private HashMap<String, User> userHashMap;

    private LifecycleOwner lifeCycleOwner;
    private LifecycleRegistry lifecycle;


    @Before
    public void setup() {
        userHashMap = new HashMap<>();
        testUsers = new ArrayList<>();
        String public_uid1 = UUID.randomUUID().toString();
        String private_code1 = UUID.randomUUID().toString();
        User user1 = new User("Jerry", private_code1, public_uid1,
                69.0d, 69.0d, Instant.now().getEpochSecond());
        testUsers.add(user1);
        userHashMap.put(user1.get_public_code(), user1);


        String public_uid2 = UUID.randomUUID().toString();
        String private_code2 = UUID.randomUUID().toString();
        User user2 = new User("Jason", private_code2, public_uid2,
                42.0d, 42.0d, Instant.now().getEpochSecond());
        testUsers.add(user2);
        userHashMap.put(user2.get_public_code(), user2);


        String public_uid3 = UUID.randomUUID().toString();
        String private_code3 = UUID.randomUUID().toString();
        User user3 = new User("Kalam", private_code3, public_uid3,
                12.0d, 12.0d, Instant.now().getEpochSecond());
        testUsers.add(user3);
        userHashMap.put(user3.get_public_code(), user3);



        String public_uid4 = UUID.randomUUID().toString();
        String private_code4 = UUID.randomUUID().toString();
        User user4 = new User("Saksham", private_code4, public_uid4,
                69.0d, 69.0d, Instant.now().getEpochSecond());
        testUsers.add(user4);
        userHashMap.put(user4.get_public_code(), user4);



        String public_uid5 = UUID.randomUUID().toString();
        String private_code5 = UUID.randomUUID().toString();
        User user5 = new User("Sukhman", private_code5, public_uid5,
                69.0d, 69.0d, Instant.now().getEpochSecond());
        testUsers.add(user5);
        userHashMap.put(user5.get_public_code(), user5);



        String public_uid6 = UUID.randomUUID().toString();
        String private_code6 = UUID.randomUUID().toString();
        User user6 = new User("Apnambir", private_code6, public_uid6,
                69.0d, 69.0d, Instant.now().getEpochSecond());
        testUsers.add(user6);
        userHashMap.put(user6.get_public_code(), user6);


        String public_uid7 = UUID.randomUUID().toString();
        String private_code7 = UUID.randomUUID().toString();
        User user7 = new User("Sierra", private_code7, public_uid7,
                69.0d, 69.0d, Instant.now().getEpochSecond());
        testUsers.add(user7);
        userHashMap.put(user7.get_public_code(), user7);


        String public_uid8 = UUID.randomUUID().toString();
        String private_code8 = UUID.randomUUID().toString();
        User user8 = new User("Midas", private_code8, public_uid8,
                69.0d, 69.0d, Instant.now().getEpochSecond());
        testUsers.add(user8);
        userHashMap.put(user8.get_public_code(), user8);


        String public_uid9 = UUID.randomUUID().toString();
        String private_code9 = UUID.randomUUID().toString();
        User user9 = new User("Brandon", private_code9, public_uid9,
                69.0d, 69.0d, Instant.now().getEpochSecond());
        testUsers.add(user9);
        userHashMap.put(user9.get_public_code(), user9);


        String public_uid10 = UUID.randomUUID().toString();
        String private_code10 = UUID.randomUUID().toString();
        User user10 = new User("Etienne", private_code10, public_uid10,
                48.858093d, 2.294694, Instant.now().getEpochSecond());
        testUsers.add(user10);
        userHashMap.put(user10.get_public_code(), user10);


        lifeCycleOwner = Mockito.mock(LifecycleOwner.class);
        lifecycle = new LifecycleRegistry(Mockito.mock(LifecycleOwner.class));
        lifecycle.setCurrentState(Lifecycle.State.RESUMED);
        Mockito.lenient().when(lifeCycleOwner.getLifecycle()).thenReturn(lifecycle);

    }

    @Test
    public void testThreadPoolExecuterWithMultipleFriends() {

        System.out.println("HERE");
        final Observer<Friend> friendObserver = new Observer<Friend>() {
            @Override
            public void onChanged(Friend friend) {
                System.out.println("Got a friend from server: " + friend.toJSON());
                String public_uid = friend.public_code;

                User expectUser = userHashMap.get(public_uid);
                assertEquals(expectUser.label, friend.label);
                assertEquals(expectUser.getLatitude(), friend.latitude, 0.1);
                assertEquals(expectUser.getLongitude(), friend.longitude, 0.1);
                assertEquals(expectUser.label, friend.label);
            }
        };



//        for (int i = 0; i < testUsers.size(); i++)
//        {
//            System.out.println(testUsers.get(i).getLabel());
//        }
//
        testUsers.forEach((user) -> {
            ServerAPI.provide().updateUserLocation(user);
            ServerAPI.provide().getFriendUpdates(
                    user.get_public_code()).observe(lifeCycleOwner, friendObserver);
            System.out.println("ADD user: " + user.label + " to the server");
        });

        try {
            Thread.sleep(15000);
            ServerAPI.provide().shutDownPool();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @After
    public void finishTest() {
        //Delete the newly created usrs from server to avoid server clutter
        testUsers.forEach((user) -> {

            try {

                assertEquals(true,ServerAPI.provide().deleteUserLocationOnRemote(user));
            } catch (Exception e) {
                fail();
            }
        });

    }
}
