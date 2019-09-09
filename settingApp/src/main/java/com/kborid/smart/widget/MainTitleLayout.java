package com.kborid.smart.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kborid.library.util.LogUtils;
import com.kborid.smart.R;

public class MainTitleLayout extends LinearLayout {

    private TextView tv_back, tv_title;

    public MainTitleLayout(Context context) {
        this(context, null);
    }

    public MainTitleLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainTitleLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LogUtils.d("onFinishInflate()");
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        initParams();
    }

    private void initParams() {
        LogUtils.d("initParams()");
        tv_title.setText("测试标题");
        tv_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onBack();
                }
            }
        });
    }

    public void setTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            return;
        }
        tv_title.setText(title);
    }

    public void setCanBack(boolean flag) {
        if (flag) {
            tv_back.setVisibility(VISIBLE);
        } else {
            tv_back.setVisibility(GONE);
        }
    }

    public interface OnTitleListener {
        void onBack();
    }

    private OnTitleListener listener = null;

    public void setOnTitleListener(OnTitleListener listener) {
        this.listener = listener;
    }
}
