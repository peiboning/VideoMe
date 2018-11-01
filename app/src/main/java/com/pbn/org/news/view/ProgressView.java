package com.pbn.org.news.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @author peiboning
 * @DATE 2018/09/11
 */
public class ProgressView extends View{

    private Paint mPaint;
    private Path mPath;
    private PathMeasure mPathMeasure;
    private Path mDest;
    private float mPercent;
    private ValueAnimator valueAnimator;

    private boolean isStop;

    public ProgressView(Context context) {
        super(context);
        init();
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void stop(){
        isStop = true;
        valueAnimator.cancel();
    }

    public void start(){
        valueAnimator.cancel();
        isStop = false;
        valueAnimator.start();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLUE);

        mPath = new Path();
        mDest = new Path();

        mPathMeasure = new PathMeasure();

        valueAnimator = ValueAnimator.ofFloat(0, 1.0f);
        valueAnimator.setDuration(1000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPercent = (float) animation.getAnimatedValue();
                if(!isStop){
                    invalidate();
                }
            }
        });

//        valueAnimator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int size = Math.min(width, height) + 30;
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPath.addCircle(getWidth()/2, getHeight()/2, getWidth()/2 - 25
                , Path.Direction.CW);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mDest.reset();
        mDest.lineTo(0, 0);
        mPathMeasure.setPath(mPath, true);
        float len = mPathMeasure.getLength();
        float stop = len * mPercent;
        float startD = stop - stop * (1 - mPercent*mPercent);
        Log.e("onDraw", startD + "    " + mPercent);
        mPathMeasure.getSegment(startD, stop, mDest, true);
        canvas.drawPath(mDest, mPaint);
    }
}
