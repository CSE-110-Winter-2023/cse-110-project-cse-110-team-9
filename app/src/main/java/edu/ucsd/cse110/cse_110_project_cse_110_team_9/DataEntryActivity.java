package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DataEntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);
    }

    public void onClickSubmit(View view){
        AddLocation temp = new AddLocation(R.id.labelName, (double) R.id.latitude, (double) R.id.longitude);
//        Intent intent = new Intent(this, AddLocation.class);
//        startActivity(intent);
    }

    public void onClickGoBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}