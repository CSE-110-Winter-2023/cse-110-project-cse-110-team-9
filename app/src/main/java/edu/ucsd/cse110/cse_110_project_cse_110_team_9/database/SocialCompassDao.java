package edu.ucsd.cse110.cse_110_project_cse_110_team_9.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;
import androidx.room.Upsert;

import java.util.List;

@Dao
public abstract class SocialCompassDao {



    //[---------------METHODS FOR USER TABLE IN DATABASE ------------]
    @Query("SELECT EXISTS(SELECT 1 FROM user)")
    public abstract boolean userExists();


    //NOTE THE USER TABLE SHOLUD ONLY EVER HAVE ONE USER, THE USER OF THE APP
    @Query("SELECT * FROM user LIMIT 1")
    public abstract LiveData<User> getLiveUser();

    @Query("SELECT * FROM user LIMIT 1")
    public abstract User getUser();

    @Upsert
    public abstract long upsertUser(User user);

    @Delete
    public abstract int deleteUser(User user);

    @Query("DELETE FROM user")
    public abstract void nukeUser();


    //[---------------METHODS FOR FRIEND TABLE IN DATABASE ------------]


    //Get all the friends in the local data base
    @Query("SELECT * FROM friends ORDER BY public_code")
    public abstract LiveData<List<Friend>> getAllFriendsLive();

    @Query("SELECT * FROM friends ORDER BY public_code")
    public abstract List<Friend> getAllFriends();

    //yeah cool
    @Upsert
    public abstract long upsertFriend(Friend friend);


    /*this will be used to
    See if friend already exists in the local database so we don't
    add the same friend twice to the database
     */
    @Query("SELECT EXISTS(SELECT 1 FROM friends WHERE public_code = :public_code)")
    public abstract boolean friendExists(String public_code);


    @Query("DELETE FROM friends")
    public abstract void nukeFriends();



    //get a single friend, might not be needed, but useful for testing
    @Query("SELECT * FROM friends WHERE public_code = :public_code")
    public abstract LiveData<Friend> getFriend(String public_code);

    //Might not be used?
    @Delete
    public abstract int deleteFriend(Friend friend);


}
