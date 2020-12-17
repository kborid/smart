package com.kborid.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.kborid.library.R;
import com.orhanobut.logger.Logger;
import com.thunisoft.ui.util.ScreenUtils;

public class FMTransmitterSeekBar extends View {

    private static final String TAG = FMTransmitterSeekBar.class.getSimpleName();

    private static final int SCALE_MIN_DEFAULT = 0; // 默认最小进度
    private static final int SCALE_MAX_DEFAULT = 100; // 默认最大进度
    private static final int SCALE_SPACE_COUNT = 60; // 默认刻度数量
    private static final int BACKGROUND_LINE_COUNT = 5; // 默认背景线数量

    private int mWidth, mHeight;
    // 控件左右空白和
    private int mWidthSpace;
    // 刻度间距
    private float mScaleSpace;
    // 背景线Y轴坐标
    private int mBgLineOneY, mBgLineTwoY, mBgLineThreeY, mBgLineFourY, mBgLineFiveY;
    // 背景线间距
    private int mBgLineSpace;
    // 最小/最大刻度
    private int mMinProgress, mMaxProgress;
    // 进度条长度
    private int mProgressLength;
    // 当前进度
    private int mCurrProgress;
    // thumb宽度
    private int mThumbWidth;

    private Paint mPaint;
    private PathMeasure mPathMeasure;
    private float mPathLength;
    private Bitmap mColorBezierBitmap, mGaryBezierBitmap;

    private Drawable mThumbDrawable;
    private int mSelectedColor, mSelectedColor1, mUnSelectedColor;
    private float mSelectedAlpha, mUnSelectedAlpha;

    public FMTransmitterSeekBar(Context context) {
        this(context, null);
    }

