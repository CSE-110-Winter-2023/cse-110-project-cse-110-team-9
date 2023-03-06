package edu.ucsd.cse110.cse_110_project_cse_110_team_9.database;

import android.util.Log;

import androidx.annotation.WorkerThread;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class ServerAPI {

    private volatile static ServerAPI instance = null;

    private OkHttpClient client;


    public ServerAPI(){
        this.client = new OkHttpClient();

    }

    public static ServerAPI provide(){
        if (instance == null){
            instance = new ServerAPI();
        }
        return instance;
    }


    @WorkerThread
    public Friend getFriend(String public_uid)
    {

        var request = new Request.Builder()
                .url("https://socialcompass.goto.ucsd.edu/location/" + public_uid)
                .method("GET", null)
                .build();

        try (var response = client.newCall(request).execute()) {
            assert response.body() != null;
            var body = response.body().string();


            return Friend.fromJSON(body);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }




}

