package edu.ucsd.cse110.cse_110_project_cse_110_team_9;


import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LifecycleOwner;

public class CustomView extends View {

    private int mWidth;
    private int mHeight;

    public CustomView(Context context) {
        super(context);
        init(context);
    }


    public CustomView(Context context, AttributeSet attrs){
        super(context,attrs);
        init(context);
    }
    public CustomView(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
        init(context);
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        Paint paint = new Paint();
        canvas.drawPaint(paint);
        paint.setColor(Color.RED);
        canvas.drawCircle(20,20,40,paint);
        canvas.save();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = View.MeasureSpec.getSize(widthMeasureSpec);
        mHeight = View.MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

    public void init(Context context)
    {

    }

    public void setSomething(int a, int b)
    {

        invalidate(); //Force this view to be redeawn
        requestLayout(); //important for something can't remeber
    }
}
