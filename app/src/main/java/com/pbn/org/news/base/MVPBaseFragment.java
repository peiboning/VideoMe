package com.pbn.org.news.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pbn.org.news.utils.LogUtils;

public abstract class MVPBaseFragment<V, P extends BasePresenter<V>> extends BaseFragment {

    protected P presenter;
    private View mRootView;
    private boolean isCreatedView;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(null == presenter){
            presenter = createPresenter();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(null == presenter){
            presenter = createPresenter();
        }
        LogUtils.d("MVPBaseFragment", "["+hashCode() + "]onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtils.d("MVPBaseFragment", "["+hashCode() + "]onCreateView, isCreatedView : " + isCreatedView);
        if(null == mRootView){
            mRootView = inflater.inflate(getLayoutId(), null, false);
            initView(mRootView);
        }else{
            isCreatedView = true;
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if(null != parent){
                parent.removeView(mRootView);
            }
        }
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(null == presenter){
            presenter = createPresenter();
        }
        if(null != presenter){
            presenter.attachView((V) this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(null != presenter){
            presenter.detachView();
            presenter = null;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public String getChannelName(){
        return "";
    }

    protected abstract P createPresenter();

    protected abstract void initView(View view);

    protected abstract int getLayoutId();

    protected void initData(boolean force){};

    @Override
    protected void fetchData() {

    }
}