    public FMTransmitterSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FMTransmitterSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FMTransmitterSeekBar);
        mThumbDrawable = getResources().getDrawable(ta.getResourceId(R.styleable.FMTransmitterSeekBar_thumb, R.drawable.shape_sb_thumb));
        mSelectedColor = ta.getColor(R.styleable.FMTransmitterSeekBar_selectedColor, Color.WHITE);
        mSelectedColor1 = ta.getColor(R.styleable.FMTransmitterSeekBar_selectedColor1, mSelectedColor);
        mUnSelectedColor = ta.getColor(R.styleable.FMTransmitterSeekBar_unSelectedColor, Color.TRANSPARENT);
        mSelectedAlpha = ta.getFloat(R.styleable.FMTransmitterSeekBar_selectedAlpha, 1.0f);
        mUnSelectedAlpha = ta.getFloat(R.styleable.FMTransmitterSeekBar_unSelectedAlpha, 0.0f);
        ta.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(1);

        mMinProgress = SCALE_MIN_DEFAULT;
        mMaxProgress = SCALE_MAX_DEFAULT;
        mProgressLength = mMaxProgress - mMinProgress;
    }

    //判断坐标是否在thumb可touch范围内
    private boolean touchInThumbRange(int x, int y) {
        int currentX = mCurrProgress * mWidth / mProgressLength;
        int touchSlop = ScreenUtils.dp2px(40);
        int offset = Math.max(touchSlop, mThumbWidth / 2);
        return (Math.abs(x - currentX) <= offset && Math.abs(y - (mBgLineOneY - mThumbWidth / 2)) <= offset);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX() - (float) mWidthSpace / 2;
        float y = event.getY();
        int progress = (int) (x / mWidth * mProgressLength);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                if (!touchInThumbRange((int) x, (int) y)) {
                    Logger.t(TAG).d("touch out of range");
                    return false;
                }

                if (null != onSeekBarChangeListener) {
                    onSeekBarChangeListener.onStartTrackingTouch(this);
                }
                return true;
            }
            case MotionEvent.ACTION_MOVE: {
                if (progress > mProgressLength) {
                    progress = mProgressLength;
                } else if (progress < 0) {
                    progress = 0;
                }


                if (null != onSeekBarChangeListener) {
                    onSeekBarChangeListener.onProgressChanged(this, progress);
                }
                return true;
            }
            case MotionEvent.ACTION_UP:
                if (null != onSeekBarChangeListener) {
                    onSeekBarChangeListener.onStopTrackingTouch(this);
                }
                return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidthSpace = ScreenUtils.dp2px(100);
        mWidth = getWidth() - mWidthSpace;
        mHeight = getHeight();
        mBgLineOneY = mHeight / 3;
        mBgLineSpace = (mHeight - mBgLineOneY) / BACKGROUND_LINE_COUNT;
        mBgLineTwoY = mBgLineOneY + mBgLineSpace;
        mBgLineThreeY = mBgLineTwoY + mBgLineSpace;
        mBgLineFourY = mBgLineThreeY + mBgLineSpace;
        mBgLineFiveY = mBgLineFourY + mBgLineSpace;
        mScaleSpace = (float) mWidth / SCALE_SPACE_COUNT;
        mThumbWidth = mThumbDrawable.getIntrinsicWidth();

        mColorBezierBitmap = Bitmap.createBitmap(getWidth(), mHeight, Bitmap.Config.ARGB_4444);
        mGaryBezierBitmap = Bitmap.createBitmap(getWidth(), mHeight, Bitmap.Config.ARGB_4444);

        initPath();
        initColorBezierCurve();
        initGaryBezierCurve();
    }

    // 初始化bessel曲线
    private void initPath() {
        Path path = new Path();
        //每一段曲线的宽度
        float step = (float) mWidth / 8;
        // line 1
        int startX = mWidthSpace / 2;
        path.moveTo(startX, mBgLineFourY);
        path.quadTo(startX + step, mBgLineThreeY - mBgLineSpace / 2, startX + step * 2, mBgLineThreeY + mBgLineSpace / 4);
        // line 2
        startX += step * 2;
        path.quadTo(startX + step / 2, mBgLineThreeY + mBgLineSpace / 2, startX + step, mBgLineTwoY);
        // line 3
        startX += step;
        path.quadTo(startX + step / 2, mBgLineOneY - mBgLineSpace / 2, startX + step, mBgLineThreeY - mBgLineSpace / 2);
        // line 4
        startX += step;
        path.quadTo(startX + step / 2, mBgLineFourY + mBgLineSpace / 2, startX + step, mBgLineFourY - mBgLineSpace / 4);
        // line 5
        startX += step;
        path.quadTo(startX + step / 2, mBgLineThreeY, startX + step, mBgLineThreeY + mBgLineSpace / 2);
        // line 6
        startX += step;
        path.quadTo(startX + step / 2, mBgLineFourY, startX + step, mBgLineTwoY + mBgLineSpace / 4);
        // line 7
        startX += step;
        path.quadTo(startX + step / 2, mBgLineOneY - mBgLineSpace / 2, mWidth + mWidthSpace / 2, mBgLineFourY);

        mPathMeasure = new PathMeasure();
        mPathMeasure.setPath(path, false);
        mPathLength = mPathMeasure.getLength();
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = mWidth + getPaddingLeft() + getPaddingRight();
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }

        return result;
    }

    private int measureHeight(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = mHeight + getPaddingTop() + getPaddingBottom();
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBezierBitmap(canvas);
        drawBackgroundLine(canvas);
        drawScaleLine(canvas);
        drawThumb(canvas);
        drawCurrentText(canvas);
    }

    /**
     * 绘制彩色bessel曲线到bitmap
     */
    private void initColorBezierCurve() {
        Path realDrawPath = new Path();
        mPathMeasure.getSegment(0, mPathLength, realDrawPath, true);

        Canvas innerCanvas = new Canvas(mColorBezierBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        LinearGradient lgLine = new LinearGradient(0, 0, mWidth + mWidthSpace / 2, mHeight,
                mSelectedColor, mSelectedColor1, Shader.TileMode.MIRROR);
        paint.setShader(lgLine);
        innerCanvas.drawPath(realDrawPath, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setAlpha(getAlpha(mSelectedAlpha));
        realDrawPath.setFillType(Path.FillType.EVEN_ODD);
        realDrawPath.addRect(mWidthSpace / 2, mBgLineFourY, mWidthSpace / 2 + mWidth, mBgLineFiveY, Path.Direction.CW);
        innerCanvas.drawPath(realDrawPath, paint);
    }

    /**
     * 绘制灰度bessel曲线到bitmap
     */
    private void initGaryBezierCurve() {
        Path realDrawPath = new Path();
        mPathMeasure.getSegment(0, mPathLength, realDrawPath, true);

        Canvas innerCanvas = new Canvas(mGaryBezierBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setAlpha(getAlpha(mUnSelectedAlpha));
        LinearGradient lgRect = new LinearGradient(0, 0, mWidth + mWidthSpace / 2, mHeight,
                mUnSelectedColor, mUnSelectedColor, Shader.TileMode.MIRROR);
        paint.setShader(lgRect);
        realDrawPath.setFillType(Path.FillType.EVEN_ODD);
        realDrawPath.addRect(mWidthSpace / 2, mBgLineFourY, mWidthSpace / 2 + mWidth, mBgLineFiveY, Path.Direction.CW);
        innerCanvas.drawPath(realDrawPath, paint);
    }

    /**
     * 绘制bessel曲线bitmap
     *
     * @param canvas
     */
    private void drawBezierBitmap(Canvas canvas) {
        // draw left bitmap
        Rect leftSrc = new Rect(0, 0, mCurrProgress * mWidth / mProgressLength + mWidthSpace / 2, mHeight);
        Rect leftDst = new Rect(0, 0, mCurrProgress * mWidth / mProgressLength + mWidthSpace / 2, mHeight);
        canvas.drawBitmap(mColorBezierBitmap, leftSrc, leftDst, mPaint);

        // draw right bitmap
        Rect rightSrc = new Rect(mCurrProgress * mWidth / mProgressLength + mWidthSpace / 2, 0, mWidth + mWidthSpace / 2, mHeight);
        Rect rightDst = new Rect(mCurrProgress * mWidth / mProgressLength + mWidthSpace / 2, 0, mWidth + mWidthSpace / 2, mHeight);
        canvas.drawBitmap(mGaryBezierBitmap, rightSrc, rightDst, mPaint);
    }

    /**
     * 绘制背景线
     *
     * @param canvas
     */
    private void drawBackgroundLine(Canvas canvas) {
        mPaint.setColor(Color.WHITE);
        mPaint.setAlpha(getAlpha(0.3f));
        mPaint.setStrokeWidth(1);
        for (int i = 0; i < BACKGROUND_LINE_COUNT; i++) {
            int lineY = mBgLineOneY + i * mBgLineSpace;
            canvas.drawLine(mWidthSpace / 2, lineY, mWidth + mWidthSpace / 2, lineY, mPaint);
        }
    }

    /**
     * 绘制刻度线
     *
     * @param canvas
     */
    private void drawScaleLine(Canvas canvas) {
        mPaint.setColor(Color.WHITE);
        mPaint.setAlpha(getAlpha(0.3f));
        mPaint.setStrokeWidth(1);

        int startScaleY = mBgLineFiveY + ScreenUtils.dp2px(10);
        for (int i = 0; i <= SCALE_SPACE_COUNT; i++) {
            float lineX = i * mScaleSpace + mWidthSpace / 2;
            if (i == 0 || i == SCALE_SPACE_COUNT || i == SCALE_SPACE_COUNT / 2) {
                mPaint.setAlpha(getAlpha(1f));
                canvas.drawLine(lineX, startScaleY, lineX, startScaleY + ScreenUtils.dp2px(19), mPaint);
                String text = getFreqFormat((int) (((float) mProgressLength / SCALE_SPACE_COUNT) * i) + mMinProgress);
                mPaint.setTextSize(16);
                canvas.drawText(text, lineX - mPaint.measureText(text) / 2, startScaleY + ScreenUtils.dp2px(40), mPaint);
            } else {
                mPaint.setAlpha(getAlpha(0.3f));
                canvas.drawLine(lineX, startScaleY, lineX, startScaleY + ScreenUtils.dp2px(12), mPaint);
            }
        }
    }

    /**
     * 绘制Thumb
     *
     * @param canvas
     */
    private void drawThumb(Canvas canvas) {
        mPaint.setColor(Color.WHITE);
        mPaint.setAlpha(getAlpha(1f));
        mPaint.setStrokeWidth(2);

        int progress = (int) ((float) mCurrProgress / mProgressLength * mWidth);
        canvas.drawLine(progress + mWidthSpace / 2, ScreenUtils.dp2px(60), progress + mWidthSpace / 2, mBgLineOneY - mThumbWidth - ScreenUtils.dp2px(5), mPaint);
        canvas.drawLine(progress + mWidthSpace / 2, mBgLineOneY + ScreenUtils.dp2px(5), progress + mWidthSpace / 2, mBgLineFiveY, mPaint);

        mThumbDrawable.setBounds(new Rect(progress + mWidthSpace / 2 - mThumbWidth / 2, mBgLineOneY - mThumbWidth, progress + mWidthSpace / 2 + mThumbWidth / 2, mBgLineOneY));
        mThumbDrawable.draw(canvas);
    }

    /**
     * 绘制Thumb上方文字
     *
     * @param canvas
     */
    private void drawCurrentText(Canvas canvas) {
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setAlpha(getAlpha(1f));
        mPaint.setStrokeWidth(2);
        mPaint.setTextSize(40);
        int progress = (int) ((float) mCurrProgress / mProgressLength * mWidth);
        String text = getFreqFormat(mCurrProgress + mMinProgress);
        canvas.drawText(text, progress + mWidthSpace / 2 - mPaint.measureText(text) / 2, ScreenUtils.dp2px(50), mPaint);
    }

    public void setMax(int max) {
        mMaxProgress = max;
        mProgressLength = mMaxProgress - mMinProgress;
        invalidate();
    }

    public void setMin(int min) {
        mMinProgress = min;
        mProgressLength = mMaxProgress - mMinProgress;
        invalidate();
    }

    public void setProgress(int progress) {
        this.mCurrProgress = progress;
        invalidate();
    }

    private int getAlpha(float alpha) {
        return (int) (alpha * 255);
    }

    public String getFreqFormat(int freq) {
        return String.format("%.1f", freq / 10f);
    }

    private OnSeekBarChangeListener onSeekBarChangeListener = null;

    public void setOnSeekBarChangeListener(OnSeekBarChangeListener listener) {
        this.onSeekBarChangeListener = listener;
    }

    public void destroy() {
        if (null != mColorBezierBitmap) {
            mColorBezierBitmap.recycle();
            mColorBezierBitmap = null;
        }

        if (null != mGaryBezierBitmap) {
            mGaryBezierBitmap.recycle();
            mGaryBezierBitmap = null;
        }
    }

    public interface OnSeekBarChangeListener {
        void onProgressChanged(View view, int progress);

        void onStartTrackingTouch(View view);

        void onStopTrackingTouch(View view);
    }
}
