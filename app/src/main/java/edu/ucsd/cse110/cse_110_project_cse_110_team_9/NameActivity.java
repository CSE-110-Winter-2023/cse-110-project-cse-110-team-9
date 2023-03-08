package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NameActivity extends AppCompatActivity {


    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);



        loadProfile();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void loadProfile() {
        SharedPreferences preferences = getSharedPreferences("username", MODE_PRIVATE);
        String name = preferences.getString("name", "");
        TextView nameView = findViewById(R.id.enter_name);
        nameView.setText(name);
    }

    public void saveProfile() {
        // Get the name input from the user
        EditText nameInput = findViewById(R.id.enter_name);
        String name = nameInput.getText().toString();
        // Save the number to SharedPreferences
        SharedPreferences preferences = getSharedPreferences("username", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("name", name);

        Intent intent = getIntent().putExtra("name", name);
        setResult(RESULT_OK, intent);
        editor.apply();
    }


    public void onExitClicked(View view) {

        saveProfile();
        finish();
    }
}
