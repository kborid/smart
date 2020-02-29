package com.kborid.smart.ui.image;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.widget.GridView;

import com.kborid.smart.R;
import com.kborid.smart.ui.image.adapter.PictureAdapter;
import com.thunisoft.common.base.BaseSimpleFragment;

import butterknife.BindView;

public class ImageFragment extends BaseSimpleFragment {

    @BindView(R.id.gridview)
    GridView mGridView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.act_imageloader;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        int type = PictureAdapter.TYPE_UNIVERSAL;
        Bundle params = getArguments();
        if (null != params) {
            type = params.getInt("type");
        }
        PictureAdapter adapter = new PictureAdapter(getContext(), type);
        mGridView.setAdapter(adapter);
    }

    public static Fragment newInstance(int type) {
        Fragment fragment = new ImageFragment();
        Bundle params = new Bundle();
        params.putInt("type", type);
        fragment.setArguments(params);
        return fragment;
    }
}
