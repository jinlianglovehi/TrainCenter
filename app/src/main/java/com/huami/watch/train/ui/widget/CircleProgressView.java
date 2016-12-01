package com.huami.watch.train.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.huami.watch.train.R;


public class CircleProgressView extends View {

    private int circleRadius;

    private int mMaxProgress;

    private int mProgress = 0;

    private int mCircleLineStrokeWidth;

    private int colorCircleResource;

    private int colorArcResource;

    // 画圆所在的距形区域
    private final RectF mRectF;

    private final Paint mPaint;

    private String mTxtHint1;

    private String mTxtHint2;


    public CircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mRectF = new RectF();
        mPaint = new Paint();

        // 设置圆相关属性
        mMaxProgress = context.getResources().getInteger(R.dimen.quicksettings_chargingCircleMaxProgress);
        circleRadius = context.getResources().getDimensionPixelOffset(R.dimen.quicksettings_chargingCircleRadius);
        mCircleLineStrokeWidth = context.getResources()
                .getDimensionPixelOffset(R.dimen.quicksettings_chargingCircleLineStrokeWidth);

        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mCircleLineStrokeWidth);
        mPaint.setStyle(Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);// 圆弧两端为圆角

        colorCircleResource = context.getResources().getColor(R.color.quicksettings_chargingBgCircle_color);
        colorArcResource = context.getResources().getColor(R.color.quicksettings_chargingCircle_color);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.drawColor(Color.TRANSPARENT);
        // 设置画笔相关属性
        mPaint.setColor(colorCircleResource);
        // 绘制圆圈，进度条背景
        canvas.drawArc(mRectF, -90, 360, false, mPaint);
        mPaint.setColor(colorArcResource);
        canvas.drawArc(mRectF, -90, ((float) mProgress / mMaxProgress) * 360, false, mPaint);
        canvas.restore();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        float centerX = (right - left) / 2f;
        float centerY = (bottom - top) / 2f;
        mRectF.set(centerX - circleRadius, centerY - circleRadius, centerX + circleRadius, centerY + circleRadius);
    }

    public int getMaxProgress() {
        return mMaxProgress;
    }

    public void setMaxProgress(int maxProgress) {
        this.mMaxProgress = maxProgress;
    }

    public void setProgress(int progress) {
        this.mProgress = progress;
        this.invalidate();
    }

    public void setProgressNotInUiThread(int progress) {
        this.mProgress = progress;
        this.postInvalidate();
    }

    public String getmTxtHint1() {
        return mTxtHint1;
    }

    public void setmTxtHint1(String mTxtHint1) {
        this.mTxtHint1 = mTxtHint1;
    }

    public String getmTxtHint2() {
        return mTxtHint2;
    }

    public void setmTxtHint2(String mTxtHint2) {
        this.mTxtHint2 = mTxtHint2;
    }

}
