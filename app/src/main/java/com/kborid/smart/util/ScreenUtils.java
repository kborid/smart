package com.kborid.smart.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.kborid.smart.PRJApplication;

import java.lang.reflect.Field;

public class ScreenUtils {

    public static int mScreenWidth;
    public static int mScreenHeight;
    public static float mDensity;

    public static void init() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) PRJApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
        if (null != wm) {
            wm.getDefaultDisplay().getMetrics(dm);
            mScreenWidth = dm.widthPixels;
            mScreenHeight = dm.heightPixels;
            mDensity = dm.density;
        }
    }

    public static int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return PRJApplication.getInstance().getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
