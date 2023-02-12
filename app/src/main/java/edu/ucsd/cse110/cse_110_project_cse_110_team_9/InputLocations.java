package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;

import com.cocoahero.android.geojson.Feature;
import com.cocoahero.android.geojson.Point;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class InputLocations extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_locations);
    }

    public void onSaveLocationClicked(View view) throws JSONException {
        Double latitude = Double.parseDouble(((EditText)findViewById(R.id.editLatitude))
                .getText().toString());
        Double longitude = Double.parseDouble(((EditText)findViewById(R.id.editLongitude))
                .getText().toString());

        String locationName =((EditText)findViewById(R.id.editLocationName)).getText().toString();

        Point point = new Point(latitude,longitude);
        Feature feature = new Feature(point);
        //Set optional feature identifier
        feature.setIdentifier(locationName);
        JSONObject test = new JSONObject();
        test.put("label", "W");
        test.put("title", locationName);

        //Set optiona feature properties
        feature.setProperties(test);
        //Convert to formatted JsonObject
        try {
            JSONObject geoJson = feature.toJSON();
            try {
                Utilities.WriteJsonObject(geoJson,"mapdata.json", this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}