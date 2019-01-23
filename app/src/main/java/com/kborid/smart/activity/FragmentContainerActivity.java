package com.kborid.smart.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.kborid.smart.R;
import com.kborid.smart.fragment.FragmentSecond;

public class FragmentContainerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.container, FragmentSecond.newInstance("Smartisan"));
        ft.commitAllowingStateLoss();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
