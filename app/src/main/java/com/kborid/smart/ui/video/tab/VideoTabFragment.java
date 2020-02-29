package com.kborid.smart.ui.video.tab;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.kborid.smart.R;
import com.thunisoft.common.base.BaseSimpleFragment;

import butterknife.BindView;

public class VideoTabFragment extends BaseSimpleFragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    @Override
    protected int getLayoutResId() {
        return R.layout.frag_tab_video;
    }

    public static Fragment newInstance(String type) {
        Fragment fragment = new VideoTabFragment();
        Bundle args = new Bundle();
        args.putString("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        toolbar.setTitle(getArguments().getString("type"));
    }
}
