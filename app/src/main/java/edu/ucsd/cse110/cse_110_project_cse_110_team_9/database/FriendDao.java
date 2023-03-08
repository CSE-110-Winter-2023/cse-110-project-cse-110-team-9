package edu.ucsd.cse110.cse_110_project_cse_110_team_9.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Upsert;

import java.util.List;

@Dao
public abstract class FriendDao {





    //Not really needed for our project
//    @Query("SELECT * FROM friends WHERE public_uid = :public_uid")
//    public abstract LiveData<Friend> get(String public_uid);

    //Get all the friends in the local data base
    @Query("SELECT * FROM friends ORDER BY public_uid")
    public abstract LiveData<List<Friend>> getAll();




    @Query("SELECT * FROM friends WHERE public_uid = :public_uid")
    public abstract LiveData<Friend> get(String public_uid);

    //Might not be used?
    @Delete
    public abstract int delete(Friend friend);





}
