package com.pbn.org.news.view.web;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

public class AdImageView1 extends ImageView {
    private int width;
    private int height;
    private int translateY;
    public AdImageView1(Context context) {
        super(context);
    }

    public AdImageView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AdImageView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setDy(int moveY, int total){
        if(getDrawable() == null){
            return;
        }

        translateY = moveY - height;
        float percent = (moveY * 1.0f/total) >= 1.0 ? 1.0f : (moveY * 1.0f/total);
        translateY = (int) ((getDrawable().getIntrinsicHeight() - height) * percent);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = (int) (9.0f * width / 16.0f);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if(getDrawable() == null){
            return;
        }

        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if(null == drawable){
            return;
        }
        int bottom = (int)(height * drawable.getIntrinsicWidth() * 1.0f/width);
        //标识图片的绘制区域
        drawable.setBounds(0, 0, getWidth(), drawable.getIntrinsicHeight());

        canvas.save();
        Log.e("AdImageView", "transY is  " + translateY);
        canvas.translate(0, -translateY);
        super.onDraw(canvas);
        canvas.restore();
    }
}
