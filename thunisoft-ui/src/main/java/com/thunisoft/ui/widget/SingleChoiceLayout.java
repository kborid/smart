package com.thunisoft.ui.widget;


import android.content.Context;
import android.util.AttributeSet;

import com.thunisoft.ui.R;

public class SingleChoiceLayout extends CheckedRelativeLayout {

    public SingleChoiceLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setImageResouce(R.drawable.iv_single_unchoiced, R.drawable.iv_single_choiced);
    }
}
