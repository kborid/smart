package com.kborid.smart.imageloader;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.kborid.smart.R;

public class PicassoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageloader);

        GridView mGridView = findViewById(R.id.gridview);
        PictureAdapter adapter = new PictureAdapter(this, PictureAdapter.TYPE_PICASSO);
        mGridView.setAdapter(adapter);
    }
}
