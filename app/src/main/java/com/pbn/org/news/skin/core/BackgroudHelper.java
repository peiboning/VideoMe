package com.pbn.org.news.skin.core;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.pbn.org.news.skin.core.res.ResourceManager;

public class BackgroudHelper extends BaseSkinHelper{
    private static final String BACKGROUND = "background";
    private int backgroudId = -1;

    public BackgroudHelper(View owner) {
        super(owner);
    }

    @Override
    public void parseAttributeSet(Context context, @Nullable AttributeSet attrs){
        int ac = attrs.getAttributeCount();
        String name = "";
        for(int i = 0;i<ac;i++){
            name = attrs.getAttributeName(i);
            if(BACKGROUND.equals(name)){
                backgroudId = parseValue(attrs.getAttributeValue(i));
            }
        }
    }

    @Override
    public void apply() {
        if(null != mOwner && backgroudId != -1){
            int bgColor = ResourceManager.getInstance().getColor(backgroudId);
            mOwner.setBackgroundColor(bgColor);
        }
    }
}
