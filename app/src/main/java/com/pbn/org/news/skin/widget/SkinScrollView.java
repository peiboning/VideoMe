package com.pbn.org.news.skin.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.pbn.org.news.skin.core.BackgroudHelper;
import com.pbn.org.news.skin.core.BaseSkinHelper;
import com.pbn.org.news.skin.inter.ISkinChangeView;

public class SkinScrollView extends ScrollView implements ISkinChangeView{
    private BaseSkinHelper bgHelper;
    public SkinScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SkinScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        bgHelper = new BackgroudHelper(this);
        bgHelper.parseAttributeSet(context, attrs);
    }


    @Override
    public void applySkin() {
        bgHelper.apply();
    }
}
