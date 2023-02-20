package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class DegreeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_degree);
        loadProfile();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveProfile();
    }

    public void loadProfile() {
        SharedPreferences preferences = getSharedPreferences("my_prefs", MODE_PRIVATE);
        float degree = preferences.getFloat("degree", 0);
        TextView nameView = findViewById(R.id.compassDegree);
        nameView.setText(String.valueOf(degree));
    }

    public void saveProfile() {
        // Get the number input from the user
        EditText numberInput = findViewById(R.id.compassDegree);
        Float number = Float.parseFloat(numberInput.getText().toString());;
        // Save the number to SharedPreferences
        SharedPreferences preferences = getSharedPreferences("my_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat("degree", number);
        editor.apply();
    }


    public void onExitClicked(View view) {
        EditText numberInput = findViewById(R.id.compassDegree);
        Float number = Float.parseFloat(numberInput.getText().toString());;

        if(number > 360 || number < 0){
            Utilities.showAlert(this, "Input a double number between 0 and 360");
        }
        else{
            finish();
        }
    }
}