package com.kborid.smart.tt;

import android.annotation.SuppressLint;
import android.content.Context;
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
    public boolean requestRectangleOnScreen(Rect rectangle, boolean immediate) {
        ViewParent parent = getParent();
        if (parent instanceof ScrollView) {
            // Just request to the parent sco
            return parent.requestChildRectangleOnScreen(this, rectangle, immediate);
        }
        return super.requestRectangleOnScreen(rectangle, immediate);
    }
}
