package com.pbn.org.news.search;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.pbn.org.news.R;
import com.pbn.org.news.base.BaseActivity;
import com.pbn.org.news.base.MVPBaseActivity;
import com.pbn.org.news.model.haokan.HotWorld;
import com.pbn.org.news.mvp.presenter.SearchActivityPresenter;
import com.pbn.org.news.mvp.view.ISearchActivityView;
import com.pbn.org.news.search.fragment.SearchRecordFragment;
import com.pbn.org.news.skin.inter.ISkinChange;
import com.pbn.org.news.status_bar.StatusBarCompat;
import com.pbn.org.news.view.NewsToast;

public class SearchActivity extends MVPBaseActivity<ISearchActivityView, SearchActivityPresenter> implements ISkinChange , View.OnClickListener{
    private static final String TAG_RECOED = "tag_record";
    public static final String KEY_HOT_WRODS = "key_hot_wrods";

    private ImageView mBackImage;
    private EditText mEditText;
    private TextView mSearch;
    private FrameLayout mMainContainer;
    private SearchRecordFragment mRecordFragment;
    private SearchListFragment mSearchResultFragment;
    private HotWorld hotWorld;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int color = getResources().getColor(R.color.skin_status_bar);
        StatusBarCompat.setStatusBarColor(this, color);
    }

    @Override
    protected SearchActivityPresenter createPresenter() {
        return new SearchActivityPresenter();
    }

    @Override
    protected void initData() {
        Object obj = getIntent().getSerializableExtra(KEY_HOT_WRODS);
        if(obj instanceof HotWorld){
            hotWorld = (HotWorld) obj;
        }
    }

    @Override
    protected void initView() {
        mBackImage = findViewById(R.id.search_back);
        mEditText = findViewById(R.id.search_content);
        mSearch = findViewById(R.id.search_txt);
        mMainContainer = findViewById(R.id.search_main_layout);
        mBackImage.setOnClickListener(this);
        mSearch.setOnClickListener(this);

        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    searchAction();
                }
                return false;
            }
        });
        if(null != hotWorld){
            mEditText.setHint(hotWorld.getHotWords());
        }
        showSearchRecordFragment();
    }

    private void searchAction() {
        String title = mEditText.getText().toString();
        if(TextUtils.isEmpty(title)){
            title = mEditText.getHint().toString();
        }
        if(TextUtils.isEmpty(title)){
            NewsToast.showSystemToast("搜索内容不能为空");
        }else{
            presenter.updateSearchHistory(title);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void showSearchRecordFragment() {
        mRecordFragment = (SearchRecordFragment) getSupportFragmentManager().findFragmentByTag(TAG_RECOED);
        if(null != mRecordFragment){
            FragmentTransaction transaction =  getSupportFragmentManager().beginTransaction();
            transaction.show(mRecordFragment);
            transaction.hide(mSearchResultFragment);
            transaction.commit();
        }else{
            mRecordFragment = new SearchRecordFragment();
            if(null != hotWorld){
                Bundle bundle = new Bundle();
                bundle.putSerializable(KEY_HOT_WRODS, hotWorld);
                mRecordFragment.setArguments(bundle);
            }
            FragmentTransaction transaction =  getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.search_main_layout, mRecordFragment, TAG_RECOED);
            transaction.commit();
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_layout;
    }

    @Override
    public void applySkin() {
        StatusBarCompat.setStatusBarColor(this, Color.RED);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    private void back(){
        finish();
    }

    @Override
    public void onClick(View v) {
        if(v == mBackImage){
            back();
        }else if(v == mSearch){
            searchAction();
        }
    }
}
