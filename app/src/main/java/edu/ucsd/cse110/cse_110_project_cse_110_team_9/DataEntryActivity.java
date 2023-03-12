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
        setResult(Constants.ADD_FRIEND_ACTIVITY_REQUEST_CODE, null);

    }

    public void onExitClicked(View view) {


        finish();
    }

    public void onAddClicked(View view) {
        //get text by calling id of text box

        //need to close out bc of reasons

        EditText public_code_View = findViewById(R.id.public_uid_textView);

        String public_code = public_code_View.getText().toString();

        if (!repo.friendExistsRemote(public_code))
        {
            Utilities.showAlert(this, "Friend does not exist on the Server. Please enter a valid " +
                    "public_code");
        }
        else {

            Intent intent = getIntent().putExtra("public_code", public_code);
            setResult(Constants.ADD_FRIEND_ACTIVITY_REQUEST_CODE, intent);
            onExitClicked(view);
        }

        //send text to server to get friend's user info
        //LiveData<Friend> friend = repo.getFriend(public_uid.getText().toString());


        //call method that inserts the friend's info into the local database
        //epo.upsertLocalFriend(friend);
    }
}