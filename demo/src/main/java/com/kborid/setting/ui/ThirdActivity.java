package com.kborid.setting.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

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

    /**
     * 隐式Intent调用
     *
     * @param view
     */
    public void onIntent(View view) {
        Intent intent = new Intent("com.thunisoft.cocall.intent.action.ChooseContacts");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        Bundle bundle = new Bundle();
        bundle.putString("type", "type_invite_meeting");
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * scheme调用
     *
     * @param view
     */
    public void onScheme(View view) {
        String path = "cc-app://com.thunisoft.cocallmobile/chooseContacts?type=type_invite_meeting?mid=42675400&type=type_invite_meeting";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(path));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        startActivity(intent);
    }

    public void onMeetingScheme(View view) {
    }

    public void onAnr(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
