package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import android.app.Activity;
import android.app.AlertDialog;

import java.util.Locale;

public class Utilities {

    static String formatOrientation(float azimuth) {
        float degrees = (float) Math.toDegrees(azimuth);
        return String.format(Locale.US, "%.0f degrees from North", degrees);
    }

    static String formatLocation(double latitude, double longitude) {
        return String.format(Locale.US, "%.0f° %.0f' %.0f\" N, %.0f° %.0f' %.0f\" W",
                Math.abs(latitude), Math.abs(latitude % 1) * 60, Math.abs(latitude % 1 % 1) * 60,
                Math.abs(longitude), Math.abs(longitude % 1) * 60, Math.abs(longitude % 1 % 1) * 60);
    }

    public static void showAlert(Activity activity, String message) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activity);

        alertBuilder.setTitle("Alert!").setMessage(message).setPositiveButton("Ok", (dialog, id) -> {
            dialog.cancel();
        }).setCancelable(true);

        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }

    static String formatTime(long time) {
        return String.format(Locale.US, "%tT %tZ", time, time);
    }

    /**
     * Returns the angle in degrees from point A to point B

     * @return angle in degrees
     */
    public static double radiansBetweenTwoLocations(Location a, Location b) {

//

        var latA = a.getLatitudeRadians();
        var longA = a.getLongitudeRadians();
        var latB = b.getLatitudeRadians();
        var longB = b.getLongitudeRadians();

        var deltaL = longB - longA;

        var x = Math.cos(latB) * Math.sin(deltaL);
        var y = Math.cos(latA) * Math.sin(latB) - (Math.sin(latA) * Math.cos(latB) * Math.cos(deltaL));
        var beta = Math.atan2(x, y);
        return beta;
        // var betaDeg = Math.toDegrees(beta);
        //betaDeg = (betaDeg + 360) % 360;
        // return betaDeg;
    }

    public static double angleBetweenTwoLocations(Location a, Location b) {
        var beta = radiansBetweenTwoLocations(a, b);
        var deg = Math.toDegrees(beta);
        deg = (deg + 360) % 360;
        return deg;
    }


    public static double findDistanceinKMBetweenTwoPoints(Location A, Location B) {


        var latA = A.getLatitudeRadians();
        var longA = A.getLongitudeRadians();
        var latB = B.getLatitudeRadians();
        var longB = B.getLongitudeRadians();

       /*
        Haverine Formula:
        a = sin²(Δφ/2) + cos φ1 ⋅ cos φ2 ⋅ sin²(Δλ/2)
        c = 2 ⋅ atan2( √a, √(1−a) )
        d = R ⋅ c
        where 	φ is latitude, λ is longitude, R is earth’s radius (mean radius = 6,371km);
        note that angles need to be in radians to pass to trig functions!

        */
        double deltaLong = longB - longA;
        double deltaLat = latB - latA;
        double R = 6371; // Radius of earth in kilometers

        double a = Math.pow((Math.sin(deltaLat/2)), 2) +
                Math.cos(latA) * Math.cos(latB) * Math.pow(Math.sin(deltaLong/2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c; //calcualte distance in km
        return d;
    }

    public static double KMtoMiles(double KM)
    {
        return (KM * 0.621371);
    }
}
