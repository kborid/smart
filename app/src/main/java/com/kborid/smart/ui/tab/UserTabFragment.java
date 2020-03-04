package com.kborid.smart.ui.tab;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.kborid.smart.R;
import com.thunisoft.common.base.BaseSimpleFragment;

import butterknife.BindView;

public class UserTabFragment extends BaseSimpleFragment {

    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbarLayout)
    CollapsingToolbarLayout toolbarLayout;

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
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                if (Math.abs(i) >= appBarLayout.getTotalScrollRange() / 3 * 2) {
                    toolbarLayout.setTitle(getArguments().getString("type"));
                } else {
                    toolbarLayout.setTitle("");
                }
            }
        });
        toolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(getContext(), R.color.text_black));
    }
}
