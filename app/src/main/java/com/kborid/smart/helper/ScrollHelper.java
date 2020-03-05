package com.kborid.smart.helper;

import android.view.View;
import android.widget.ScrollView;

import com.kborid.smart.tt.SingletonTest;

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
        Thread.yield();
        String name = SingletonTest.Singleton5.instance.getTestName();
        SingletonTest.Singleton4.getInstance();
        scroll.smoothScrollTo(0, offset);
    }

}
