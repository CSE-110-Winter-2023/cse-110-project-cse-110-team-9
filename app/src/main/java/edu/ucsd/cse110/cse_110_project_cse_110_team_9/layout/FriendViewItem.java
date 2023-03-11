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
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import edu.ucsd.cse110.cse_110_project_cse_110_team_9.R;
import edu.ucsd.cse110.cse_110_project_cse_110_team_9.database.Friend;

public class FriendViewItem extends LinearLayout {

    LinearLayout layout;
    TextView nameLabel;
    TextView friendIcon;
    ImageView friendImg;
    Context mContext;

    public FriendViewItem(Context context)
    {

        super(context);

        String service = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(service);

        layout = (LinearLayout) li.inflate(R.layout.friend_view_item, this, true);

        nameLabel = (TextView) layout.findViewById(R.id.friendName);

        friendIcon=  (TextView) layout.findViewById(R.id.friendIcon);


        // rightTextView = (TextView) layout.findViewById(R.id.right_text);

        nameLabel.setText("jerry");
        //friendIcon.setText(new String(Character.toChars(0x1F4A9)));

        friendIcon.setText("Test");
        // rightTextView.setText(rightText)

    }

    public FriendViewItem(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FriendViewItem);

        String friendName = a.getString(R.styleable.FriendViewItem_FriendName);
        //String rightText = a.getString(R.styleable.DoubleText_rightText);

        String friendIconCode = a.getString(R.styleable.FriendViewItem_FriendIcon);

        friendName = friendName == null ? "" : friendName;
        friendIconCode = friendIconCode == null ? "" : friendIconCode;

        String service = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(service);

        layout = (LinearLayout) li.inflate(R.layout.friend_view_item, this, true);

        nameLabel = (TextView) layout.findViewById(R.id.friendName);

        friendIcon=  (TextView) layout.findViewById(R.id.friendIcon);

        nameLabel.setTextColor(Color.RED);

       // rightTextView = (TextView) layout.findViewById(R.id.right_text);

        nameLabel.setText(friendName);
       // friendIcon.setText(new String(Character.toChars(0x1F346)));
        friendIcon.setText("test");

       // rightTextView.setText(rightText)
        a.recycle();
    }


    public FriendViewItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }


    public void setNameLabel(String name) {
       nameLabel.setText(name);


    }

    public void setData(LiveData<Friend> friend, LifecycleOwner owner)
    {
        friend.observe(owner,this::onFriendDataChange);
       // this.
    }



    public void setAngle(int angle)
    {

        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.getLayoutParams();

        layoutParams.circleAngle = angle;
        this.setLayoutParams(layoutParams);

    }

    public void onFriendDataChange(Friend friend){

        setNameLabel(friend.label);
    }

    @Override
    protected void onDraw(final Canvas canvas){
        super.onDraw(canvas);
    }
}
