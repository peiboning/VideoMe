package com.pbn.org.news.skin.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.pbn.org.news.skin.core.BackgroudHelper;
import com.pbn.org.news.skin.core.BaseSkinHelper;
import com.pbn.org.news.skin.inter.ISkinChangeView;

public class SkinLinearLayout extends LinearLayout implements ISkinChangeView{
    private int backgroudId = -1;
    private BaseSkinHelper mBackgroudHelper;

    public SkinLinearLayout(Context context) {
        super(context);
    }

    public SkinLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SkinLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs){
        mBackgroudHelper = new BackgroudHelper(this);
        mBackgroudHelper.parseAttributeSet(context, attrs);
        applySkin();
    }



    @Override
    public void applySkin() {
        if(null != mBackgroudHelper){
            mBackgroudHelper.apply();
        }
    }
}
