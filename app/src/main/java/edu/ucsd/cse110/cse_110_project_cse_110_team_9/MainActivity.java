package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.ucsd.cse110.cse_110_project_cse_110_team_9.Map.MapUIState;
import edu.ucsd.cse110.cse_110_project_cse_110_team_9.Map.MapsFragment;
import edu.ucsd.cse110.cse_110_project_cse_110_team_9.Map.SharedViewModel;

public class MainActivity extends AppCompatActivity {


    private SharedViewModel model;
    private MapsFragment mapsFrag;
    private boolean locationPermissionGranted;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        model = new ViewModelProvider(this).get(SharedViewModel.class);
      //  getLocationPermission();


    }

    public void OnSavePointBtn(View view) {
        Double lat = Double.parseDouble(((EditText) findViewById(R.id.enterLatitudeBox)).getText().toString());
        Double longy = Double.parseDouble(((EditText) findViewById(R.id.enterLongitudeBox)).getText().toString());
        String label = ((EditText) findViewById(R.id.enterPointLabel)).getText().toString();

//        LatLng sydney = new LatLng(-34, 151);
//        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        MarkerOptions marker = new MarkerOptions().position(new LatLng(lat, longy)).title(label);
        model.addNewMarker(marker);
        //model.setItem(new MapUIState(lat, longy, label));

    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        locationPermissionGranted = false;
        if (requestCode
                == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true;
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        //update map


//        fragment.<specific_function_name>();

    }


    public void setOnDataListener(MapsFragment mapsFragment) {
        mapsFrag = mapsFragment;
    }
}