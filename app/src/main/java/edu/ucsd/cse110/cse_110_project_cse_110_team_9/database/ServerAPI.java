package edu.ucsd.cse110.cse_110_project_cse_110_team_9.database;

import androidx.annotation.WorkerThread;

import okhttp3.OkHttpClient;

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
    public Friend getFriend(String UID)
    {

        return null;
    }




}

