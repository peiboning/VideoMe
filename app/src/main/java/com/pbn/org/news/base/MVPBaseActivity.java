package com.pbn.org.news.base;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

public abstract class MVPBaseActivity<V,P extends BasePresenter<V>> extends BaseActivity {
    protected P presenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = createPresenter();
        if(null != presenter){
            presenter.attachView((V) this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != presenter){
            presenter.detachView();
        }
    }

    protected abstract P createPresenter();


}
