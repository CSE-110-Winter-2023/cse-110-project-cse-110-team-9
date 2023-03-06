package edu.ucsd.cse110.cse_110_project_cse_110_team_9.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Upsert;

@Dao
public abstract class FriendDao {


    @Upsert
    public abstract void upsertFriend(Friend friend);

    @Query("SELECT * FROM friends WHERE public_uid = :public_uid")
    public abstract LiveData<Friend> get(String public_uid);


}
