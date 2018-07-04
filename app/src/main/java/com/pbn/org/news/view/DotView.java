package com.pbn.org.news.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class DotView extends View implements Runnable{
    private int mDotNum = 3;
    private int mRadius = 10;
    private Paint mDotPaint;

    private int leftX;
    private int midX;
    private int rightX;

    private float currentProgress;
    private Handler handler;
    private int currentNum = 2;

    public DotView(Context context) {
        super(context);
        init();
    }

    public DotView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DotView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mDotPaint = new Paint();
        mDotPaint.setColor(Color.RED);
        mDotPaint.setStyle(Paint.Style.FILL);
        mDotPaint.setDither(true);
        handler = new Handler(Looper.getMainLooper());
    }

    public void setProgress(float progress){
        currentProgress = progress;
        if(currentProgress <=1){
            invalidate();
        }else {
            currentProgress = 1;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        midX = getWidth()/2;
        mRadius = getHeight()/3;

        leftX = (int) (midX - (currentProgress * mRadius * 2.5f ));
        rightX = (int) (midX + (currentProgress * mRadius * 2.5f ));
        int index = currentNum % mDotNum + 1;
        if(index == 1){
            canvas.drawCircle(leftX, getHeight()/2, mRadius, mDotPaint);
        }else if(index == 2){
            canvas.drawCircle(leftX, getHeight()/2, mRadius, mDotPaint);
            canvas.drawCircle(midX, getHeight()/2, mRadius, mDotPaint);
        }else{
            canvas.drawCircle(leftX, getHeight()/2, mRadius, mDotPaint);
            canvas.drawCircle(midX, getHeight()/2, mRadius, mDotPaint);
            canvas.drawCircle(rightX, getHeight()/2, mRadius, mDotPaint);
        }

    }

    public void startLoading(){
        handler.post(this);
    }

    public void stopLoading(){
        handler.removeCallbacks(this);
        currentNum = 2;
    }

    @Override
    public void run() {
        currentNum = currentNum + 1;
        invalidate();
        handler.postDelayed(this, 300);
    }
}
