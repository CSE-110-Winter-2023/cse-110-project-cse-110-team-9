package edu.ucsd.cse110.cse_110_project_cse_110_team_9.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@Entity(tableName = "user")
public class User {


    @PrimaryKey
    @SerializedName("public_code")
    @NonNull
    String public_code;

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
    public String label; //THIS IS THE USERS NAME


   @NonNull
    long updated_at;


    public User(@NonNull String label, @NonNull String private_code,
                @NonNull String public_code, double latitude, double longitude, long updated_at) {

        this.label = label;
        this.latitude = latitude;
        this.longitude = longitude;
        this.private_code = private_code;
        this.public_code = public_code;
        this.updated_at = updated_at;
    }

    @NonNull
    public String getPrivate_code() {
        return private_code;
    }

    @NonNull
    public String get_public_code() {
        return public_code;
    }




    public void setPrivate_code(@NonNull String private_code) {
        this.private_code = private_code;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @NonNull
    public String getLabel() {
        return label;
    }

    public void setLabel(@NonNull String label) {
        this.label = label;
    }

    @NonNull
    public long getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(@NonNull long updated_at) {
        this.updated_at = updated_at;
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
