package com.kborid.smart.util;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Space;

import com.kborid.smart.R;
import com.thunisoft.ui.util.ScreenUtils;

public class ImmersiveStatusBarUtils {

    /**
     * 在{@link Activity#setContentView}之后调用
     *
     * @param activity  要实现的沉浸式状态栏的Activity
     * @param titleView 头部控件的ViewGroup, 若为null, 整个界面将和状态栏重叠
     */
    public static void initAfterSetContentView(Activity activity, View titleView) {
        if (null == activity || null == titleView) {
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            //透明状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

            Space space = titleView.findViewById(R.id.space);
            space.setVisibility(View.VISIBLE);
            //获取到状态栏的高度
            int statusHeight = ScreenUtils.mStateBarHeight;
            //动态的设置隐藏布局的高度
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) space.getLayoutParams();
            params.height = statusHeight;
            space.setLayoutParams(params);
        }
    }
}
