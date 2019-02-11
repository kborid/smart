package com.kborid.smart.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.View;

import com.kborid.smart.R;
import com.kborid.smart.fragment.FragmentSecond;

public class FragmentContainerActivity extends BaseActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_fragment_container;
    }

    @Override
    protected void initParams() {
        super.initParams();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.container, FragmentSecond.newInstance());
        ft.commitAllowingStateLoss();
    }

    public void onClick1(View view) {

    }

    public void onClick2(View view) {

    }

    public void onClick3(View view) {

    }

    public void onClick4(View view) {

    }

    public void onClick5(View view) {

    }
}
