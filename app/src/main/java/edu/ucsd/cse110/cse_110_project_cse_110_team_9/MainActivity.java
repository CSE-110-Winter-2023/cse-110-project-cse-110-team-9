package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.util.Pair;
import androidx.lifecycle.MutableLiveData;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.ucsd.cse110.cse_110_project_cse_110_team_9.database.ServerAPI;
import edu.ucsd.cse110.cse_110_project_cse_110_team_9.database.SocialCompassDatabase;
import edu.ucsd.cse110.cse_110_project_cse_110_team_9.database.SocialCompassRepository;
import edu.ucsd.cse110.cse_110_project_cse_110_team_9.database.User;
import edu.ucsd.cse110.cse_110_project_cse_110_team_9.view.CompassView;
import edu.ucsd.cse110.cse_110_project_cse_110_team_9.view.FriendViewItem;
import edu.ucsd.cse110.cse_110_project_cse_110_team_9.services.LocationService;
import edu.ucsd.cse110.cse_110_project_cse_110_team_9.services.OrientationService;
import edu.ucsd.cse110.cse_110_project_cse_110_team_9.services.TimeService;


public class MainActivity extends AppCompatActivity {

    private TimeService timeService;
    private OrientationService orientationService;
    private float orientation = 0f;

    private List<String> emojiStrings;

    private MutableLiveData<Integer> scale;
    private SocialCompassRepository repo; //in previous labs this was final.
    private LocationService locationService;
    private String user_public_code = "";

    private User userCache = null; //cache an instance of the users so we don't have to get
    // the user's public_uid, private_code, and label every time we want to update the users
    // location on the server.
    private ActivityResultLauncher<Intent> activityResultLauncher; //We use this to launch
    // NameAcitity and DataEntryActivity which allows us to get a result from an activty. For
    // NameActivity the result is the user's name and for DataEntryActivity the result is the
    // public_uid of the friend you want to add. SEE LINE 78 for this is used.
    // See: https://developer.android.com/training/basics/intents/result


    //List of references to our friendViewItems on the map
    private List<FriendViewItem> friendItems;

    //Working in progress
    private int[] scaleValues = new int[]{1, 10, 500,};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        friendItems = new ArrayList<>();

        emojiStrings = new ArrayList<>();

        emojiStrings = Utilities.getEmojis();

        //SCALE SETUP

        scale = new MutableLiveData<>();
        scale.postValue(1);

