package edu.ucsd.cse110.cse_110_project_cse_110_team_9.database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;


public class FriendRepository {
    private final FriendDao dao;
    private int poolSize;


    public FriendRepository(FriendDao dao, int poolSize) {
        this.poolSize = poolSize;
        this.dao = dao;

    }


    public LiveData<List<Friend>> getAllLocalFriends()
    {
        return dao.getAll();
    }

    public LiveData<List<Friend>> getFriendsFromRemote()
    {
        var remoteFriends = new MutableLiveData<>();

        return null;
    }

}
