package com.pbn.org.news.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

public class AdImageView extends ImageView {
    private int width;
    private int height;
    private int translateY;
    private int totalHeight;
    public AdImageView(Context context) {
        super(context);
    }

    public AdImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AdImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setDy(int moveY, int total){
        if(getDrawable() == null){
            return;
        }
        totalHeight = total;
        translateY = moveY - height;
        float percent = (1-(moveY * 1.0f/total)) >= 1.0 ? 1.0f :(1- (moveY * 1.0f/total));
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
        int h = (int) (getWidth() * 1.0f * drawable.getIntrinsicHeight() / drawable.getIntrinsicWidth());
        //标识图片的绘制区域
        drawable.setBounds(0, 0, getWidth(), h);

        int[] location = new int[2];
        getLocationOnScreen(location);
        int startY = (totalHeight-h)/2;
        int endY = startY + h;
        int y = location[1];
        canvas.save();
        int dy = 0;
        if (y>startY && y<endY-getBottom()){
            dy = -(y-startY);
        }else if (y>=endY-getBottom()){
            dy = getBottom()-h;
        }
        Log.e("AdImageView", "dy is : "+dy+" , 0transY is  " + y+", startY:" + startY + ", endY:" + endY + ", bottom:" + getBottom() + ",(end-bottom):" + (endY - getBottom()));
        canvas.translate(0, dy);
        super.onDraw(canvas);
        canvas.restore();
    }
}
