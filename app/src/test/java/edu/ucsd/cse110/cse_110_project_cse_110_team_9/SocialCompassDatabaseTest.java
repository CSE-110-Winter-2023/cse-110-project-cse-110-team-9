package edu.ucsd.cse110.cse_110_project_cse_110_team_9;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.manipulation.Ordering;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import edu.ucsd.cse110.cse_110_project_cse_110_team_9.database.Friend;
import edu.ucsd.cse110.cse_110_project_cse_110_team_9.database.SocialCompassDao;
import edu.ucsd.cse110.cse_110_project_cse_110_team_9.database.SocialCompassDatabase;
import edu.ucsd.cse110.cse_110_project_cse_110_team_9.database.User;

@RunWith(AndroidJUnit4.class)
public class SocialCompassDatabaseTest {

    private SocialCompassDao dao;
    private SocialCompassDatabase db;

    private LifecycleOwner lifeCycleOwner;
    private LifecycleRegistry lifecycle;

    @Before
    public void createDb()
    {
        Context context = ApplicationProvider.getApplicationContext();

        db = Room.inMemoryDatabaseBuilder(context, SocialCompassDatabase.class)
                .allowMainThreadQueries().build();
        dao = db.getDao();



        lifeCycleOwner = Mockito.mock(LifecycleOwner.class);
        lifecycle = new LifecycleRegistry(Mockito.mock(LifecycleOwner.class));
        lifecycle.setCurrentState(Lifecycle.State.RESUMED);
        Mockito.when(lifeCycleOwner.getLifecycle()).thenReturn(lifecycle);

    }


    @Test
    public void testInsertFriend()
    {
        String public_code1 = UUID.randomUUID().toString();
        String public_code2= UUID.randomUUID().toString();

        String private_code = UUID.randomUUID().toString();
        Friend friend1 = new Friend(public_code1, "Kalam", 10.0d, 10.0d,
                "0", "0");

        Friend friend2 = new Friend(public_code2, "Jerry", 10.0d, 10.0d,
                "0", "0");

        long id1 = dao.upsertFriend(friend1);
        System.out.println("id1: " + Long.toString(id1));
        long id2 = dao.upsertFriend(friend2);
        System.out.println("id2: " + Long.toString(id2));

        assertNotEquals(id1, id2);
    }

    @Test
    public void testGetFriend()
    {
        String public_code1 = UUID.randomUUID().toString();


        String time = Utilities.formatTime(Instant.now().getEpochSecond());
        String private_code = UUID.randomUUID().toString();
        Friend insertedFriend = new Friend(public_code1, "Kalam", 10.0d, 10.0d,
                time, time);


        long id1 = dao.upsertFriend(insertedFriend);

        var retrivedFriend = dao.getFriend(public_code1);

        retrivedFriend.observe(lifeCycleOwner, new Observer<Friend>() {
            @Override
            public void onChanged(Friend friend) {
                assertEquals(insertedFriend.label, friend.label);
                assertEquals(insertedFriend.latitude,friend.latitude, 0.01);
                assertEquals(insertedFriend.longitude, friend.longitude, 0.01);
                assertEquals(insertedFriend.public_code,friend.public_code);
                assertEquals(insertedFriend.created_at, friend.created_at);
                assertEquals(insertedFriend.updated_at, friend.updated_at);
            }
        });
    }

    @Test
    public void testUpdateFriend()
    {
        String public_code1 = UUID.randomUUID().toString();
        String time = Utilities.formatTime(Instant.now().getEpochSecond());
        String private_code = UUID.randomUUID().toString();
        Friend insertedFriend = new Friend(public_code1, "Kalam", 10.0d, 10.0d,
                time, time);

        long id1 = dao.upsertFriend(insertedFriend);

        //udate friend by usperted again

        var retrivedFriend = dao.getFriend(public_code1);

        final int[] updateNum = {0};
        retrivedFriend.observe(lifeCycleOwner, new Observer<Friend>() {
            @Override
            public void onChanged(Friend friend) {

                if (updateNum[0] == 0)
                {
                    assertEquals(insertedFriend.label, friend.label);
                    assertEquals(insertedFriend.latitude,friend.latitude, 0.01);
                    assertEquals(insertedFriend.longitude, friend.longitude, 0.01);
                    assertEquals(insertedFriend.public_code,friend.public_code);
                    assertEquals(insertedFriend.created_at, friend.created_at);
                    assertEquals(insertedFriend.updated_at, friend.updated_at);
                    updateNum[0]++;

                    friend.latitude = 20.0d;
                    friend.longitude = 21.0d;
                    dao.upsertFriend(friend);
                }
                else{
                    assertEquals(insertedFriend.label, friend.label);
                    assertEquals(20.0d,friend.latitude, 0.01);
                    assertEquals(21.0d, friend.longitude, 0.01);
                    assertEquals(insertedFriend.public_code,friend.public_code);
                    assertEquals(insertedFriend.created_at, friend.created_at);
                    assertEquals(insertedFriend.updated_at, friend.updated_at);
                }
            }
        });
    }


