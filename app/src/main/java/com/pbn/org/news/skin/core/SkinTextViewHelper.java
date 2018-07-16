package com.pbn.org.news.skin.core;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.pbn.org.news.skin.core.res.ResourceManager;
import com.pbn.org.news.skin.widget.SkinTextView;

public class SkinTextViewHelper extends BaseSkinHelper{
    private static final String TEXTCOLOR = "textColor";
    private int textColorId = -1;
    public SkinTextViewHelper(View owner) {
        super(owner);
    }

    @Override
    public void apply() {
        if(textColorId != -1){
            int color = ResourceManager.getInstance().getColor(textColorId);
            ((SkinTextView)mOwner).setTextColor(color);
        }
    }

    @Override
    public void parseAttributeSet(Context context, @Nullable AttributeSet attrs) {
        int ac = attrs.getAttributeCount();
        String name = "";
        for(int i = 0;i<ac;i++){
            name = attrs.getAttributeName(i);
            if(TEXTCOLOR.equals(name)){
                textColorId = parseValue(attrs.getAttributeValue(i));
            }
        }
    }
}
