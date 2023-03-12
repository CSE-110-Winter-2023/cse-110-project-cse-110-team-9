package edu.ucsd.cse110.cse_110_project_cse_110_team_9.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
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
        //TODO: Finish this method and implement map scale.
        var distance =
                Utilities.findDistanceinMilesBetweenTwoPoints(userLocation, friendLocation);
        //scale

        View parent = (View)getParent();
        int width = parent.getWidth();

        setRadius(width/2- Constants.EDGE_PADDING);
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

    @Override
    protected void onDraw(final Canvas canvas){
        super.onDraw(canvas);
    }
}
