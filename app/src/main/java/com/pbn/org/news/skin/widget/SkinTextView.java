package com.pbn.org.news.skin.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.pbn.org.news.skin.core.BaseSkinHelper;
import com.pbn.org.news.skin.core.SkinTextViewHelper;
import com.pbn.org.news.skin.inter.ISkinChangeView;

public class SkinTextView extends TextView implements ISkinChangeView{
    private BaseSkinHelper mTextViewHelper;
    public SkinTextView(Context context) {
        super(context);
    }

    public SkinTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SkinTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs){
        mTextViewHelper = new SkinTextViewHelper(this);
        mTextViewHelper.parseAttributeSet(context, attrs);
        mTextViewHelper.apply();
    }

    @Override
    public void applySkin() {
        if(null != mTextViewHelper){
            mTextViewHelper.apply();
        }
    }
}
