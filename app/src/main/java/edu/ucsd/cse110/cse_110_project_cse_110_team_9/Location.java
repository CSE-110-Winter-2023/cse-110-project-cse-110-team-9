package edu.ucsd.cse110.cse_110_project_cse_110_team_9;


import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class Location {
    public Double longitude;
    public Double latitude;
    public String name;

    public Location(String name, Double latitude, Double longitude) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }


    @Override
    public String toString() {
        return "Location{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                ", name='" + name + '\'' +
                '}';
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitudeRadians() {
        return Math.toRadians(getLatitude());
    }

    public Double getLongitudeRadians() {
        return Math.toRadians(getLongitude());
    }

    public static List<Location> loadJson(Context context, String path){
        try{
            InputStream input = context.getAssets().open(path);
            Reader reader = new InputStreamReader(input);
            Gson gson = new Gson();
            Type type = new TypeToken<List<Location>>(){}.getType();
            return gson.fromJson(reader, type);
        }
        catch (IOException e){
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}
