package com.pbn.org.news.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


public class VideoLoadingView extends View {

    private Path mBottomPath;
    private Path mTopPath;
    private Paint bottomPaint;
    private Paint topPaint;

    private int width;
    private int height;

    public VideoLoadingView(Context context) {
        super(context);
        init();
    }

    public VideoLoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VideoLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        bottomPaint = new Paint();
        bottomPaint.setStrokeWidth(5);
        bottomPaint.setColor(Color.GRAY);
        bottomPaint.setAntiAlias(true);
        bottomPaint.setStyle(Paint.Style.STROKE);

        topPaint = new Paint();
        topPaint.setStrokeWidth(3);
        topPaint.setColor(Color.RED);
        topPaint.setAntiAlias(true);
        topPaint.setStyle(Paint.Style.STROKE);

        mBottomPath = new Path();
        mTopPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = Math.min(width, height);
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
        width = w;
        setPath();
    }

    private void setPath() {
        int top = (int) (width/2 * Math.sin(Math.PI/3));
        int startX = width/2 - width/4;
        int startY = height/2-top;

        mBottomPath.moveTo(startX, startY);
        mBottomPath.lineTo(startX, height/2 + top);

        mBottomPath.lineTo(width, height/2);
        mBottomPath.close();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mBottomPath, bottomPaint);
    }
}
