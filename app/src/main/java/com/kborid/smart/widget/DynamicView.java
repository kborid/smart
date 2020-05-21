package com.kborid.smart.widget;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class DynamicView extends SurfaceView implements SurfaceHolder.Callback {
    private HandlerThread drawThread;
    private Handler handler;

    public DynamicView(Context context) {
        this(context, null);
    }

    public DynamicView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DynamicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init() {
        setBackgroundTransparent();
    }

    /**
     * 设置surface背景透明
     */
    private void setBackgroundTransparent() {
        getHolder().setFormat(PixelFormat.TRANSPARENT);
        setZOrderOnTop(true);
    }

    private Runnable drawRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                System.out.println("DrawThread:" + Thread.currentThread().getName() + ", drawing~~~");
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.post(this);
        }
    };

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (null == drawThread) {
            drawThread = new HandlerThread("draw-thread");
            drawThread.start();
            handler = new Handler(drawThread.getLooper());
            handler.post(drawRunnable);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (null != handler) {
            handler.removeCallbacksAndMessages(null);
        }
        if (null != drawThread) {
            drawThread.quit();
        }
    }
}
