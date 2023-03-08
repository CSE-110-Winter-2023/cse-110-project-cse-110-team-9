package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.Instant;
import java.util.UUID;

import edu.ucsd.cse110.cse_110_project_cse_110_team_9.database.SocialCompassDatabase;
import edu.ucsd.cse110.cse_110_project_cse_110_team_9.database.SocialCompassRepository;
import edu.ucsd.cse110.cse_110_project_cse_110_team_9.database.User;
import edu.ucsd.cse110.cse_110_project_cse_110_team_9.services.LocationService;
import edu.ucsd.cse110.cse_110_project_cse_110_team_9.services.OrientationService;
import edu.ucsd.cse110.cse_110_project_cse_110_team_9.services.TimeService;


public class MainActivity extends AppCompatActivity {

    private TimeService timeService;
    private OrientationService orientationService;
    private float orientation = 0f;
    private float previous_orientation = 0f;
    private RecyclerView recyclerView;
    private static final int NAME_ACTIVITY_REQUEST_CODE = 0;

    private SocialCompassRepository repo; //in previous labs this was final.

    private LocationService locationService;
    private String user_public_uid = "";

    private User userTemplate = null; //keep a instnace of the user.
    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Main Activity", "main activity launched");


//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 200);
//        }






        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
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

                                user_public_uid = UUID.randomUUID().toString();
                                String private_code = UUID.randomUUID().toString();
                                User user = new User(name, private_code, user_public_uid,
                                        0d, 0d, Instant.now().getEpochSecond());

                                //insert user into user database
                                repo.upsertLocalUser(user);
                                repo.upsertUserRemote(user);

                                TextView public_uid_view = findViewById(R.id.public_uid_textView);
                                public_uid_view.setText(user_public_uid);

                                userTemplate = user;
                            }
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
            public_uid_textView.setText(repo.getUser().getPublic_uid());
        }


        timeService = TimeService.singleton();
        var timeData = timeService.getTimeData();
        timeData.observe(this, this::onTimeChanged);

        CompassView compass = findViewById(R.id.compass);
        compass.setRangeDegrees(360);

        orientationService = OrientationService.singleton(this);
        var azimuthData = orientationService.getAzimuthData();
        azimuthData.observe(this, this::OnOrientationChanged);


    }


    private void OnOrientationChanged(Float azimuth) {
        CompassView compass = findViewById(R.id.compass);
        SharedPreferences preferences = getSharedPreferences("my_prefs", MODE_PRIVATE);

        float degree = preferences.getFloat("degree", 0);
        float lat_n = preferences.getFloat("lat", 0);
        float long_n = preferences.getFloat("long", 0);

        compass.setDegrees(azimuth, true);

        orientation = azimuth;

        ImageView marker = findViewById(R.id.compassImg);
        float rotation = (-orientation) + degree;
        // System.out.println("This is the rotation "+ degree);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) marker.getLayoutParams();
        layoutParams.circleAngle = rotation;
        marker.setLayoutParams(layoutParams);

        // Set the rotation of the ImageView to match the circle angle
        marker.setRotation(rotation);

        ImageView location_marker = findViewById(R.id.parentsImageView);
        double deltaTheta = Math.toDegrees(Math.atan2(lat_n, long_n)) - azimuth;
        float rotation_f = (float) deltaTheta;
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) location_marker.getLayoutParams();
        layoutParams2.circleAngle = rotation_f;
        location_marker.setLayoutParams(layoutParams2);

        // Set the rotation of the ImageView to match the circle angle
        location_marker.setRotation(rotation_f);
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


    private void onTimeChanged(Long time) {
        ImageView img = findViewById(R.id.compassImg);
        RotateAnimation rotateAnimation = new RotateAnimation(previous_orientation,
                orientation, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(100);
        rotateAnimation.setFillAfter(true);
        img.startAnimation(rotateAnimation);

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
        startActivity(intent);
    }

    public void onLaunchDegree(View view) {
        Intent intent = new Intent(this, DegreeActivity.class);
        startActivity(intent);
    }

    public void onLaunchName(View view) {
        Intent intent = new Intent(this, NameActivity.class);
        activityResultLauncher.launch(intent);
    }


}