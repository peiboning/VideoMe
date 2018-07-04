package com.pbn.org.news.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pbn.org.news.R;
import com.pbn.org.news.base.MVPBaseFragment;
import com.pbn.org.news.model.quyue.QueyueNewsBean;
import com.pbn.org.news.mvp.presenter.DetailPagePresenter;
import com.pbn.org.news.mvp.view.IDetailPageView;
import com.pbn.org.news.view.web.DetailPageWeb;

public class DetailPageFragment extends MVPBaseFragment<IDetailPageView, DetailPagePresenter> implements IDetailPageView {

    private static final String DETAIL_PAGE_URL = "detail_news";
    public static DetailPageFragment newInstance(QueyueNewsBean bean){
        DetailPageFragment fragment = new DetailPageFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DETAIL_PAGE_URL, bean);
        fragment.setArguments(bundle);
        return fragment;
    }
    private LinearLayout mWebviewLayout;
    private String detailUrl;
    private QueyueNewsBean queyueNewsBean;
    private boolean isResume;
    private ImageView back;
    private DetailPageWeb detailPageWeb;
    private Handler handler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    protected DetailPagePresenter createPresenter() {
        return new DetailPagePresenter(getContext());
    }

    @Override
    protected void initView(View view) {
        detailPageWeb = view.findViewById(R.id.webview);
        back = view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)DetailPageFragment.this.getContext()).finish();
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        isResume = true;
        if(null == queyueNewsBean){
            queyueNewsBean = (QueyueNewsBean) getArguments().getSerializable(DETAIL_PAGE_URL);
        }
        presenter.parseHtml(queyueNewsBean.getDetail_url());

        Log.e("queyueNewsBean", "\n" + queyueNewsBean.getDetail_url()+"\n");

    }



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_detail;
    }

    @Override
    public void onLoadedHtml(String html) {
        loadhtml(html, true);
    }

    @Override
    public void onLoadedHtmlError() {
        loadhtml(queyueNewsBean.getDetail_url(), false);
    }

    private void loadhtml(final String html, final boolean flag){
        if(Looper.getMainLooper() == Looper.myLooper()){
            if(flag){
                detailPageWeb.loadData(html, "text/html", "UTF-8");
            }else{
                detailPageWeb.loadUrl(queyueNewsBean.getDetail_url());
            }
        }else{
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if(flag){
                        detailPageWeb.loadData(html, "text/html", "UTF-8");
                    }else{
                        detailPageWeb.loadUrl(queyueNewsBean.getDetail_url());
                    }
                }
            });
        }
    }


}
