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

    public static double angleBetweenTwoLocations(double fromLatitude, double fromLongitude, double toLatitude, double toLongitude) {

//

        var latA = Math.toRadians(fromLatitude);
        var longA = Math.toRadians(fromLongitude);
        var latB = Math.toRadians(toLatitude);
        var longB = Math.toRadians(toLongitude);

        //Beta = atan(x,y
        var deltaL = longB - longA;
        //L is longitude
        //theta is latitude
        var x = Math.cos(latB) * Math.sin(deltaL);
        var y = Math.cos(latA) * Math.sin(latB) - (Math.sin(latA) * Math.cos(latB) * Math.cos(deltaL));
        var beta = Math.atan2(x, y);
        var betaDeg = Math.toDegrees(beta);
        betaDeg = (betaDeg + 360) % 360;
        return betaDeg;
    }


}
