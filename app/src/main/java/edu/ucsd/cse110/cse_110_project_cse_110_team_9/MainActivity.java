package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Pair;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    private float previous_orientation = 0f;
    private RecyclerView recyclerView;
    private List<String> emojiStrings;


    private MutableLiveData<Integer> scale;
    private SocialCompassRepository repo; //in previous labs this was final.

    private LocationService locationService;
    private String user_public_code = "";

    private User userTemplate = null; //keep a instnace of the user.
    private ActivityResultLauncher<Intent> activityResultLauncher;

   private List<FriendViewItem> friendItems;

   private int[] scaleValues = new int[]{1, 10, 500, };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        friendItems = new ArrayList<>();

        emojiStrings = new ArrayList<>();


        //SCALE SETUP

        scale = new MutableLiveData<>();
        scale.postValue(1);





        emojiStrings.add(new String(Character.toChars(0x1F99C)));
        emojiStrings.add(new String(Character.toChars(0x1F99A)));
        emojiStrings.add(new String(Character.toChars(0x1F9A9)));
        emojiStrings.add(new String(Character.toChars(0x1F9A4)));
        emojiStrings.add(new String(Character.toChars(0x1F986)));
        emojiStrings.add(new String(Character.toChars(0x1F985)));
        emojiStrings.add(new String(Character.toChars(0x1F54A)));
        emojiStrings.add(new String(Character.toChars(0x1F413)));
        emojiStrings.add(new String(Character.toChars(0x1F9A1)));
        emojiStrings.add(new String(Character.toChars(0x1F9A8)));
        emojiStrings.add(new String(Character.toChars(0x1F9A6)));
        emojiStrings.add(new String(Character.toChars(0x1F9A5)));
        emojiStrings.add(new String(Character.toChars(0x1F54A)));
        emojiStrings.add(new String(Character.toChars(0x1F987)));
        emojiStrings.add(new String(Character.toChars(0x1F994)));
        emojiStrings.add(new String(Character.toChars(0x1F54A)));
        emojiStrings.add(new String(Character.toChars(0x1F9AB)));
        emojiStrings.add(new String(Character.toChars(0x1F43F)));
        emojiStrings.add(new String(Character.toChars(0x1F407)));
        emojiStrings.add(new String(Character.toChars(0x1F400)));
        emojiStrings.add(new String(Character.toChars(0x1F401)));
        emojiStrings.add(new String(Character.toChars(0x1F99B)));
        emojiStrings.add(new String(Character.toChars(0x1F98F)));
        emojiStrings.add(new String(Character.toChars(0x1F9A3)));
        emojiStrings.add(new String(Character.toChars(0x1F992)));
        emojiStrings.add(new String(Character.toChars(0x1F999)));
        emojiStrings.add(new String(Character.toChars(0x1F42B)));
        emojiStrings.add(new String(Character.toChars(0x1F411)));
        emojiStrings.add(new String(Character.toChars(0x1F404)));
        emojiStrings.add(new String(Character.toChars(0x1F402)));
        emojiStrings.add(new String(Character.toChars(0x1F42E)));
        emojiStrings.add(new String(Character.toChars(0x1F9AC)));
        emojiStrings.add(new String(Character.toChars(0x1F98C)));
        emojiStrings.add(new String(Character.toChars(0x1F993)));
        emojiStrings.add(new String(Character.toChars(0x1F984)));
        emojiStrings.add(new String(Character.toChars(0x1F40E)));
        emojiStrings.add(new String(Character.toChars(0x1F406)));
        emojiStrings.add(new String(Character.toChars(0x1F405)));
        emojiStrings.add(new String(Character.toChars(0x1F408)));
        emojiStrings.add(new String(Character.toChars(0x1F429)));
        emojiStrings.add(new String(Character.toChars(0x1F415)));
        emojiStrings.add(new String(Character.toChars(0x1F98D)));
        emojiStrings.add(new String(Character.toChars(0x1F412)));


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
                                //update the users name;
