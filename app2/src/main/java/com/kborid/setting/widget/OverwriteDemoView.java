package com.kborid.setting.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class OverwriteDemoView extends View {

    static final int sBitmapWidth = 300;
    static final int sBitmapHeight = 300;
    int mOneLeft, mTwoLeft, mThreeLeft;

    int mResId;
    Bitmap mBitmap;
    Paint mPaint;

    public OverwriteDemoView(Context context) {
        this(context, null);
    }

    public OverwriteDemoView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OverwriteDemoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.YELLOW);
    }

    public void setImageResId(int resId) {
        this.mResId = resId;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(150, 150, sBitmapWidth, sBitmapHeight, mPaint);
//        canvas.drawBitmap(mBitmap, 300, 0, mPaint);
//        canvas.drawBitmap(mBitmap, mTwoLeft, 0, mPaint);
//        canvas.drawBitmap(mBitmap, mThreeLeft, 0, mPaint);
    }
}
