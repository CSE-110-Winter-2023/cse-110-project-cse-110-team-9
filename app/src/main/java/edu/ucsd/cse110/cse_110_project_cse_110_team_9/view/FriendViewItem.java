package edu.ucsd.cse110.cse_110_project_cse_110_team_9.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.provider.ContactsContract;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Pair;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import edu.ucsd.cse110.cse_110_project_cse_110_team_9.Constants;
import edu.ucsd.cse110.cse_110_project_cse_110_team_9.Location;
import edu.ucsd.cse110.cse_110_project_cse_110_team_9.R;
import edu.ucsd.cse110.cse_110_project_cse_110_team_9.Utilities;
import edu.ucsd.cse110.cse_110_project_cse_110_team_9.database.Friend;
import edu.ucsd.cse110.cse_110_project_cse_110_team_9.services.LocationService;
import edu.ucsd.cse110.cse_110_project_cse_110_team_9.services.OrientationService;


//THIS IS WHERE THE MAGIC HAPPENS BOIS
public class FriendViewItem extends LinearLayout {

    LinearLayout layout;
    TextView nameLabel;
    TextView friendIcon;
    ImageView friendImg;
    Context mContext;

    private OrientationService orientationService;
    private LocationService locationService;

    private Constants.scale zoomLevel;


    //used to store the location of the user when we get an update from the the locationService.
    private Location userLocation;


    //USed to store the location of the friend when we get an update from the server
    private Location friendLocation;

    private double distance = 0;

    //used to store the angle of the user when we get an update from the orientationService.

    private float userAngle = 0;


    //used to store
    private Friend friend;


    private int scale = 1;
    public FriendViewItem(Context context)
    {
        super(context);
        init(context);
    }

    public void init(Context context)
    {
        String service = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(service);

        layout = (LinearLayout) li.inflate(R.layout.friend_view_item, this, true);

        nameLabel = (TextView) layout.findViewById(R.id.friendName);

        friendIcon=  (TextView) layout.findViewById(R.id.friendIcon);

        friendLocation = new Location(0d,0d);

        userLocation = new Location(0d, 0d);

        // rightTextView = (TextView) layout.findViewById(R.id.right_text);

        nameLabel.setText("default_string");
        friendIcon.setText("default_icon");

        zoomLevel = Constants.scale.TEN;
        mContext = context;

    }

    public FriendViewItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    public FriendViewItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void setNameLabel(String name) {
       nameLabel.setText(name);
    }

    public void setData(LiveData<Friend> friend, LifecycleOwner owner)
    {
        friend.observe(owner,this::onFriendDataChange);
    }

    public void setFriendIcon(String text){
        friendIcon.setText(text);
    }

    public void setOrientationService(OrientationService service, LifecycleOwner owner){
        this.orientationService = service;
        service.getAzimuthData().observe(owner, this::onUserOrientationChanged);
    }

    public void setLocationService(LocationService service, LifecycleOwner owner)
    {
        locationService =service;
        service.getLocation().observe(owner, this::onUserLocationChanged);
    }

    /**
     * Update the friends angle and radius when the user's location changes
     * @param location of the user
     */
    public void onUserLocationChanged(Pair<Double, Double> location)
    {
        if (userLocation!= null && location.first != null && location.second != null) {
            userLocation.setLatitude(location.first);
            userLocation.setLongitude(location.second);
            reCalcualteAngle();

        }
    }

    /**
     * Update the friend's angle when the users orientation changes
     * @param orientation of the user
     */
    public void onUserOrientationChanged(Float orientation)
    {
        userAngle = orientation;
        reCalcualteAngle();
    }


    /**
     * Set the ange of the friend
     * @param angle to set
     */
    private void setAngle(int angle)
    {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.getLayoutParams();
        layoutParams.circleAngle = angle;
        this.setLayoutParams(layoutParams);

    }

    /**
     * Set the radius of the friend
     * @param radius
     */
    private void setRadius(int radius)
    {

        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.getLayoutParams();
        layoutParams.circleRadius = radius;
        this.setLayoutParams(layoutParams);

        Log.d(Double.toString(distance), Integer.toString(radius));
    }

    /**
     * Recaultes what the angle for the friend should be based on the user's location, the
     * friends location, and the user's orientation
     */
    public void reCalcualteAngle()
    {
        if (userLocation != null && friendLocation != null) {
            var angle = Utilities.angleBetweenTwoLocations(userLocation, friendLocation);
            angle = angle + userAngle;
            setAngle((int) angle);
        }
    }

