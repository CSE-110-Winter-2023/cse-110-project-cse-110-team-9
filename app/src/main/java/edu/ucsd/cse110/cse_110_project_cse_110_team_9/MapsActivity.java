package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.data.geojson.GeoJsonFeature;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonParser;
import com.google.maps.android.data.geojson.GeoJsonPoint;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import edu.ucsd.cse110.cse_110_project_cse_110_team_9.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private static final String FILE_NAME="map-data.json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng ucsd = new LatLng(32.8801, -117.2340);
        mMap.addMarker(new MarkerOptions().position(ucsd).title("UCSD Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ucsd));

        //GeoJsonLayer layer = new GeoJsonLayer(mMap, )
//        JSONObject geoJsonData = new JSONObject();
//        GeoJsonLayer layer = new GeoJsonLayer(mMap,geoJsonData);
//        GeoJsonPoint point = new GeoJsonPoint(new LatLng(32.6, -117.4));
//        HashMap<String, String> properties = new HashMap<>();
//        properties.put("Ocean", "South Atlantic");
//        GeoJsonFeature pointFeature = new GeoJsonFeature(point, "Origin", properties, null);
//        layer.addFeature(pointFeature);
//        layer.addLayerToMap();
//
//        File file = new File(this.getFilesDir(), FILE_NAME);
//        FileReader fileReader = null;
//        FileWriter fileWriter = null;
//        BufferedReader bufferedReader = null;
//        BufferedWriter bufferedWriter = null;
//        String response = null;
//        System.out.println(layer.toString());
//        if (!file.exists()){
//                try {
//                    file.createNewFile();
//                    fileWriter = new FileWriter(file.getAbsoluteFile());
//                    bufferedWriter = new BufferedWriter(fileWriter);
//                    bufferedWriter.write("{}");
//                    bufferedWriter.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }


        }


    }

