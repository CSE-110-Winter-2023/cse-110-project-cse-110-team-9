package edu.ucsd.cse110.cse_110_project_cse_110_team_9.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.GenericArrayType;


@Entity(tableName = "friends")
public class Friend {


    //Friend public code used as the primary key for friends (
    @PrimaryKey
    @SerializedName("public_uid")
    @NonNull
    public String public_uid;


    //This will contain the friend's name
    @SerializedName("label")
    @NonNull
    public String label;
    @SerializedName("latitude")
    @NonNull
    public double latitude;

    @SerializedName("longitude")
    @NonNull
    public double longitude;

    @SerializedName("created_at")
    @NonNull
    public String created_at;

    @SerializedName("updated_at")
    @NonNull
    public String updated_at;


    public Friend(@NonNull String public_uid, @NonNull String label,
                  @NonNull double latitude, @NonNull double longitude,
                  String created_at, String updated_at) {

        this.public_uid = public_uid;
        this.latitude = latitude;
        this.longitude = longitude;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.label = label;
    }

    public static Friend fromJSON(String json) {
        return new Gson().fromJson(json, Friend.class);
    }

    public String toJSON() {
        return new Gson().toJson(this);
    }

}
