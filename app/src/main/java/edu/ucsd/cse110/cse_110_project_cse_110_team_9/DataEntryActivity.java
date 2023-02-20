package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class DataEntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);
        loadProfile();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveProfile();
    }

    public void loadProfile() {
        SharedPreferences preferences = getSharedPreferences("my_prefs", MODE_PRIVATE);
        float latitude = preferences.getFloat("lat", 0);
        TextView latView = findViewById(R.id.editTextTextPersonName);
        latView.setText(String.valueOf(latitude));

        float longitude = preferences.getFloat("long", 0);
        TextView longView = findViewById(R.id.editTextTextPersonName2);
        longView.setText(String.valueOf(longitude));
    }

    public void saveProfile() {
        // Get the number input from the user
        EditText latInput = findViewById(R.id.editTextTextPersonName);
        Float lat_num = Float.parseFloat(latInput.getText().toString());;
        // Save the number to SharedPreferences
        SharedPreferences preferences = getSharedPreferences("my_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat("lat", lat_num);
        editor.apply();

        EditText longInput = findViewById(R.id.editTextTextPersonName2);
        Float long_num = Float.parseFloat(longInput.getText().toString());;
        // Save the number to SharedPreferences
        editor = preferences.edit();
        editor.putFloat("long", long_num);
        editor.apply();
    }

    public void onExitClicked(View view) {
        finish();
    }
}