package com.pbn.org.news.mvp.presenter;

import android.app.AlarmManager;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.pbn.org.news.base.BasePresenter;
import com.pbn.org.news.model.Channel;
import com.pbn.org.news.model.ChannelRequestBean;
import com.pbn.org.news.model.bobo.BOBOModel;
import com.pbn.org.news.model.bobo.BORequestBean;
import com.pbn.org.news.model.quyue.HttpResponse;
import com.pbn.org.news.model.zixun.HttpResponse1;
import com.pbn.org.news.model.quyue.QueyueNewsBean;
import com.pbn.org.news.model.sdk.SDKVideoInfo;
import com.pbn.org.news.model.common.NewsBean;
import com.pbn.org.news.mvp.view.INewsListView;
import com.pbn.org.news.net.RetrofitClient;
import com.pbn.org.news.net.api.BOBOAPI;
import com.pbn.org.news.net.api.ZIXUNAPI;
import com.pbn.org.news.net.api.MyAPi;
import com.pbn.org.news.net.api.SDKAPI;
import com.pbn.org.news.utils.RequestParamsUtils;
import com.pbn.org.news.utils.SpUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class NewsListPresenter extends BasePresenter<INewsListView> {
    public static final String REFRESH_TIME = "refresh_time";
    private static final long  REFRESH_TIME_INTERNAL = AlarmManager.INTERVAL_HALF_HOUR;
    public void updateNewsList(Channel channel, int pageIndex, final boolean isLoadMore){
        videoSDK(isLoadMore, channel);
    }

    private void boboSDK(){
        BOBOAPI boboapi = RetrofitClient.getInstance().getBOBORetrofit().create(BOBOAPI.class);
        Map<String, String> map = new BORequestBean().getMapp();
        Observable<BOBOModel> observable = boboapi.getVideo(map);
        observable.subscribeOn(Schedulers.io())
                .subscribe(new Observer<BOBOModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BOBOModel boboModel) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void videoSDK(final boolean isLoadmore, Channel channel){
        SDKAPI sdkapi = RetrofitClient.getInstance().getSDKRetrofit().create(SDKAPI.class);
        Map<String, String> map = new HashMap<String, String>();
        map.put("channelId", channel.getTitleCode());
        Observable<SDKVideoInfo> observable = sdkapi.getList(map);
        observable.subscribeOn(Schedulers.io())
                .map(new Function<SDKVideoInfo, List<NewsBean>>() {
                    @Override
                    public List<NewsBean> apply(SDKVideoInfo sdkVideoInfo) throws Exception {

                        return sdkVideoInfo.toNewsBeans();
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<NewsBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<NewsBean> newsBeans) {
                        getView().updateNewsList(newsBeans, isLoadmore);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    private void Queyue(Channel channel, int pageIndex, final boolean isLoadMore) {
        MyAPi api = RetrofitClient.getInstance().getquAppRetrofit().create(MyAPi.class);

        Observable<HttpResponse<List<QueyueNewsBean>>> observable
                = api.getxdInfo(RequestParamsUtils.getNewsRequestParam(channel.getTitleCode(), String.valueOf(pageIndex)));

        observable.subscribeOn(Schedulers.io())
                .switchMap(new Function<HttpResponse<List<QueyueNewsBean>>, ObservableSource<QueyueNewsBean>>() {
                    @Override
                    public ObservableSource<QueyueNewsBean> apply(HttpResponse<List<QueyueNewsBean>> listHttpResponse) {
                        return Observable.fromIterable(listHttpResponse.getData());
                    }
                }).filter(new Predicate<QueyueNewsBean>() {
            @Override
            public boolean test(QueyueNewsBean bean) {
                return !bean.getType().equals("ad");
            }
        }).toList()
                .map(new Function<List<QueyueNewsBean>, List<NewsBean>>() {
                    @Override
                    public List<NewsBean> apply(List<QueyueNewsBean> queyueNewsBeans) {
                        List<NewsBean> targetList = new ArrayList<NewsBean>(queyueNewsBeans.size());
                        for(QueyueNewsBean bean : queyueNewsBeans){
                            targetList.add(bean.switchToOriginContent());
                        }
                        return targetList;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<NewsBean>>() {
                               @Override
                               public void accept(List<NewsBean> queyueNewsBeans) {
                                   Log.e("ChannelFragment", "onNext : thread is " + Thread.currentThread().getName());
                                   NewsListPresenter.this.getView().updateNewsList(queyueNewsBeans, isLoadMore);
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable e) {
                                   e.printStackTrace();
                                   Log.e("ChannelFragment", "onError   " );
                               }
                           }

                )
        ;
    }

    public void refreshNews(Channel channel){
        if(null != channel){
            long lastUpdate = SpUtils.getLong(REFRESH_TIME , 0);
//            if(System.currentTimeMillis() - lastUpdate > REFRESH_TIME_INTERNAL){
//                getView().requestNews(true);
//                return;
//            }
        }

//        getView().requestNews(false);

    }

    public void getNewsList(final boolean isLoadMore){
        ZIXUNAPI boboapi = RetrofitClient.getInstance().getMiguRetrofit().create(ZIXUNAPI.class);
        ChannelRequestBean requestBean = new ChannelRequestBean();
        requestBean.setChannelid(2);
        requestBean.setChannelName("视频");
        Observable<HttpResponse1> videoList = boboapi.getVideoList(requestBean);
        videoList.subscribeOn(Schedulers.io())
                .map(new Function<HttpResponse1, List<NewsBean>>() {

                    @Override
                    public List<NewsBean> apply(HttpResponse1 httpResponse1) throws Exception {
                        return httpResponse1.switchToOriginContent();
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<NewsBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<NewsBean> newsBeans) {
                        getView().updateNewsList(newsBeans, isLoadMore);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
