package edu.ucsd.cse110.cse_110_project_cse_110_team_9.database;

import android.util.Log;
import androidx.annotation.AnyThread;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import edu.ucsd.cse110.cse_110_project_cse_110_team_9.Constants;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class ServerAPI {
    private volatile static ServerAPI instance = null;
    private OkHttpClient client;
    private ScheduledThreadPoolExecutor threadPool;
    private HashMap<String, ScheduledFuture<?>> friendsScheduledFutureHashMap;

    public ServerAPI() {
       this.client = new OkHttpClient();
//        this.client = new OkHttpClient.Builder()
//                .connectTimeout(10, TimeUnit.SECONDS)
//                .writeTimeout(10, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .build();

        friendsScheduledFutureHashMap = new HashMap<>();
        this.threadPool = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(4);
    }


    public void initThreadPool()
    {
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
                    var friend = getFriendFromRemote(public_uid);
                    remoteFriend.postValue(friend);
                    //System.out.println("Got friend: " + friend.label);
                }

                , 0, 3, TimeUnit.SECONDS);
        friendsScheduledFutureHashMap.put(public_uid, future);
        return remoteFriend;
        //need to add this to the Mutable live data lis
    }

    public void shutDownPool() {
        threadPool.shutdown();
    }

    public void restartThreadPool() {
       threadPool = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(4);
    }

    /**
     * Gets a friends location from the server
     *
     * @param public_uid this is the uid that your friend gives you
     * @return Friend POJO
     */
    @WorkerThread
    public Friend getFriendFromRemote(String public_uid) {
        var request = new Request.Builder()
                .url(Constants.serverEndpoint + "/location/" + public_uid)
                .method("GET", null)
                .build();

        try (var response = client.newCall(request).execute()) {

            assert response.body() != null;
            var body = response.body().string();

            if (body.equals(Constants.LocationNotFoundJsonResponse)) {
                return null;
            }
            return Friend.fromJSON(body);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @WorkerThread
    public Friend[] getallPubliclyListedLocations() {
        var request = new Request.Builder()
                .url(Constants.serverEndpoint + "/locations")
                .method("GET", null)
                .build();

        System.out.println(request.toString());

        try (var response = client.newCall(request).execute()) {

            assert response.body() != null;
            var body = response.body().string();

            if (body.equals(Constants.LocationNotFoundJsonResponse)) {
                return null;
            }

            //System.out.println(body);
           return new Gson().fromJson(body, Friend[].class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @AnyThread
    public Future<Friend[]> getallPublicListedLocationsAsync( ) {
        var executor = Executors.newSingleThreadExecutor();
        var future = executor.submit(() -> getallPubliclyListedLocations());
        return future;
    }


    @AnyThread
    public Future<Friend> getFriendAsync(String public_uid) {
        var executor = Executors.newSingleThreadExecutor();
        var future = executor.submit(() -> getFriendFromRemote(public_uid));
        return future;
    }

    @AnyThread
    public boolean deleteUserLocationOnRemote(User user)
    {
        JSONObject deleteJson = new JSONObject();

        try {
            deleteJson.put("private_code", user.getPrivate_code());

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        RequestBody body = RequestBody.create(deleteJson.toString(), MediaType.parse("application/json; charset=utf-8"));

        var request = new Request.Builder()
                .url(Constants.serverEndpoint + "/location/" + user.get_public_code())
                .delete(body)
                .build();

        try (var response = client.newCall(request).execute()) {


            assert response.body() != null;
            var responseBody = response.body().string();
           // Log.d("Delete user location from server:", responseBody);

            return (responseBody.equals(Constants.LocationDeletedSuccesfullyResponse));

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @AnyThread
    public Future<Boolean> deleteUserLocationOnRemoteAsync(User user)
    {
        var executer = Executors.newSingleThreadExecutor();
        var future = executer.submit(()->deleteUserLocationOnRemote(user));
        return future;
    }

    @AnyThread
    public Future<Boolean> updateUserLocationAsync(User user)
    {
        var executer = Executors.newSingleThreadExecutor();
        var future = executer.submit(()->updateUserLocation(user));
        return future;
    }
    @WorkerThread
    public boolean updateUserLocation(User user) {
        var userJson = user.toJSON();

        //Log.d("Trying to send to server user:", userJson);
        RequestBody body = RequestBody.create(userJson, MediaType.parse("application/json; charset=utf-8"));

        var request = new Request.Builder()
                .url(Constants.serverEndpoint + "/location/" + user.public_code)
                .put(body)
                .build();


        try (var response = client.newCall(request).execute()) {
            //Log.d("Response from updating user location",response.body().toString());

            assert response.body() != null;
            String res = response.body().string();
            JSONObject jsonRes = new JSONObject(res);

            return (jsonRes.get("label").equals(user.label) &&
                    jsonRes.get("public_code").equals(user.get_public_code())
                    && jsonRes.get("latitude").equals(user.getLatitude())
                    && jsonRes.get("longitude").equals(user.getLongitude())
            );

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }
}

