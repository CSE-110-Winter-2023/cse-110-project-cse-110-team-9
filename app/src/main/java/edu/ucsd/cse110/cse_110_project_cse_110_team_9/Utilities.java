package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import android.app.Activity;
import android.app.AlertDialog;

import java.util.ArrayList;
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
     *
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

        double a = Math.pow((Math.sin(deltaLat / 2)), 2) +
                Math.cos(latA) * Math.cos(latB) * Math.pow(Math.sin(deltaLong / 2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c; //calcualte distance in km
        return d;
    }

    public static double KMtoMiles(double KM) {
        return (KM * 0.621371);
    }


    public static double findDistanceinMilesBetweenTwoPoints(Location A, Location B) {
        return KMtoMiles(findDistanceinKMBetweenTwoPoints(A, B));
    }




    public static ArrayList<String> getEmojis()
    {
        var emojiStrings = new ArrayList<String>();
        emojiStrings.add(new String(Character.toChars(0x1F99C)));
        emojiStrings.add(new String(Character.toChars(0x1F99A)));
        emojiStrings.add(new String(Character.toChars(0x1F9A9)));
        emojiStrings.add(new String(Character.toChars(0x1F9A4)));
        emojiStrings.add(new String(Character.toChars(0x1F986)));
        emojiStrings.add(new String(Character.toChars(0x1F985)));
        emojiStrings.add(new String(Character.toChars(0x1F54A)));
        emojiStrings.add(new String(Character.toChars(0x1F413)));
        emojiStrings.add(new String(Character.toChars(0x1F9A1)));
        emojiStrings.add(new String(Character.toChars(0x1F9A8)));
        emojiStrings.add(new String(Character.toChars(0x1F9A6)));
        emojiStrings.add(new String(Character.toChars(0x1F9A5)));
        emojiStrings.add(new String(Character.toChars(0x1F54A)));
        emojiStrings.add(new String(Character.toChars(0x1F987)));
        emojiStrings.add(new String(Character.toChars(0x1F994)));
        emojiStrings.add(new String(Character.toChars(0x1F54A)));
        emojiStrings.add(new String(Character.toChars(0x1F9AB)));
        emojiStrings.add(new String(Character.toChars(0x1F43F)));
        emojiStrings.add(new String(Character.toChars(0x1F407)));
        emojiStrings.add(new String(Character.toChars(0x1F400)));
        emojiStrings.add(new String(Character.toChars(0x1F401)));
        emojiStrings.add(new String(Character.toChars(0x1F99B)));
        emojiStrings.add(new String(Character.toChars(0x1F98F)));
        emojiStrings.add(new String(Character.toChars(0x1F9A3)));
        emojiStrings.add(new String(Character.toChars(0x1F992)));
        emojiStrings.add(new String(Character.toChars(0x1F999)));
        emojiStrings.add(new String(Character.toChars(0x1F42B)));
        emojiStrings.add(new String(Character.toChars(0x1F411)));
        emojiStrings.add(new String(Character.toChars(0x1F404)));
        emojiStrings.add(new String(Character.toChars(0x1F402)));
        emojiStrings.add(new String(Character.toChars(0x1F42E)));
        emojiStrings.add(new String(Character.toChars(0x1F9AC)));
        emojiStrings.add(new String(Character.toChars(0x1F98C)));
        emojiStrings.add(new String(Character.toChars(0x1F993)));
        emojiStrings.add(new String(Character.toChars(0x1F984)));
        emojiStrings.add(new String(Character.toChars(0x1F40E)));
        emojiStrings.add(new String(Character.toChars(0x1F406)));
        emojiStrings.add(new String(Character.toChars(0x1F405)));
        emojiStrings.add(new String(Character.toChars(0x1F408)));
        emojiStrings.add(new String(Character.toChars(0x1F429)));
        emojiStrings.add(new String(Character.toChars(0x1F415)));
        emojiStrings.add(new String(Character.toChars(0x1F98D)));
        emojiStrings.add(new String(Character.toChars(0x1F412)));
        return emojiStrings;
    }
}