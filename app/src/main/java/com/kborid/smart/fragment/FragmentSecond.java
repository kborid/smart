package com.kborid.smart.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kborid.library.common.UIHandler;
import com.kborid.smart.R;
import com.kborid.smart.activity.RecyclerViewTestActivity;

public class FragmentSecond extends Fragment implements View.OnClickListener {

    private static final String KEY = "Fragment";

    private ImageView iv_voice;
    private Button btn_click1, btn_click2;

    public static Fragment newInstance(String index) {
        FragmentSecond fragment = new FragmentSecond();
        Bundle bundle = new Bundle();
        bundle.putString(KEY, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        if (null != view) {
            TextView tv_test = (TextView) view.findViewById(R.id.test);
            tv_test.setText(getArguments().getString(KEY));
            iv_voice = (ImageView) view.findViewById(R.id.iv_voice);
            btn_click1 = (Button) view.findViewById(R.id.btn_click1);
            btn_click1.setOnClickListener(this);
            btn_click2 = (Button) view.findViewById(R.id.btn_click2);
            btn_click2.setOnClickListener(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        UIHandler.removeCallbacks(runnable);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_click1:
                UIHandler.removeCallbacks(runnable);
                UIHandler.post(runnable);
                break;
            case R.id.btn_click2:
                startActivity(new Intent(getActivity(), RecyclerViewTestActivity.class));
                break;
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            UIHandler.postDelayed(runnable, 900);
        }
    };
}
