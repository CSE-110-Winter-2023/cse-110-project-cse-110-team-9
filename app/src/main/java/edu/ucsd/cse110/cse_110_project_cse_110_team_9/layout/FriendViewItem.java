package edu.ucsd.cse110.cse_110_project_cse_110_team_9.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Pair;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import edu.ucsd.cse110.cse_110_project_cse_110_team_9.Location;
import edu.ucsd.cse110.cse_110_project_cse_110_team_9.R;
import edu.ucsd.cse110.cse_110_project_cse_110_team_9.Utilities;
import edu.ucsd.cse110.cse_110_project_cse_110_team_9.database.Friend;
import edu.ucsd.cse110.cse_110_project_cse_110_team_9.services.LocationService;
import edu.ucsd.cse110.cse_110_project_cse_110_team_9.services.OrientationService;

public class FriendViewItem extends LinearLayout {

    LinearLayout layout;
    TextView nameLabel;
    TextView friendIcon;
    ImageView friendImg;
    Context mContext;

    private OrientationService orientationService;
    private LocationService locationService;


    private Location userLocation;

    private Location friendLocation;

    private double distance = 0;

    private float userAngle = 0;

    private Friend friend;
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
        friendIcon.setText(new String(Character.toChars(0x1F4A9)));
        //friendIcon.setText("Test");
        // rightTextView.setText(rightText)
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
       // this.
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

    public void onUserLocationChanged(Pair<Double, Double> location)
    {

        if (location.first != null && location.second != null) {

            userLocation.setLatitude(location.first);
            userLocation.setLongitude(location.second);
            reCalcualteAngle();

        }
    }

    public void onUserOrientationChanged(Float orientation)
    {
        userAngle = orientation;
        reCalcualteAngle();
    }


    private void setAngle(int angle)
    {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.getLayoutParams();
        layoutParams.circleAngle = angle;
        this.setLayoutParams(layoutParams);

    }

    private void setRadius(int radius)
    {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.getLayoutParams();
        layoutParams.circleRadius = radius;
        this.setLayoutParams(layoutParams);
    }
    public void reCalcualteAngle()
    {
        if (userLocation != null && friendLocation != null) {
            var angle = Utilities.angleBetweenTwoLocations(userLocation, friendLocation);
            angle = angle + userAngle;
            setAngle((int) angle);
        }
    }

    public void reCalculateRadius()
    {

    }

    public void onFriendDataChange(Friend friend){

        setNameLabel(friend.label);
        this.friend = friend;
        friendLocation.setLatitude(friend.latitude);
        friendLocation.setLongitude(friend.longitude);
    }

    @Override
    protected void onDraw(final Canvas canvas){
        super.onDraw(canvas);
    }
}
