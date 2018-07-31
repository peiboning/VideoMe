package com.pbn.org.news.vh;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pbn.org.news.R;
import com.pbn.org.news.model.common.NewsBean;

public class AdItemVH extends BaseVH {
    private TextView mTitle;
    private ImageView mIconRight;
    private TextView mNewsSource;
    private TextView mNewsComment;
    public AdItemVH(View itemView) {
        super(itemView);

    }

    @Override
    public void showNews(NewsBean bean) {
    }
}