//                                LiveData<User> liveUser = repo.getUser();
//
//                                liveUser.observe(this, user ->{
//                                    user.setLabel(name);
//                                    repo.upsertLocalUser(user);
//                                } );
                                User user = repo.getUser();
                                userTemplate = user;
                                user.setLabel(name);
                                repo.upsertLocalUser(user);
                                repo.upsertUserRemote(user);

                            } else {
                                //create user for the first time

                                user_public_code = UUID.randomUUID().toString();
                                String private_code = UUID.randomUUID().toString();
                                User user = new User(name, private_code, user_public_code,
                                        0d, 0d, Instant.now().getEpochSecond());

                                //insert user into user database
                                repo.upsertLocalUser(user);
                                repo.upsertUserRemote(user);

                                TextView public_uid_view = findViewById(R.id.public_uid_textView);
                                public_uid_view.setText(user_public_code);

                                userTemplate = user;
                            }
                        }
                    }
                    else if (result.getResultCode() == Constants.ADD_FRIEND_ACTIVITY_REQUEST_CODE)
                    {
                        Intent data = result.getData();
                        String public_code = data.getStringExtra("public_code");


                        Log.d("got public uid from activity" ,public_code);
                        //check if friend exisits on remote

                        if (public_code != null ) {

                            addFriend(public_code);
                            repo.upsertLocalFriend(public_code);
                        }
                    }
                });

        locationService = LocationService.singleton(this);
        var locationData = locationService.getLocation();
        locationData.observe(this, this::onLocationChanged);



        //For future code push
        //AddLocationAdapter adapter = new AddLocationAdapter();
        //adapter.setHasStableIds(true);

        //recyclerView = findViewById(R.id.locations);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.setAdapter(adapter);

        //adapter.setLocations(AddLocation.loadJson(this, "saved_locations.json"));


        //SETUP DATABASE

        var db = SocialCompassDatabase.provide(getApplicationContext());
        var dao = db.getDao();
        repo = new SocialCompassRepository(dao);


        //if the user has never opened the app before and entered their name request it.
        if (!repo.userExists()) {
            Intent intent = new Intent(this, NameActivity.class);
            activityResultLauncher.launch(intent);
        }
        else{
            TextView public_uid_textView = findViewById(R.id.public_uid_textView);
            public_uid_textView.setText(repo.getUser().get_public_code());
        }


        timeService = TimeService.singleton();
        var timeData = timeService.getTimeData();
        timeData.observe(this, this::onTimeChanged);

        CompassView compass = findViewById(R.id.compass);
        compass.setRangeDegrees(360);

        orientationService = OrientationService.singleton(this);
        var azimuthData = orientationService.getAzimuthData();
        azimuthData.observe(this, this::OnOrientationChanged);



        //ADD FRIEND STORED IN LOCAL DB

        var friends = repo.getAllLocalFriends();

        friends.forEach(friend -> {


            if (repo.friendExistsRemote(friend.public_code)) {

                addFriend(friend.public_code);
            }
            else{

                Log.d("Server",
                        "Saved friend does not exists on REMOTE" + friend.public_code);
            }
        });

      //  addFriend("jason12");


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

        // Set the rotation of the ImageView to match the circle angle
        compassImageView.setRotation(rotation);

        //ImageView location_marker = findViewById(R.id.parentsImageView);
//        double deltaTheta = Math.toDegrees(Math.atan2(lat_n, long_n)) - azimuth;
//        float rotation_f = (float) deltaTheta;
       // ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) location_marker.getLayoutParams();
      // / layoutParams2.circleAngle = rotation_f;
       // location_marker.setLayoutParams(layoutParams2);

        // Set the rotation of the ImageView to match the circle angle
     //   location_marker.setRotation(rotation_f);

    }

    private void onLocationChanged(Pair<Double, Double> latLong) {
        TextView locationText = findViewById(R.id.locationText);
        locationText.setText(Utilities.formatLocation(latLong.first, latLong.second));

        Log.d("location change", "locationChange");
        if (userTemplate != null) {
            userTemplate.setUpdated_at(Instant.now().getEpochSecond());
            userTemplate.setLatitude(latLong.first);
            userTemplate.setLongitude(latLong.second);
            repo.upsertUserRemote(userTemplate);
            repo.upsertLocalUser(userTemplate);
        }
    }


    public void addFriend(String public_code)
    {
        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.constraintLayout);
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT);


        params.circleConstraint = R.id.compassImg;

        FriendViewItem newFriend = new FriendViewItem(this);
        newFriend.setLayoutParams(params);

        layout.addView(newFriend);

        newFriend.setData(repo.getFriendFromRemoteLive(public_code), this);

        friendItems.add(newFriend);
        newFriend.setLocationService(locationService, this);
        newFriend.setOrientationService(orientationService, this);

        int hashcode = public_code.hashCode() & 0xfffffff;
        int index = hashcode % emojiStrings.size();
        newFriend.setFriendIcon(emojiStrings.get(index));

    }

    private void onTimeChanged(Long time) {
//        ImageView img = findViewById(R.id.compassImg);
//        RotateAnimation rotateAnimation = new RotateAnimation(previous_orientation,
//                orientation, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        rotateAnimation.setDuration(100);
//        rotateAnimation.setFillAfter(true);
//        img.startAnimation(rotateAnimation);

        previous_orientation = orientation;
    }

    @Override
    protected void onPause() {
        super.onPause();
        orientationService.unregisterSensorListeners();

    }

    @Override
    protected void onResume() {
        super.onResume();
        orientationService.registerSensorListeners();
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