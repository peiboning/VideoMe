package com.pbn.org.news.utils;

import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;

public class HtmlParseUitls {
    private HtmlParse.OnParseListener mListener;
    private HtmlParse parse;

    public HtmlParseUitls(HtmlParse.OnParseListener mListener, HtmlParse parse) {
        this.mListener = mListener;
        this.parse = parse;
    }

    @JavascriptInterface
    public void getSource(String html){
        if(!TextUtils.isEmpty(html)){
            Document doc = Jsoup.parse(html);
            Elements es= doc.select("div[class=article-main]");

            int size = es.size();
            for(int i = 0;i<size;i++){
                Element e = es.get(i);
                String h = e.outerHtml();
                try {
                    InputStream in = parse.getContext().getAssets().open("html/article_text.html");
                    byte[] buffer = new byte[1024];
                    int len = -1;
                    StringBuffer sb = new StringBuffer();
                    while ((len = in.read(buffer)) != -1){
                        String s = new String(buffer, 0, len);
                        sb.append(s);
                    }

                    String srcH = sb.toString();
                    srcH = srcH.replace("$DOC",h);
                    Log.e("HTML1", srcH);
                    if(!TextUtils.isEmpty(srcH)){
                        if(null != mListener){
                            mListener.success(srcH);
                        }
                        return;
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }

            if(null != mListener){
                mListener.onError();
            }
        }
    }
}
