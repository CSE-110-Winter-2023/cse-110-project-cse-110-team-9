package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestUtils {

    @Test
    public void testUtils(){


        var expected  = 96.51;
        var latA = (39.099912);
        var longA = (-94.581213);
        var latB = (38.627089);
        var longB = (-90.200203);

       var result =  Utilities.angleBetweenTwoLocations(latA, longA, latB, longB);
       // v/ar result = Utilities.angleBetweenTwoLocations(Math.toRadians())a
        assertEquals(expected,result, 0.1);


    }
}
