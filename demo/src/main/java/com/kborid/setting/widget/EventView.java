package com.kborid.setting.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class EventView extends View {

    private final static String TAG = EventView.class.getSimpleName();

    public EventView(Context context) {
        this(context, null);
    }

    public EventView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EventView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
//        setOnTouchListener(new OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                boolean ret = true;
//                System.out.println(TAG + ": onTouch:" + EventUtil.eventName(event) + ", return:" + ret);
//                return ret;
//            }
//        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        System.out.println(TAG + ": dispatchTouchEvent(), " + EventUtil.eventName(event));
        boolean ret = super.dispatchTouchEvent(event);
        System.out.println(TAG + ": dispatchTouchEvent(), return:" + ret);
        return ret;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println(TAG + ": onTouchEvent(), " + EventUtil.eventName(event));
        boolean ret = super.onTouchEvent(event);
        System.out.println(TAG + ": onTouchEvent(), return:" + ret);
        return ret;
    }
}
