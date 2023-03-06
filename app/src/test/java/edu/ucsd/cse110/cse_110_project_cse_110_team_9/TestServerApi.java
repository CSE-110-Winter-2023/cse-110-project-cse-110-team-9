package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import org.junit.Before;
import org.junit.Test;

import edu.ucsd.cse110.cse_110_project_cse_110_team_9.database.ServerAPI;

public class TestServerApi {


    @Test
    public void testServer()
    {


        String public_uid = "point-nemo";

        var friend = ServerAPI.provide().getFriend(public_uid);

        var result = friend.toJSON();

        System.out.println(result);

    }

}
