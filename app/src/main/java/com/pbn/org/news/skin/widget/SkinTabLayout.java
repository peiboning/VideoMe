package com.pbn.org.news.skin.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;

import com.pbn.org.news.skin.core.res.ResourceManager;
import com.pbn.org.news.skin.inter.ISkinChangeView;

public class SkinTabLayout extends TabLayout implements ISkinChangeView{
    private static final String BACKGROUND = "background";
    private static final String TABINDICATORCOLOR = "tabIndicatorColor";
    private static final String TABSELECTEDTEXTCOLOR = "tabSelectedTextColor";
    private static final String TABTEXTCOLOR = "tabTextColor";

    private int backgroudId;
    private int tabIndicatorColorId;
    private int tabSelectedTextColorId;
    private int tabTextAppearanceId;
    private int tabTextColorId;

    public SkinTabLayout(Context context) {
        super(context);
    }

    public SkinTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SkinTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        int ac = attrs.getAttributeCount();
        String name = "";
        for(int i = 0;i<ac;i++){
            name = attrs.getAttributeName(i);
            if(BACKGROUND.equals(name)){
                backgroudId = parseValue(attrs.getAttributeValue(i));
            }else if(TABINDICATORCOLOR.equals(name)){
                tabIndicatorColorId = parseValue(attrs.getAttributeValue(i));
            }else if(TABSELECTEDTEXTCOLOR.equals(name)){
                tabSelectedTextColorId = parseValue(attrs.getAttributeValue(i));
            }else if(TABTEXTCOLOR.equals(name)){
                tabTextColorId = parseValue(attrs.getAttributeValue(i));
            }

            Log.e("SkinTabLayout", "name:" + attrs.getAttributeName(i) + ",value is:" + attrs.getAttributeValue(i));
        }
    }

    private int parseValue(String attributeValue) {
        if(attributeValue.startsWith("@")){
            return Integer.parseInt(attributeValue.substring(1));
        }
        return -1;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void applySkin() {
        //update bg
        if(-1 != backgroudId){
            setBackgroundColor(ResourceManager.getInstance().getColor(backgroudId));
        }

        if(-1 != tabTextColorId && -1!=tabSelectedTextColorId){
            int textColor = ResourceManager.getInstance().getColor(tabTextColorId);
            int selectTextColor = ResourceManager.getInstance().getColor(tabSelectedTextColorId);
            setTabTextColors(textColor, selectTextColor);
        }
    }
}
