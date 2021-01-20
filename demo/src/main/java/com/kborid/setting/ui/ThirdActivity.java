package com.kborid.setting.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.AppBarLayout;
import com.kborid.setting.R;
import com.kborid.setting.widget.ToolbarArcBackground;
import com.thunisoft.common.base.BaseSimpleActivity;

public class ThirdActivity extends BaseSimpleActivity {

    private ToolbarArcBackground mToolbarArcBackground;

    @Override
    protected int getLayoutResId() {
        return R.layout.act_third;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();


        AppBarLayout appBarLayout = findViewById(R.id.appbar);
        mToolbarArcBackground = findViewById(R.id.toolbarArcBackground);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                float scale = (float) Math.abs(verticalOffset) / scrollRange;
                mToolbarArcBackground.setScale(1 - scale);
            }
        });
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                mToolbarArcBackground.startAnimate();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
