package com.kborid.setting.ui;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.kborid.setting.Fragment.TestAFragment;
import com.kborid.setting.R;
import com.thunisoft.common.base.BaseActivity;

public class FragmentActivity extends BaseActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void initDataAndEvent(@Nullable Bundle bundle) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.container, new TestAFragment());
        ft.commitAllowingStateLoss();
    }
}
