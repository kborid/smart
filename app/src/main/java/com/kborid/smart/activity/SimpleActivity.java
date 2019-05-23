package com.kborid.smart.activity;

import android.os.Bundle;

import com.kborid.library.base.BaseSimpleActivity;
import com.kborid.smart.R;
import com.kborid.smart.util.ImmersiveStatusBarUtils;
import com.kborid.smart.widget.MainTitleLayout;

/**
 * 基类提供一些共有属性操作
 *
 * @author kborid
 */
public abstract class SimpleActivity extends BaseSimpleActivity {

    protected MainTitleLayout titleView;

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        titleView = findViewById(R.id.title_layout);
        if (needStatusBarImmersive()) {
            ImmersiveStatusBarUtils.initAfterSetContentView(this, titleView);
        }
    }

    /**
     * 设置标题栏是否是沉浸式状态栏，默认false
     *
     * @return
     */
    protected boolean needStatusBarImmersive() {
        return false;
    }
}
