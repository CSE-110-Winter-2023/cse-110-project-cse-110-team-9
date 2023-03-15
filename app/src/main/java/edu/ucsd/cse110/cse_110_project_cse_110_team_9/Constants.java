package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import org.json.JSONException;
import org.json.JSONObject;

public class Constants {
    public static final float NORTH = 0;
    public static final float EAST = (float) (Math.PI / 2);
    public static final float SOUTH = (float) Math.PI;
    public static final float WEST = (float) (3 * Math.PI / 2);

    public static final int NAME_ACTIVITY_REQUEST_CODE = 0;
    public static final int ADD_FRIEND_ACTIVITY_REQUEST_CODE = 1;

    public static final int EDGE_PADDING = 80;
    public static final int OUTER_RING_PADDING = 40;

    public static final int TIME_BETWEEN_USE_LOCATION_UPDATES = 3;

    public static final int TIME_SERVICE_UPDATE_INTERVAL = 30;

    public static final String FRIEND_DIRECTION_INDICATOR_EMOIJI =
            new String(Character.toChars(0x1F539));
    public enum scale{
        ONE, TEN, FIVE_HUNDRED, FIVE_HUNDRED_PLUS
    }

    public static String LocationNotFoundJsonResponse;

    static {
        try {
            LocationNotFoundJsonResponse = new JSONObject().put("detail","Location not found.").toString();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }


    public static String LocationDeletedSuccesfullyResponse;

    static {
        try {
            LocationDeletedSuccesfullyResponse = new JSONObject().put("message","Location deleted.").toString();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static String InvalidPrivateCodeResponse;

    static {
        try {
            InvalidPrivateCodeResponse = new JSONObject().put("detail","Invalid private_code.").toString();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

}
