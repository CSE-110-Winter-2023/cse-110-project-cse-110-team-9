package edu.ucsd.cse110.cse_110_project_cse_110_team_9.database;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FriendArray {

    @SerializedName("friendList")
    @NonNull
    public List<Friend> allFriends;

    public static FriendArray fromJson(String json) {
        return new Gson().fromJson(json, FriendArray.class);
    }

}


