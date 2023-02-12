package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import edu.ucsd.cse110.cse_110_project_cse_110_team_9.Map.MapUIState;
import edu.ucsd.cse110.cse_110_project_cse_110_team_9.Map.MapViewModel;
import edu.ucsd.cse110.cse_110_project_cse_110_team_9.Map.MapsFragment;
import edu.ucsd.cse110.cse_110_project_cse_110_team_9.Map.SharedViewModel;

public class MainActivity extends AppCompatActivity {


    private MapViewModel viewModel;
    private SharedViewModel model;
    private MapsFragment mapsFrag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        viewModel = new ViewModelProvider(this).get(MapViewModel.class);

        model =  new ViewModelProvider(this).get(SharedViewModel.class);

    }

    public void OnLaunchMap(View view) {
        Double lat =  Double.parseDouble(((EditText) findViewById(R.id.enterLatitudeBox)).getText().toString());
        Double longy =  Double.parseDouble(((EditText) findViewById(R.id.enterLongitudeBox)).getText().toString());

        model.setItem(new MapUIState(lat, longy));

    }

    public void setOnDataListener(MapsFragment mapsFragment)
    {
        mapsFrag = mapsFragment;
    }
}