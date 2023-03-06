package edu.ucsd.cse110.cse_110_project_cse_110_team_9.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@Entity(tableName = "user")
public class User {

    @SerializedName("public_uid")
    @NonNull
    String public_uid;

    @SerializedName("private_code")
    @NonNull
    @Expose
    String private_code;


    @SerializedName("latitude")
    @NonNull
    @Expose
    double latitude;

    @SerializedName("longitude")
    @NonNull
    @Expose
    double longitude;

    @SerializedName("label")
    @NonNull
    @Expose
    String label; //THIS IS THE USERS NAME

    private String updated_at;


    public User(@NonNull String label, @NonNull String private_code,
                @NonNull String public_uid, double latitude, double longitude) {

        this.label = label;
        this.latitude = latitude;
        this.longitude = longitude;
        this.private_code = private_code;
        this.public_uid = public_uid;
    }

    public static User fromJSON(String json) {
        return new Gson().fromJson(json, User.class);
    }

    public String toJSON() {
        // return new Gson().toJson(this);
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        return gson.toJson(this);

    }

}
