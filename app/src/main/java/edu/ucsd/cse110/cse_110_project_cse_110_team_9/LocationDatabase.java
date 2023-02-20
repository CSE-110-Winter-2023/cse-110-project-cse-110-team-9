package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {AddLocation.class}, version = 1)
public abstract class LocationDatabase extends RoomDatabase {
    public abstract AddLocationDao addLocationDao();
}
