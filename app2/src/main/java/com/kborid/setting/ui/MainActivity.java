package com.kborid.setting.ui;//package com.kborid.setting.ui

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.kborid.setting.R;
import com.thunisoft.common.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initDataAndEvent(@Nullable Bundle bundle) {

    }

    public void startFlutter(View view) {
        Toast.makeText(this, "tt", Toast.LENGTH_SHORT).show();
//        startActivity(FlutterActivity.createDefaultIntent(this));
//        startActivity(FlutterActivity.withNewEngine().initialRoute("hybrid").build(this));
//        startActivity(FlutterActivity.withCachedEngine("flutter_engine").build(this))
        startActivity(new Intent(this, FragmentActivity.class));
    }
}