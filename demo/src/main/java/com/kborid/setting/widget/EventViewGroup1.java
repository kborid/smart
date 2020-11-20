package com.kborid.setting.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class EventViewGroup1 extends RelativeLayout {

    private final static String TAG = EventViewGroup1.class.getSimpleName();

    public EventViewGroup1(Context context) {
        super(context);
    }

    public EventViewGroup1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EventViewGroup1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        System.out.println(TAG + ": 1 dispatchTouchEvent(), " + EventUtil.eventName(event));
        boolean ret = super.dispatchTouchEvent(event);
        System.out.println(TAG + ": 1 dispatchTouchEvent(), return:" + ret);
        return ret;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        System.out.println(TAG + ": 1 onInterceptTouchEvent(), " + EventUtil.eventName(event));
        boolean ret = true;
        System.out.println(TAG + ": 1 onInterceptTouchEvent(), return:" + ret);
        return ret;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println(TAG + ": 1 onTouchEvent(), " + EventUtil.eventName(event));
        boolean ret = true;
        System.out.println(TAG + ": 1 onTouchEvent(), return:" + ret);
        return ret;
    }
}
