package com.kborid.setting.widget;

import android.view.MotionEvent;

public class EventUtil {
    public static String eventName(MotionEvent event) {
        String name = "unknow";
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                name = "down";
                break;
            case MotionEvent.ACTION_MOVE:
                name = "move";
                break;
            case MotionEvent.ACTION_HOVER_MOVE:
                name = "hover move";
                break;
            case MotionEvent.ACTION_UP:
                name = "up";
                break;
            default:
                break;
        }
        return name;
    }
}
