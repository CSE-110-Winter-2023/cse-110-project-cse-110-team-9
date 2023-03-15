package edu.ucsd.cse110.cse_110_project_cse_110_team_9.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;


//TODO Create a custom view class that display the rings on the map
public class RingView extends View {





    private void init(Context context)
    {

    }

    public RingView(Context context) {
        super(context);
        init(context);
    }

    public RingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    public RingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public RingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }
}
