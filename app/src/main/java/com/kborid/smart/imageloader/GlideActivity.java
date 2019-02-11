package com.kborid.smart.imageloader;

import android.widget.GridView;

import com.kborid.smart.R;
import com.kborid.smart.activity.BaseActivity;

public class GlideActivity extends BaseActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_imageloader;
    }

    @Override
    protected void initParams() {
        super.initParams();
        GridView mGridView = findViewById(R.id.gridview);
        PictureAdapter adapter = new PictureAdapter(this, PictureAdapter.TYPE_GLIDE);
        mGridView.setAdapter(adapter);
    }
}