        Log.d("Main Activity", "main activity launched");

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Constants.NAME_ACTIVITY_REQUEST_CODE) {
                        // There are no request codes
                        Intent data = result.getData();
                        String name = data.getStringExtra("name");

                        if (name != null) {
                            if (repo.userExists()) {

                                //THE user already exists in the database so just update the name
                                // of the user locally, and on the server
                                User user = repo.getUser();
                                userCache = user;
                                user.setLabel(name);
                                repo.upsertLocalUser(user);
                                repo.upsertUserRemote(user);

                            } else {
                                //The user does not exist in the database (first time app is
                                // opened from clean install) so we generate a public_code and
                                // private_code for the user, and save it locally, and to the
                                // server;
                                user_public_code = UUID.randomUUID().toString();
                                String private_code = UUID.randomUUID().toString();

                                User user = new User(name, private_code, user_public_code,
                                        0d, 0d, Instant.now().getEpochSecond());

                                //insert user into user database
                                repo.upsertLocalUser(user);
                                repo.upsertUserRemote(user);

                                //SET the public_uid of the user to the Textview
                                TextView public_uid_view = findViewById(R.id.public_uid_textView);
                                public_uid_view.setText(user_public_code);
                                //update user cache
                                userCache = user;
                            }
                        }
                    } else if (result.getResultCode() == Constants.ADD_FRIEND_ACTIVITY_REQUEST_CODE) {
                        Intent data = result.getData();

                        if (data != null) {
                            String public_code = data.getStringExtra("public_code");


                            Log.d("got public uid from activity", public_code);
                            //check if friend exisits on remote

                            if (public_code != null) {

                                addFriend(public_code);
                                repo.upsertLocalFriend(public_code);
                            }
                        }
                    }
                });

        //SETUP the location service
        locationService = LocationService.singleton(this);
        var locationData = locationService.getLocation();
        locationData.observe(this, this::onLocationChanged);


        //SETUP DATABASE
        var db = SocialCompassDatabase.provide(getApplicationContext());
        var dao = db.getDao();
        repo = new SocialCompassRepository(dao);


        //if the user has never opened the app before and entered their name request it.
        if (!repo.userExists()) {
            Intent intent = new Intent(this, NameActivity.class);
            activityResultLauncher.launch(intent);
        } else {
            TextView public_uid_textView = findViewById(R.id.public_uid_textView);
            public_uid_textView.setText(repo.getUser().get_public_code());
        }

        //SETUP TIME SERVICE (I don't think we are really using this rn).
        timeService = TimeService.singleton();
        var timeData = timeService.getTimeData();
        timeData.observe(this, this::onTimeChanged);

        CompassView compass = findViewById(R.id.compass);
        compass.setRangeDegrees(360);


        //SETUP ORIENTATION SERVICE
        orientationService = OrientationService.singleton(this);
        var azimuthData = orientationService.getAzimuthData();
        azimuthData.observe(this, this::OnOrientationChanged);


        //ADD ALL FRIENDS STORED IN THE LOCAL DATABASE TO THE MAP
        addFriendsFromDatabaseToMap();

    }


    private void addFriendsFromDatabaseToMap() {
        var friends = repo.getAllLocalFriends();
        friends.forEach(friend -> {

            if (repo.friendExistsRemote(friend.public_code)) {

                addFriend(friend.public_code);
            } else {

                Log.d("Server",
                        "Saved friend does not exists on REMOTE" + friend.public_code);
            }
        });
    }

    private void OnOrientationChanged(Float azimuth) {
        CompassView compass = findViewById(R.id.compass);
        SharedPreferences preferences = getSharedPreferences("my_prefs", MODE_PRIVATE);

        // float degree = preferences.getFloat("degree", 0);
        float lat_n = preferences.getFloat("lat", 0);
        float long_n = preferences.getFloat("long", 0);

        compass.setDegrees(azimuth, true);

        orientation = azimuth;

        ImageView compassImageView = findViewById(R.id.compassImg);
        float rotation = (-orientation);
        // System.out.println("This is the rotation "+ degree);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) compassImageView.getLayoutParams();
        layoutParams.circleAngle = rotation;
        compassImageView.setLayoutParams(layoutParams);

        compassImageView.setRotation(rotation);
    }

    private void onLocationChanged(Pair<Double, Double> latLong) {

        TextView locationText = findViewById(R.id.locationText);
        locationText.setText(Utilities.formatLocation(latLong.first, latLong.second));
        //UPDATE THE LOCATION IN our cached user object.
        Log.d("location change", "locationChange");
        if (userCache != null) {
            userCache.setUpdated_at(Instant.now().getEpochSecond());
            userCache.setLatitude(latLong.first);
            userCache.setLongitude(latLong.second);
            repo.upsertUserRemote(userCache);
            repo.upsertLocalUser(userCache);
        }
    }

    /**
     * Add a friend to the map given a public uid
     * @param public_code
     */
    public void addFriend(String public_code) {
        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.constraintLayout);
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT);


        //Constrain friend to compassIMG
        params.circleConstraint = R.id.compassImg;

        FriendViewItem newFriend = new FriendViewItem(this);
        newFriend.setLayoutParams(params);

        layout.addView(newFriend);

        //get the friend from remote and setup polling. Pass the liveDAta object into
        // FriendViewobject
        newFriend.setData(repo.getFriendFromRemoteLive(public_code), this);

        friendItems.add(newFriend);
        //set the location and orientation services for the friend view item, so the friendView
        // item can adjust it's angle and radius whenever the users' orientation and location change
        newFriend.setLocationService(locationService, this);
        newFriend.setOrientationService(orientationService, this);

        //SET the friends
        int hashcode = public_code.hashCode() & 0xfffffff; // &0xfff... makes hash code positive
        int index = hashcode % emojiStrings.size();
        newFriend.setFriendIcon(emojiStrings.get(index));

    }

    private void onTimeChanged(Long time) {
    }

    @Override
    protected void onPause() {
        //TODO: Stop polling threads for friends
        super.onPause();
        orientationService.unregisterSensorListeners();
        locationService.unregisterLocationListener();

    }

    @Override
    protected void onResume() {
        //TODO: restart polling threads for friends
        super.onResume();
        orientationService.registerSensorListeners();
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        else{
            locationService.registerLocationListener();
        }

    }

    public void onLaunchDataEntry(View view) {

        Intent intent = new Intent(this, DataEntryActivity.class);
        activityResultLauncher.launch(intent);
    }

    public void onLaunchDegree(View view) {
        Intent intent = new Intent(this, DegreeActivity.class);
        startActivity(intent);
    }

    public void onLaunchName(View view) {
        Intent intent = new Intent(this, NameActivity.class);
        activityResultLauncher.launch(intent);
    }


    public void onZoomOut(View view) {

    }

    public void onZoomIn(View view) {
    }

}