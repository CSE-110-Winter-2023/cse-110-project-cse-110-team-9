package edu.ucsd.cse110.cse_110_project_cse_110_team_9.database;


import android.content.Context;

import androidx.annotation.VisibleForTesting;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Friend.class, User.class}, version =  4, exportSchema = false)
public abstract class SocialCompassDatabase extends RoomDatabase {

    private volatile static SocialCompassDatabase instance = null;

    public abstract SocialCompassDao getDao();

    public synchronized static SocialCompassDatabase provide(Context context) {

        if (instance == null) {
            instance = SocialCompassDatabase.make(context);
        }
        return instance;
    }

    public static SocialCompassDatabase make(Context context) {
        return Room.databaseBuilder(context, SocialCompassDatabase.class, "sc2_app.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    @VisibleForTesting
    public static void inject(SocialCompassDatabase testDatabase) {
        if (instance != null) {
            instance.close();
        }
        instance = testDatabase;
    }
}

