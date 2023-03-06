package edu.ucsd.cse110.cse_110_project_cse_110_team_9.database;

import android.util.Log;

import androidx.annotation.WorkerThread;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

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


    /**
     * Gets a friends location from the server
     * @param public_uid this is the uid that your friend gives you
     * @return Friend POJO
     */
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


    @WorkerThread
    public void updateUserLocation(User user) {
        var userJson = user.toJSON();

        System.out.println(userJson);

        RequestBody body = RequestBody.create(userJson, MediaType.parse("application/json; charset=utf-8"));

        var request = new Request.Builder()
                .url("https://socialcompass.goto.ucsd.edu/location/" + user.public_uid)
                .put(body)
                .build();


        System.out.println(request.body().toString());


        try (
                var response = client.newCall(request).execute()) {

                System.out.println(response.body().toString());


        } catch (Exception e) {
            e.printStackTrace();

        }

    }


//    @WorkerThread
//    public boolean putLocation(String private_code, )
//




}

