package edu.ucsd.cse110.cse_110_project_cse_110_team_9.database;


import android.content.Context;

import androidx.annotation.VisibleForTesting;
import androidx.room.Room;
import androidx.room.RoomDatabase;

public abstract class FriendDatabase extends RoomDatabase {

    private volatile static FriendDatabase instance = null;

    public abstract FriendDao getFriendDao();

    public synchronized static FriendDatabase provide(Context context) {

        if (instance == null) {
            instance = FriendDatabase.make(context);
        }
        return instance;
    }

    public static FriendDatabase make(Context context) {
        return Room.databaseBuilder(context, FriendDatabase.class, "note_app.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    @VisibleForTesting
    public static void inject(FriendDatabase testDatabase) {
        if (instance != null) {
            instance.close();
        }
        instance = testDatabase;
    }
}

