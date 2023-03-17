package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestUtils {

    @Test
    public void testAngleBetweenTwoLocations(){


        var expected  = 96.51;
        var latA = (39.099912);
        var longA = (-94.581213);
        var latB = (38.627089);
        var longB = (-90.200203);

       var result =  Utilities.angleBetweenTwoLocations(
               new Location(latA, longA), new Location(latB,longB));
       // v/ar result = Utilities.angleBetweenTwoLocations(Math.toRadians())a
        assertEquals(expected,result, 0.1);


        //test ucsd to MIT
        expected = 61.61;
        latA = 32.8801;
        longA = -117.2340;
        latB = 42.3601;
        longB = -71.0942;
         result =  Utilities.angleBetweenTwoLocations(
                new Location(latA, longA), new Location(latB,longB));
        // v/ar result = Utilities.angleBetweenTwoLocations(Math.toRadians())a
        assertEquals(expected,result, 0.1);



        //North korea ryugyong hotel
        latA = 39.0362;
        longA = 125.7310;

        latB = -34.603722;
        longB = -58.381592;

        expected = 37.855;
        result =  Utilities.angleBetweenTwoLocations(
                new Location(latA, longA), new Location(latB,longB));
        // v/ar result = Utilities.angleBetweenTwoLocations(Math.toRadians())a
        assertEquals(expected,result, 0.1);
    }

    @Test
    public void testDistanceBetweenTwoPoints()
    {

        var expected  = 382.9;
        var latA = (39.099912);
        var longA = (-94.581213);
        var latB = (38.627089);
        var longB = (-90.200203);

        var result = Utilities.findDistanceinKMBetweenTwoPoints(
                new Location(latA, longA), new Location(latB,longB));

        System.out.println("Distance: " + result);
        assertEquals(expected, result, 0.2);


         latA = (32.862020);
         longA = (-117.213690);
         latB = (37.950530);
         longB = (-122.542780);

         result = Utilities.findDistanceinKMBetweenTwoPoints(
                new Location(latA, longA), new Location(latB,longB));


        System.out.println("home is:" + Utilities.KMtoMiles(result) + " Miles");



    }
}