    @Test
    public void testFriendExists()
    {
        String public_code1 = UUID.randomUUID().toString();
        String time = Utilities.formatTime(Instant.now().getEpochSecond());
        String private_code = UUID.randomUUID().toString();
        Friend insertedFriend = new Friend(public_code1, "Kalam", 10.0d, 10.0d,
                time, time);

        dao.upsertFriend(insertedFriend);
       boolean result = dao.friendExists(public_code1);
       assertTrue(result);
    }

    @Test
    public void testGetFriends()
    {

        String public_code1 = UUID.randomUUID().toString();
        String time = Utilities.formatTime(Instant.now().getEpochSecond());
        Friend insertedFriend1 = new Friend(public_code1, "Kalam", 10.0d, 10.0d,
                time, time);

        String public_code2 = UUID.randomUUID().toString();
        Friend insertedFriend2 = new Friend(public_code2, "Jason", 10.0d, 10.0d,
                time, time);


        String public_code3 = UUID.randomUUID().toString();
        Friend insertedFriend3 = new Friend(public_code3, "Jerry", 10.0d, 10.0d,
                time, time);

        dao.upsertFriend(insertedFriend1);
        dao.upsertFriend(insertedFriend2);
        dao.upsertFriend(insertedFriend3);

        var allLiveFriends = dao.getAllFriendsLive();

        allLiveFriends.observe(lifeCycleOwner, new Observer<List<Friend>>() {
            @Override
            public void onChanged(List<Friend> friends) {
                assertEquals(3, friends.size());
            }
        });

    }

    @Test
    public void TestDeleteFriend()
    {
        String public_code1 = UUID.randomUUID().toString();
        String time = Utilities.formatTime(Instant.now().getEpochSecond());
        Friend insertedFriend1 = new Friend(public_code1, "Kalam", 10.0d, 10.0d,
                time, time);

        dao.upsertFriend(insertedFriend1);
        dao.deleteFriend(insertedFriend1);
        assertFalse(dao.friendExists(insertedFriend1.public_code));
    }

    @Test
    public void testGetUser()
    {
        String public_uid1 = UUID.randomUUID().toString();
        String private_code1 = UUID.randomUUID().toString();
        User insertedUser = new User("Jerry", private_code1, public_uid1,
                69.0d, 69.0d, Instant.now().getEpochSecond());

        dao.upsertUser(insertedUser);

        User retrievedUser = dao.getUser();

        assertEquals(insertedUser.getLongitude(), retrievedUser.getLongitude(), 0.01);
        assertEquals(insertedUser.getLatitude(), retrievedUser.getLatitude(), 0.01);
        assertEquals(insertedUser.getPrivate_code(), retrievedUser.getPrivate_code());
        assertEquals(insertedUser.get_public_code(), retrievedUser.get_public_code());
        assertEquals(insertedUser.getLabel(), retrievedUser.getLabel());

    }

    @Test
    public void TestUpsertUser()
    {
        String public_uid1 = UUID.randomUUID().toString();
        String private_code1 = UUID.randomUUID().toString();
        User insertedUser = new User("Jerry", private_code1, public_uid1,
                69.0d, 69.0d, Instant.now().getEpochSecond());

        dao.upsertUser(insertedUser);

        var retrievedUser = dao.getLiveUser();
        final int[] updateNum = {0};
        retrievedUser.observe(lifeCycleOwner, new Observer<User>() {
            @Override
            public void onChanged(User user) {

                if (updateNum[0] == 0)
                {
                    updateNum[0]++;
                    user.setLabel("Jason");
                    dao.upsertUser(user);
                }
                else{
                    assertEquals("Jason", user.getPrivate_code()
                    );
                }
            }
        });
    }


    @Test
    public void testUserExists()
    {
        String public_uid1 = UUID.randomUUID().toString();
        String private_code1 = UUID.randomUUID().toString();
        User insertedUser = new User("Jerry", private_code1, public_uid1,
                69.0d, 69.0d, Instant.now().getEpochSecond());
        dao.upsertUser(insertedUser);
        assertTrue(dao.userExists());
    }

    @Test
    public void testDeleteUser()
    {

        String public_uid1 = UUID.randomUUID().toString();
        String private_code1 = UUID.randomUUID().toString();
        User insertedUser = new User("Jerry", private_code1, public_uid1,
                69.0d, 69.0d, Instant.now().getEpochSecond());
        dao.upsertUser(insertedUser);

        dao.deleteUser(insertedUser);
        assertFalse(dao.userExists());
    }

    @After
    public void closeDb(){
        db.close();
    }
}

