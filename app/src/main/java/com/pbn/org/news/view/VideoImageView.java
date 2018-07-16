package com.pbn.org.news.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class VideoImageView extends ImageView{
    private Paint paint;
    public VideoImageView(Context context) {
        super(context);
        init();
    }

    public VideoImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VideoImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint  = new Paint();
        paint.setColor(Color.parseColor("#66555555"));
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        Rect rect = new Rect(getLeft(), getTop(), getRight(), getBottom());
        canvas.drawRect(rect, paint);
    }
}
