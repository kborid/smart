package com.kborid.smart.imageloader;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.GridView;

import com.kborid.smart.R;
import com.kborid.smart.activity.BaseActivity;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class PictureActivity extends BaseActivity {

    private int mType = PictureAdapter.TYPE_UNIVERSAL;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_imageloader;
    }

    @Override
    protected void dealIntent() {
        super.dealIntent();
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            mType = bundle.getInt("imageType");
        }
    }

    @Override
    protected void initParams() {
        super.initParams();
        if (PictureAdapter.TYPE_UNIVERSAL == mType) {
            initImageLoaderConfig();
        }

        GridView mGridView = findViewById(R.id.gridview);
        PictureAdapter adapter = new PictureAdapter(this, mType);
        mGridView.setAdapter(adapter);
    }

    private void initImageLoaderConfig() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnFail(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
//                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .build();
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(options)
                .memoryCache(new LruMemoryCache(1024 * 1024 * 4))
                .writeDebugLogs()
                .threadPoolSize(5)
                .build();
        ImageLoader.getInstance().init(configuration);
    }
}