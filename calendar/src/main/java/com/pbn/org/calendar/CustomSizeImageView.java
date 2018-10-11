package com.pbn.org.calendar;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CustomSizeImageView extends ImageView{
    public CustomSizeImageView(Context context) {
        super(context);
    }

    public CustomSizeImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomSizeImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));

        int width = getMeasuredWidth();
        int height = (width * 9)>>4;

        int wid = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        int hei = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

        super.onMeasure(wid, hei);
    }
}
