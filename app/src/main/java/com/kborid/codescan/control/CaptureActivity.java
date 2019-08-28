package com.kborid.codescan.control;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.facebook.stetho.common.LogUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.kborid.codescan.camera.CameraManager;
import com.kborid.codescan.view.ViewfinderView;
import com.kborid.library.base.BaseSimpleActivity;
import com.kborid.smart.R;
import com.kborid.smart.util.ToastUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import butterknife.BindView;

/**
 * 这个activity打开相机，在后台线程做常规的扫描；它绘制了一个结果view来帮助正确地显示条形码，在扫描的时候显示反馈信息，
 * 然后在扫描成功的时候覆盖扫描结果
 */
public final class CaptureActivity extends BaseSimpleActivity implements
        SurfaceHolder.Callback {

    private static final String TAG = CaptureActivity.class.getSimpleName();

    @BindView(R.id.preview_view)
    SurfaceView preview_view;
    @BindView(R.id.viewfinder_view)
    ViewfinderView viewfinder_view;

    // 相机控制
    private CameraManager cameraManager;
    private CaptureActivityHandler captureActivityHandler;
    private boolean hasSurface;
    private IntentSource source;
    private Collection<BarcodeFormat> decodeFormats;
    private Map<DecodeHintType, ?> decodeHints;
    private String characterSet;
    // 声音、震动控制
    private BeepManager beepManager;

    public ViewfinderView getViewfinderView() {
        return viewfinder_view;
    }

    public Handler getHandler() {
        return captureActivityHandler;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    public void drawViewfinder() {
        viewfinder_view.drawViewfinder();
    }

    /**
     * OnCreate中初始化一些辅助类，如InactivityTimer（休眠）、Beep（声音）以及AmbientLight（闪光灯）
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        // 保持Activity处于唤醒状态
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.ui_code_scan_act;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        hasSurface = false;
        beepManager = new BeepManager(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // CameraManager必须在这里初始化，而不是在onCreate()中。
        // 这是必须的，因为当我们第一次进入时需要显示帮助页，我们并不想打开Camera,测量屏幕大小
        // 当扫描框的尺寸不正确时会出现bug
        cameraManager = new CameraManager(getApplication());
        viewfinder_view.setCameraManager(cameraManager);
        captureActivityHandler = null;
        SurfaceHolder surfaceHolder = preview_view.getHolder();
        if (hasSurface) {
            // activity在paused时但不会stopped,因此surface仍旧存在；
            // surfaceCreated()不会调用，因此在这里初始化camera
            initCamera(surfaceHolder);
        } else {
            // 重置callback，等待surfaceCreated()来初始化camera
            surfaceHolder.addCallback(this);
        }

        beepManager.updatePrefs();

        source = IntentSource.NONE;
        decodeFormats = null;
        characterSet = null;
    }

    @Override
    protected void onPause() {
        if (captureActivityHandler != null) {
            captureActivityHandler.quitSynchronously();
        }
        beepManager.close();
        cameraManager.closeDriver();
        if (!hasSurface) {
            SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
            SurfaceHolder surfaceHolder = surfaceView.getHolder();
            surfaceHolder.removeCallback(this);
        }
        super.onPause();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    /**
     * 扫描成功，处理反馈信息
     *
     * @param rawResult
     * @param barcode
     * @param scaleFactor
     */
    public void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor) {
        boolean fromLiveScan = barcode != null;
        //这里处理解码完成后的结果，此处将参数回传到Activity处理
        if (fromLiveScan) {
            beepManager.playBeepSoundAndVibrate();
            if (rawResult != null) {
                String ret = rawResult.getText();
                LogUtil.d(TAG, "decord result = " + ret);
                ToastUtils.showToast(ret, Toast.LENGTH_LONG);
            }
        }

    }

    /**
     * 初始化Camera
     *
     * @param surfaceHolder
     */
    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            return;
        }
        try {
            // 打开Camera硬件设备
            cameraManager.openDriver(surfaceHolder);
            // 创建一个handler来打开预览，并抛出一个运行时异常
            if (captureActivityHandler == null) {
                captureActivityHandler = new CaptureActivityHandler(this, decodeFormats,
                        decodeHints, characterSet, cameraManager);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
            displayFrameworkBugMessageAndExit();
        } catch (RuntimeException e) {
            e.printStackTrace();
            displayFrameworkBugMessageAndExit();
        }
    }

    /**
     * 显示底层错误信息并退出应用
     */
    private void displayFrameworkBugMessageAndExit() {
        ToastUtils.showToast("底层错误", Toast.LENGTH_LONG);
    }
}
