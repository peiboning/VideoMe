package com.pbn.org.news.view.web;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.webkit.WebView;

public class BaseWebview extends WebView {
    public BaseWebview(Context context) {
        super(context);
    }

    public BaseWebview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseWebview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    public void destroy() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            if(getParent() != null){
                ((ViewGroup)getParent()).removeView(this);
            }
        }
        super.destroy();

    }


}
