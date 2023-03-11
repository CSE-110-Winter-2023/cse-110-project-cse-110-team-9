package edu.ucsd.cse110.cse_110_project_cse_110_team_9.database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class SocialCompassRepository {
    private final SocialCompassDao dao;


    public SocialCompassRepository(SocialCompassDao dao) {
        this.dao = dao;

    }


    public LiveData<Friend> getFriendFromRemoteLive(String public_uid)
    {
        return ServerAPI.provide().getFriendUpdates(public_uid);
    }

    public Friend getFriendFromRemote(String public_uid)
    {
        var fur = ServerAPI.provide().getFriendAsync(public_uid);

        try {
            return fur.get(1, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
        } catch (InterruptedException e) {
        } catch (TimeoutException e) {
        }

        return null;
    }

    public LiveData<List<Friend>> getAllLocalFriends() {
        return dao.getAllFriends();
    }


    public boolean friendExistsLocal(String public_uid) {
        return dao.friendExists(public_uid);
    }


//    public Friend getFriendFromRemote(String public_uid)
//    {
//        ServerAPI.provide().getFriendAsync();
//    }

    public void upsertUserRemote(User user) {
        ServerAPI.provide().updateUserLocationAsync(user);
    }

    public boolean userExists() {
        return dao.userExists();
    }
    public LiveData<User> getLiveUser() {
        return dao.getLiveUser();
    }

    public User getUser() {
        return dao.getUser();
    }

    public void deleteLocalFriend(Friend friend) {
        dao.deleteFriend(friend);
    }

    public void deleteUserOnRemoteLocally(User user)
    {
        ServerAPI.provide().deleteUserLocationOnRemoteAsync(user);
        dao.deleteUser(user);

    }


    public void upsertLocalUser(User user) {
        dao.upsertUser(user);
    }


    public void upsertLocalFriend(Friend friend) {
        dao.upsertFriend(friend);
    }


}
