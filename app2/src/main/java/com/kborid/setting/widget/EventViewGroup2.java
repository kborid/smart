package com.kborid.setting.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class EventViewGroup2 extends RelativeLayout {

    private final static String TAG = EventViewGroup2.class.getSimpleName();

    public EventViewGroup2(Context context) {
        super(context);
    }

    public EventViewGroup2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EventViewGroup2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        System.out.println(TAG + ": 2 dispatchTouchEvent(), " + EventUtil.eventName(event));
        boolean ret = super.dispatchTouchEvent(event);
        System.out.println(TAG + ": 2 dispatchTouchEvent(), return:" + ret);
        return ret;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        System.out.println(TAG + ": 2 onInterceptTouchEvent(), " + EventUtil.eventName(event));
        boolean ret = super.onInterceptTouchEvent(event);
        System.out.println(TAG + ": 2 onInterceptTouchEvent(), return:" + ret);
        return ret;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println(TAG + ": 2 onTouchEvent(), " + EventUtil.eventName(event));
        boolean ret = super.onTouchEvent(event);
        System.out.println(TAG + ": 2 onTouchEvent(), return:" + ret);
        return ret;
    }
}
