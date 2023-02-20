package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AddLocationDao {
    @Insert
    long insert(AddLocation addLocation);

    @Query("SELECT * FROM `saved_locations` WHERE `name`=:name")
    AddLocation get(String name);

    @Query("SELECT * FROM `saved_locations`")
    List<AddLocation> getAll();

    @Update
    int update(AddLocation addLocation);

    @Delete
    int delete(AddLocation addLocation);
}
