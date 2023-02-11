package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;

import com.cocoahero.android.geojson.Point;


public class InputLocations extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_locations);
    }

    public void onSaveLocationClicked(View view) {
        Double latitude = Double.parseDouble(((EditText)findViewById(R.id.editLatitude))
                .getText().toString());
        Double longitude = Double.parseDouble(((EditText)findViewById(R.id.editLongitude))
                .getText().toString());

        Point point = new Point(latitude,longitude);

    }
}