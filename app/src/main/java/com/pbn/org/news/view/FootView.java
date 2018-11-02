package com.pbn.org.news.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.pbn.org.news.R;

public class FootView extends FrameLayout {

    public final static int STATE_LOADING = 0;
    public final static int STATE_COMPLETE = 1;
//    public final static int STATE_NOMORE = 2;
    public final static int STATE_NO_NETWORK = 3;
//    public final static int STATE_NOMORE_LIMIT_SIZE = 4;

    private TextView textView;
    public FootView(@NonNull Context context) {
        super(context);
        init();
    }

    public FootView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FootView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        View view = LayoutInflater.from(getContext()).inflate(R.layout.listview_footer,null);
        textView = view.findViewById(R.id.listview_foot_more);
        addView(view);
        setVisibility(GONE);
    }

    public void setStatus(int state){
        switch (state){
            case STATE_LOADING:
                textView.setText("正在加载");
                setVisibility(VISIBLE);
                break;
            case STATE_COMPLETE:
                textView.setText("加载完毕");
                setVisibility(GONE);
                break;
            case STATE_NO_NETWORK:
                textView.setText("网络不可用");
                setVisibility(VISIBLE);
                break;
            default:
                setVisibility(GONE);
                break;
        }
    }
}
