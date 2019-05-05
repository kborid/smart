package com.kborid.smart.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kborid.smart.R;
import com.kborid.smart.util.ImmersiveStatusBarUtils;
import com.kborid.smart.widget.MainTitleLayout;

import butterknife.ButterKnife;

/**
 * 基类提供一些共有属性操作
 *
 * @author kborid
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected MainTitleLayout titleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        ButterKnife.bind(this);
        titleView = findViewById(R.id.title_layout);
        if (needStatusBarImmersive()) {
            ImmersiveStatusBarUtils.initAfterSetContentView(this, titleView);
        }
        initParams();
    }

    /**
     * 设置布局资源id
     * @return
     */
    protected abstract int getLayoutResId();

    /**
     * 设置标题栏是否是沉浸式状态栏，默认false
     * @return
     */
    protected boolean needStatusBarImmersive() {
        return false;
    }

    protected void initParams() {
        dealIntent();
    }

    protected void dealIntent() {
    }
}
