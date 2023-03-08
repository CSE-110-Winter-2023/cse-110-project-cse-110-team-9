package edu.ucsd.cse110.cse_110_project_cse_110_team_9.database;

import androidx.annotation.AnyThread;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class ServerAPI {

    private volatile static ServerAPI instance = null;

    private OkHttpClient client;

    private ScheduledThreadPoolExecutor threadPool;


    //private MediatorLiveData<List<Friend>> friendsLive;
    private HashMap<String, ScheduledFuture<?>> friendsScheduledFutureHashMap;


    public ServerAPI() {
        this.client = new OkHttpClient();

        friendsScheduledFutureHashMap = new HashMap<>();
        this.threadPool = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(4);


    }

    public static ServerAPI provide() {
        if (instance == null) {
            instance = new ServerAPI();


        }
        return instance;
    }

    /**
     * Gets updates continously from the server for friend give UID
     * @param public_uid
     * @return
     */
    public LiveData<Friend> getFriendUpdates(String public_uid) {

        var remoteFriend = new MediatorLiveData<Friend>();

        var future = threadPool.scheduleWithFixedDelay(() ->
                {
                    var friend = getFriend(public_uid);
                    remoteFriend.postValue(friend);
                    System.out.println("Got friend: " + friend.label);
                }

                , 0, 5, TimeUnit.SECONDS);
        friendsScheduledFutureHashMap.put(public_uid, future);
        return remoteFriend;
        //need to add this to the Mutable live data lis
    }


    public void shutDownPool() {
        threadPool.shutdown();
    }

    /**
     * Gets a friends location from the server
     *
     * @param public_uid this is the uid that your friend gives you
     * @return Friend POJO
     */
    @WorkerThread
    public Friend getFriend(String public_uid) {
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

    @AnyThread
    public Future<Friend> getFriendAsync(String public_uid) {
        var executor = Executors.newSingleThreadExecutor();
        var future = executor.submit(() -> getFriend(public_uid));
        return future;

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


        try (var response = client.newCall(request).execute()) {

            System.out.println(response.body().toString());


        } catch (Exception e) {
            e.printStackTrace();

        }

    }


}

