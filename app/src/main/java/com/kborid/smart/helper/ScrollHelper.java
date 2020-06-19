package com.kborid.smart.helper;

import android.view.View;
import android.widget.ScrollView;

public class ScrollHelper {

    public static void scroll2Bottom(final ScrollView scroll, final View inner) {
        if (scroll == null || inner == null) {
            return;
        }
        // 内层高度超过外层
        int offset = inner.getMeasuredHeight() - scroll.getMeasuredHeight();
        if (offset < 0) {
            offset = 0;
        }
        scroll.smoothScrollTo(0, offset);
    }

}
