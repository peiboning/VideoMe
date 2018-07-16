package com.pbn.org.news.skin.core;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public abstract class BaseSkinHelper {
    protected View mOwner;

    public BaseSkinHelper(View mOwner) {
        this.mOwner = mOwner;
    }

    public abstract void apply();
    public abstract void parseAttributeSet(Context context, @Nullable AttributeSet attrs);
    protected int parseValue(String attributeValue) {
        if(attributeValue.startsWith("@")){
            return Integer.parseInt(attributeValue.substring(1));
        }
        return -1;
    }
}
