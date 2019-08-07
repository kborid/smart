package com.kborid.library.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.lang.reflect.Field;

public class ScreenUtils {

    public static int mScreenWidth;
    public static int mScreenHeight;
    public static float mDensity;
    public static int mScreenStatusBarHeight;

    public static void init(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (null != wm) {
            wm.getDefaultDisplay().getMetrics(dm);
            mScreenWidth = dm.widthPixels;
            mScreenHeight = dm.heightPixels;
            mDensity = dm.density;
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object obj = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = Integer.parseInt(field.get(obj).toString());
                mScreenStatusBarHeight = context.getResources().getDimensionPixelSize(x);
            } catch (Exception e) {
                e.printStackTrace();
                mScreenStatusBarHeight = 0;
            }
        }
    }
}
