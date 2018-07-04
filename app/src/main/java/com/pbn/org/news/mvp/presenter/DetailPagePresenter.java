package com.pbn.org.news.mvp.presenter;

import android.content.Context;

import com.pbn.org.news.base.BasePresenter;
import com.pbn.org.news.mvp.view.IDetailPageView;
import com.pbn.org.news.utils.HtmlParse;

public class DetailPagePresenter extends BasePresenter<IDetailPageView> {
    private Context context;

    public DetailPagePresenter(Context context) {
        this.context = context;
    }

    public void parseHtml(String url){
        HtmlParse parse = new HtmlParse(new HtmlParse.OnParseListener() {
            @Override
            public void success(String html) {
                getView().onLoadedHtml(html);
            }

            @Override
            public void onError() {
                getView().onLoadedHtmlError();
            }
        }, context);
        parse.parse(url, null);
    }
}
