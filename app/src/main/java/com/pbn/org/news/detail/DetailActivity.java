package com.pbn.org.news.detail;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;

import com.pbn.org.news.R;
import com.pbn.org.news.base.BasePresenter;
import com.pbn.org.news.base.MVPBaseActivity;
import com.pbn.org.news.fragment.DetailPageFragment;
import com.pbn.org.news.model.quyue.QueyueNewsBean;
import com.pbn.org.news.model.common.NewsBean;

public class DetailActivity extends MVPBaseActivity {

    public static void startDetailPage(Activity activity,NewsBean bean){
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra("bean", bean);
        activity.startActivity(intent);

    }
    private QueyueNewsBean queyueNewsBean;
    private DetailPageFragment fragment;


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    @Override
    protected void initView() {
        queyueNewsBean = (QueyueNewsBean) getIntent().getSerializableExtra("bean");
        fragment = DetailPageFragment.newInstance(queyueNewsBean);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.main_content, fragment);
        ft.commit();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail_page;
    }

    @Override
    protected boolean isNeedSlide() {
        return true;
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }
}
