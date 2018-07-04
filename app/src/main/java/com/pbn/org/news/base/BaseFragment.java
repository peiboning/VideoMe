package com.pbn.org.news.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

public abstract class BaseFragment extends Fragment {
    protected boolean isViewCreated;
    protected boolean isVisibleToUser;
    protected boolean isDatainited;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewCreated = true;
        prepareFetchData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }

    protected abstract void  fetchData();


    private void prepareFetchData(){
        prepareFetchData(false);
    }

    protected void prepareFetchData(boolean force){
        if(isViewCreated && isVisibleToUser && (!isDatainited || force)){
            isDatainited = true;
            fetchData();
        }
    }
}