    /**
     * reCalcuates the radius of the friend based on the distance between the friend and the user
     * and the current zoom level of the map.
     */
    public void reCalculateRadius()
    {
         distance = Utilities.findDistanceinMilesBetweenTwoPoints(userLocation, friendLocation);

        //IDK THIS IS JANK BUT IT WORKS

        View parent = (View)getParent();
        int width = parent.getWidth();
        Log.d("FriendView", Integer.toString(width));


        // float radius = (float)width/2 - Constants.OUTER_RING_PADDING;

        float circleRadius = (float) width / 2 - Constants.OUTER_RING_PADDING;


        switch (zoomLevel) {
            case ONE:
                 circleRadius = (float) width / 2 - Constants.OUTER_RING_PADDING;
                if (distance > 1)
                {
                    this.setVisibility(INVISIBLE);
                }
                else {
                    this.setVisibility(VISIBLE);
                    float pixelsPerMile = circleRadius/1;
                    float radius = (float) (pixelsPerMile * distance);
                    setRadius((int) radius);
                }
                break;
            case TEN:
                 float oneCircleRadius = circleRadius/ 2;
                if (distance < 1)
                {
                    //DRAW in the inner most circle
                    this.setVisibility(VISIBLE);
                    float pixelsPerMile = oneCircleRadius/1;
                    float radius = (float) (pixelsPerMile * distance);
                    setRadius((int) radius);
                }
                else if (distance < 10) {
                    this.setVisibility(VISIBLE);
                    float pixelsPerMile = oneCircleRadius/10; //note that oneCircleRadius is the
                    // distance between onemile ring and 10 miles ring
                    float radius = ((float) (pixelsPerMile * distance)) + oneCircleRadius; // we
                    // add onecircleradius so we draw outside of 1 miles ring
                    setRadius((int) radius);
                }
                else
                {
                    this.setVisibility(INVISIBLE);
                }
                break;
            case FIVE_HUNDRED:
                float oneCircleRadius3 = circleRadius/3;

                if (distance < 1)
                {
                    this.setVisibility(VISIBLE);
                    float pixelsPerMile = oneCircleRadius3/1;
                    float radius = (float) (pixelsPerMile * distance);
                    setRadius((int) radius);
                }
                else if (distance < 10)
                {
                    this.setVisibility(VISIBLE);
                    float pixelsPerMile = oneCircleRadius3/10; //note that oneCircleRadius is the
                    // distance between ten mile ring and 500 mile ring miles ring
                    float radius = ((float) (pixelsPerMile * distance)) + oneCircleRadius3; // we
                    // add onecircleradius so we draw outside of 1 miles ring
                    setRadius((int) radius);
                }
                else if (distance < 500){
                    this.setVisibility(VISIBLE);
                    float pixelsPerMile = oneCircleRadius3/500; //note that oneCircleRadius is the
                    // distance between onemile ring and 10 miles ring
                    float radius = ((float) (pixelsPerMile * distance)) + (2*oneCircleRadius3); // we
                    // add onecircleradius so we draw outside of 1 miles ring
                    setRadius((int) radius);
                }
                else{
                    this.setVisibility(INVISIBLE);
                }
                break;
            case FIVE_HUNDRED_PLUS:
                float oneCircleRadius3plus = circleRadius/3;

                if (distance < 1)
                {
                    this.setVisibility(VISIBLE);
                    float pixelsPerMile = oneCircleRadius3plus/1;
                    float radius = (float) (pixelsPerMile * distance);
                    setRadius((int) radius);
                }
                else if (distance < 10)
                {
                    this.setVisibility(VISIBLE);
                    float pixelsPerMile = oneCircleRadius3plus/10; //note that oneCircleRadius is
                    // the
                    // distance between ten mile ring and 500 mile ring miles ring
                    float radius = ((float) (pixelsPerMile * distance)) + oneCircleRadius3plus;
                    // we
                    // add onecircleradius so we draw outside of 1 miles ring
                    setRadius((int) radius);
                }
                else if (distance < 500){
                    this.setVisibility(VISIBLE);
                    float pixelsPerMile = oneCircleRadius3plus/500; //note that oneCircleRadius is
                    // the
                    // distance between onemile ring and 10 miles ring
                    float radius = ((float) (pixelsPerMile * distance)) + (2*oneCircleRadius3plus);
                    // we
                    // add onecircleradius so we draw outside of 1 miles ring
                    setRadius((int) radius);
                }
                else{
                    this.setVisibility(VISIBLE);
                    setRadius((int) circleRadius);
                }
                break;
        }

        //pixels per miles o
        //if scale is 10 miles then we have (width/2) / 10
    }




    /**
     * update the local instance  for the friend's location whenever we get an update from
     * the server
     * @param friend
     */
    public void onFriendDataChange(Friend friend){

        setNameLabel(friend.label);
        this.friend = friend;
        friendLocation.setLatitude(friend.latitude);
        friendLocation.setLongitude(friend.longitude);
        reCalcualteAngle();
        reCalculateRadius();
    }


    public void setScaleObserver(LiveData<Constants.scale> zoomLevel, LifecycleOwner owner){
        zoomLevel.observe(owner, this::onZoomLevelChanged);
        reCalculateRadius();
        reCalcualteAngle();
    }

    private void onZoomLevelChanged(Constants.scale zoomLevel)
    {
        this.zoomLevel = zoomLevel;
        reCalculateRadius();
    }

    @Override
    protected void onDraw(final Canvas canvas){
        super.onDraw(canvas);
    }
}
