package com.pbn.org.news.base;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<V> {
    protected WeakReference<V> view;

    protected void attachView(V view){
        this.view = new WeakReference<>(view);
    }

    protected void detachView(){
        if(null != view){
            view.clear();
            view = null;
        }
    }

    protected boolean isAttachView(){
        return null != view && null != view.get();
    }

    protected V getView(){
        if(isAttachView()){
            return view.get();
        }
        return null;
    }
}
