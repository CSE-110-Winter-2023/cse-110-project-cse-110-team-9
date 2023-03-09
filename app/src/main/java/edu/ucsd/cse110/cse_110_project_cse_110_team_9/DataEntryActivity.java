package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import edu.ucsd.cse110.cse_110_project_cse_110_team_9.database.Friend;
import edu.ucsd.cse110.cse_110_project_cse_110_team_9.database.SocialCompassDatabase;
import edu.ucsd.cse110.cse_110_project_cse_110_team_9.database.SocialCompassRepository;

public class DataEntryActivity extends AppCompatActivity {
    private SocialCompassRepository repo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        var db = SocialCompassDatabase.provide(getApplicationContext());
        var dao = db.getDao();
        repo = new SocialCompassRepository(dao);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);
    }

    public void onExitClicked(View view) {
        finish();
    }

    public void onAddClicked(View view) {
        //get text by calling id of text box
        EditText public_uid = findViewById(R.id.public_uid_textView);

        //send text to server to get friend's user info
        LiveData<Friend> friend = repo.getFriend(public_uid.getText().toString());
        //call method that inserts the friend's info into the local database
        repo.upsertLocalFriend(friend);
    }
}