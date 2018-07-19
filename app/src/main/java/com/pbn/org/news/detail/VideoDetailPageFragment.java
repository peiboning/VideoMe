package com.pbn.org.news.detail;

import android.view.View;

import com.pbn.org.news.base.MVPBaseFragment;
import com.pbn.org.news.mvp.presenter.VideoDetailPagePresenter;
import com.pbn.org.news.mvp.view.IVideoDetailView;

public class VideoDetailPageFragment extends MVPBaseFragment<IVideoDetailView, VideoDetailPagePresenter> implements IVideoDetailView {

    @Override
    protected VideoDetailPagePresenter createPresenter() {
        return new VideoDetailPagePresenter();
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }
}
