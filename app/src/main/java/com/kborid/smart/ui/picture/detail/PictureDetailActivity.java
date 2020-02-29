package com.kborid.smart.ui.picture.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.kborid.smart.R;
import com.kborid.smart.contant.AppConstant;
import com.thunisoft.common.base.BaseSimpleActivity;

import butterknife.BindView;
import uk.co.senab.photoview.PhotoView;

public class PictureDetailActivity extends BaseSimpleActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.photoview)
    PhotoView photoView;

    @Override
    protected int getLayoutResId() {
        return R.layout.act_photo_detail;
    }

    @Override
    protected void initEventAndData(Bundle bundle) {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String url = getIntent().getStringExtra(AppConstant.PHOTO_DETAIL);
        Glide.with(this).load(url)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .fitCenter())
                .into(photoView);

        photoView.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                finish();
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (photoView.getScale() == 1) {
                    photoView.setScale(2, true);
                } else {
                    photoView.setScale(1, true);
                }
                return true;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                return false;
            }
        });

        photoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
    }

    public static void startPictureDetailActivity(Context context, String url) {
        Intent intent = new Intent(context, PictureDetailActivity.class);
        intent.putExtra(AppConstant.PHOTO_DETAIL, url);
        context.startActivity(intent);
    }
}
