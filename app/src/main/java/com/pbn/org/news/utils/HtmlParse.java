package com.pbn.org.news.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class HtmlParse {
    public static interface OnParseListener{
        void success(String html);
        void onError();
    }

    private OnParseListener mListener;
    private Context mContext;
    private HtmlParseUitls uitls;
    private Handler handler;

    public HtmlParse(OnParseListener listener, Context context) {
        this.mListener = listener;
        this.mContext = context;
        uitls = new HtmlParseUitls(mListener, this);
        handler = new Handler(Looper.getMainLooper());
    }

    public Context getContext() {
        return mContext;
    }

    public void parse(final String url, final String methonName){
        if(Looper.myLooper() == Looper.getMainLooper()){
            parseByWebview(url, methonName);
        }else{
            handler.post(new Runnable() {
                @Override
                public void run() {
                    parseByWebview(url, methonName);
                }
            });
        }
    }

    private void parseByWebview(String url, final String methonName) {
        WebView view = new WebView(mContext);
        addSettings(view.getSettings());
        view.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.e("HTML1", "onPageStarted");
                view.addJavascriptInterface(uitls, "java_obj");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.e("HTML1", "onPageFinished");
                String mn = methonName;
                if(TextUtils.isEmpty(mn)){
                    mn = "getSource";
                }
                view.loadUrl("javascript:window.java_obj."+mn+"('<head>'+" +
                        "document.getElementsByTagName('html')[0].innerHTML+'</head>');");
            }
        });
        view.loadUrl(url);
    }

    private void addSettings(WebSettings settings){
        settings.setJavaScriptEnabled(true);
    }
}
