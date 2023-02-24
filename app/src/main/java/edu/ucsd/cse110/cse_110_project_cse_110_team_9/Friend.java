package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "my_friends")
public class Friend {

    @PrimaryKey(autoGenerate = false)//might need to change this
    public long id;

    @NonNull
    public String firstName;
    public String lastName;

    public double latitude;
    public double longitude;

    public Friend(@NonNull String firstName, String lastName, double latitude, double longitude){
        this.firstName = firstName;
        this.lastName = lastName;
        this.longitude = longitude;
        this.latitude = latitude;
    }

}
