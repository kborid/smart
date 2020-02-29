package com.kborid.smart.ui.user.tab;

import android.os.Bundle;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.kborid.smart.R;
import com.thunisoft.common.base.BaseSimpleFragment;

import butterknife.BindView;

public class UserTabFragment extends BaseSimpleFragment {

//    @BindView(R.id.toolbar)
//    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView titleTV;

    @Override
    protected int getLayoutResId() {
        return R.layout.frag_tab_user;
    }

    public static Fragment newInstance(String type) {
        Fragment fragment = new UserTabFragment();
        Bundle args = new Bundle();
        args.putString("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
//        toolbar.setTitle(getArguments().getString("type"));
        titleTV.setText(getArguments().getString("type"));
    }
}
