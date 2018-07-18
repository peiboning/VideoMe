package com.pbn.org.news.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

@SuppressLint("AppCompatCustomView")
public class RoundImageView extends ImageView {
    private int radius;
    private Paint mPaint;
    public RoundImageView(Context context) {
        super(context);
        init();
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = Math.min(getMeasuredHeight(), getMeasuredWidth());
        radius = size/2;
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (getDrawable() == null) {
            return; // couldn't resolve the URI
        }

        if (getDrawable().getIntrinsicWidth() == 0 || getDrawable().getIntrinsicHeight() == 0) {
            return;     // nothing to draw (empty bounds)
        }

        if (getImageMatrix() == null && getPaddingTop() == 0 && getPaddingLeft() == 0) {
            getDrawable().draw(canvas);
        } else {
            final int saveCount = canvas.getSaveCount();
            canvas.save();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                if (getCropToPadding()) {
                    final int scrollX = getScrollX();
                    final int scrollY = getScrollY();
                    canvas.clipRect(scrollX + getPaddingLeft(), scrollY + getPaddingTop(),
                            scrollX + getRight() - getLeft() - getPaddingRight(),
                            scrollY + getBottom() - getTop() - getPaddingBottom());
                }
            }

            canvas.translate(getPaddingLeft(), getPaddingTop());

            Bitmap bitmap = drawableToBitmap(getDrawable());
            BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            mPaint.setShader(shader);
            canvas.drawCircle(getWidth()/2, getHeight()/2, radius, mPaint);
            canvas.restoreToCount(saveCount);
        }
    }

    private Bitmap drawableToBitmap(Drawable drawable){
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_4444 );
        Canvas canvas = new Canvas(bitmap);

        if(getImageMatrix() != null){
            canvas.concat(getImageMatrix());
        }

        drawable.draw(canvas);
        return bitmap;

    }
}
