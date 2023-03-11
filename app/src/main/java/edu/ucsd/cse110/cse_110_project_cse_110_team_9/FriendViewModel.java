package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import edu.ucsd.cse110.cse_110_project_cse_110_team_9.database.Friend;
import edu.ucsd.cse110.cse_110_project_cse_110_team_9.database.SocialCompassDatabase;
import edu.ucsd.cse110.cse_110_project_cse_110_team_9.database.SocialCompassRepository;


public class FriendViewModel extends AndroidViewModel {
    private LiveData<Friend> friend;
    private final SocialCompassRepository repo;

    public FriendViewModel(@NonNull Application application) {
        super(application);
        var context = application.getApplicationContext();
        var db = SocialCompassDatabase.provide(context);
        var dao = db.getDao();
        this.repo = new SocialCompassRepository(dao);
    }

    public LiveData<Friend> getFriend(String public_uid) {

        if (friend == null) {

            friend = repo.getFriendFromRemoteLive(public_uid);
        }
        return friend;
    }

    public double calcAngle()
    {
        return 10.0d;
    }

}
