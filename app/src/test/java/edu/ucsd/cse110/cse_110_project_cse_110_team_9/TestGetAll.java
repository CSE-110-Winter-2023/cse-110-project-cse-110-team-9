package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import edu.ucsd.cse110.cse_110_project_cse_110_team_9.database.ServerAPI;

@RunWith(AndroidJUnit4.class)
public class TestGetAll
{

    @Test
    public void testGetAll()
    {
         var test = ServerAPI.provide().getallPubliclyListedLocations();

         for (int i = 0; i < test.length; i ++)
         {

         }

    }
}

