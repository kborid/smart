package com.kborid.smart.ui.texture;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.TextureView;
import android.widget.LinearLayout;

import com.kborid.library.base.BaseActivity;
import com.kborid.smart.R;
import com.kborid.smart.di.DaggerCommonComponent;
import com.kborid.smart.ui.texture.presenter.TexturePresenter;
import com.kborid.smart.ui.texture.presenter.contract.TextureContract;
import com.thunisoft.ui.util.ScreenUtils;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;

public class TextureViewActivity extends BaseActivity<TexturePresenter> implements TextureContract.View {

    @BindView(R.id.textureView)
    TextureView textureView;

    private Camera camera;

    @Override
    protected void initInject() {
        DaggerCommonComponent.builder()
                .commonModule(getCommonModule("texture"))
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.act_texture;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void initEventAndData(@Nullable Bundle savedInstanceState) {
        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                camera = Camera.open();
                Camera.Parameters parameters = camera.getParameters();
                if (parameters.getSupportedFocusModes().contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                    parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                }

                Camera.Size previewSize = getSuitableSize(parameters.getSupportedPreviewSizes());
                mPreviewWidth = previewSize.width;
                mPreviewHeight = previewSize.height;
                parameters.setPreviewSize(mPreviewWidth, mPreviewHeight);
                Camera.Size pictureSize = getSuitableSize(parameters.getSupportedPictureSizes());
                parameters.setPictureSize(pictureSize.width, pictureSize.height);
                camera.setParameters(parameters);
                textureView.setLayoutParams(new LinearLayout.LayoutParams(mPreviewWidth, mPreviewHeight));
                try {
                    camera.setPreviewTexture(surface);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                camera.startPreview();
                textureView.setAlpha(1.0f);
                textureView.setRotation(90.0f);
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                camera.stopPreview();
                camera.release();
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {

            }
        });
    }

    private int mPreviewWidth = ScreenUtils.mScreenWidth;
    private int mPreviewHeight = ScreenUtils.mScreenHeight;
    private float mPreviewScale = (float) mPreviewHeight / mPreviewWidth;

    private Camera.Size getSuitableSize(List<Camera.Size> sizes) {
        int minDelta = Integer.MAX_VALUE; // 最小的差值，初始值应该设置大点保证之后的计算中会被重置
        int index = 0; // 最小的差值对应的索引坐标
        for (int i = 0; i < sizes.size(); i++) {
            Camera.Size previewSize = sizes.get(i);
            // 找到一个与设置的分辨率差值最小的相机支持的分辨率大小
            if (previewSize.width * mPreviewScale == previewSize.height) {
                int delta = Math.abs(mPreviewWidth - previewSize.width);
                if (delta == 0) {
                    return previewSize;
                }
                if (minDelta > delta) {
                    minDelta = delta;
                    index = i;
                }
            }
        }
        return sizes.get(index); // 默认返回与设置的分辨率最接近的预览尺寸
    }
}
