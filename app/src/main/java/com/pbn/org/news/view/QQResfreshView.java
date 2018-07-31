package com.pbn.org.news.view;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class QQResfreshView extends View{
    private Path mPath;
    private Paint mPaint;

    public QQResfreshView(Context context) {
        super(context);
        init();
    }

    public QQResfreshView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public QQResfreshView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {

    }


}
