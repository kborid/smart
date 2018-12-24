package com.kborid.smart.test;

import android.view.animation.Interpolator;

import com.kborid.library.util.LogUtils;

public class CustomInterpolator implements Interpolator {
    @Override
    public float getInterpolation(float input) {
        float value = (float) Math.pow(input, 2);
        LogUtils.d("value = " + value);
        return value;
    }
}
