package edu.ucsd.cse110.cse_110_project_cse_110_team_9.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;
import androidx.room.Upsert;

import java.util.List;

@Dao
public abstract class SocialCompassDao {




    @Query("SELECT EXISTS(SELECT 1 FROM user)")
    public abstract boolean userExists();


    @Query("SELECT * FROM user LIMIT 1")
    public abstract LiveData<User> getUser();

    @Upsert
    public abstract long upsertUser(User user);


    //[---------------METHODS FOR FRIEND TABLE IN DATABASE ------------]


    //Get all the friends in the local data base
    @Query("SELECT * FROM friends ORDER BY public_uid")
    public abstract LiveData<List<Friend>> getAllFriends();

    //yeah cool
    @Upsert
    public abstract long upsertFriend(Friend friend);


    /*this will be used to
    See if friend already exists in the local database so we don't
    add the same friend twice to the database
     */
    @Query("SELECT EXISTS(SELECT 1 FROM friends WHERE public_uid = :public_uid)")
    public abstract boolean friendExists(String public_uid);


    //get a single friend, might not be needed, but useful for testing
    @Query("SELECT * FROM friends WHERE public_uid = :public_uid")
    public abstract LiveData<Friend> getFriend(String public_uid);

    //Might not be used?
    @Delete
    public abstract int deleteFriend(Friend friend);

}
