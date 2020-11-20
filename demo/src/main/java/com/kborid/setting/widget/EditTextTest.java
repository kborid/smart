package com.kborid.setting.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.ScrollView;

@SuppressLint("AppCompatCustomView")
public class EditTextTest extends EditText {
    public EditTextTest(Context context) {
        super(context);
    }

    public EditTextTest(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditTextTest(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int measureWidth(int widthSpec) {
        int width;
        int specMode = MeasureSpec.getMode(widthSpec);
        int specSize = MeasureSpec.getSize(widthSpec);
        switch (specMode) {
            case MeasureSpec.EXACTLY:
                width = specSize * 2;
                break;
            case MeasureSpec.AT_MOST:
                width = 100;
                break;
            case MeasureSpec.UNSPECIFIED:
            default:
                width = 200;
                break;
        }
        return width;
    }

    private int measureHeight(int heightSpec) {
        int height;
        int specMode = MeasureSpec.getMode(heightSpec);
        int specSize = MeasureSpec.getSize(heightSpec);
        switch (specMode) {
            case MeasureSpec.EXACTLY:
                height = specSize * 2;
                break;
            case MeasureSpec.AT_MOST:
                height = 50;
                break;
            case MeasureSpec.UNSPECIFIED:
            default:
                height = 80;
                break;
        }
        return height;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public boolean requestRectangleOnScreen(Rect rectangle, boolean immediate) {
        ViewParent parent = getParent();
        if (parent instanceof ScrollView) {
            // Just request to the parent sco
            return parent.requestChildRectangleOnScreen(this, rectangle, immediate);
        }
        return super.requestRectangleOnScreen(rectangle, immediate);
    }
}
