package com.pbn.org.news.mvp.presenter;

import android.app.AlarmManager;
import android.content.Context;
import android.content.res.AssetManager;
import android.print.PrinterId;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.JsonObject;
import com.pbn.org.news.base.BasePresenter;
import com.pbn.org.news.loclib.LocationMgr;
import com.pbn.org.news.model.Channel;
import com.pbn.org.news.model.ChannelRequestBean;
import com.pbn.org.news.model.bobo.BOBOModel;
import com.pbn.org.news.model.bobo.BORequestBean;
import com.pbn.org.news.model.haokan.HKRequestParams;
import com.pbn.org.news.model.haokan.HaokanVideo;
import com.pbn.org.news.model.quyue.HttpResponse;
import com.pbn.org.news.model.xigua.QueryMap;
import com.pbn.org.news.model.xigua.XiguaModel;
import com.pbn.org.news.model.zixun.HttpResponse1;
import com.pbn.org.news.model.quyue.QueyueNewsBean;
import com.pbn.org.news.model.sdk.SDKVideoInfo;
import com.pbn.org.news.model.common.NewsBean;
import com.pbn.org.news.mvp.view.INewsListView;
import com.pbn.org.news.net.RetrofitClient;
import com.pbn.org.news.net.api.BOBOAPI;
import com.pbn.org.news.net.api.HAOKANAPI;
import com.pbn.org.news.net.api.XiguaAPI;
import com.pbn.org.news.net.api.ZIXUNAPI;
import com.pbn.org.news.net.api.MyAPi;
import com.pbn.org.news.net.api.SDKAPI;
import com.pbn.org.news.utils.RequestParamsUtils;
import com.pbn.org.news.utils.SpUtils;

import org.json.JSONObject;

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

    private static final int SRC_NUM = 3;
    private static final int SRC_INDEX_NEWSLIST = 0;
    private static final int SRC_INDEX_HK = 1;
    private static final int SRC_INDEX_VIDEOSDK = 2;

    public static final String REFRESH_TIME = "refresh_time";
    private static final long  REFRESH_TIME_INTERNAL = AlarmManager.INTERVAL_HALF_HOUR;
    boolean flag ;
    private int requestNum = 0;

    public void updateNewsList(Channel channel, int pageIndex, final boolean isLoadMore){
        int index = requestNum%SRC_NUM;
        requestNum++;
        if(index == 0){//暂时不需要0渠道(相关推荐没搞定)
            index = requestNum%SRC_NUM;
            requestNum++;
        }
        if(SRC_INDEX_NEWSLIST == index){
            if(channel.getQuickCode() != -1){
                getNewsList(isLoadMore, channel);
                return;
            }
            index++;
        }

        if(SRC_INDEX_HK == index){
            if(!TextUtils.isEmpty(channel.getHaokanId())){
                hkVideo(channel, isLoadMore);
                return;
            }
            index++;
        }

        if(SRC_INDEX_VIDEOSDK == index){
            videoSDK(isLoadMore, channel);
            return;
        }
    }

    private void hkVideo(Channel channel, final boolean isLoadmore){
        HAOKANAPI api = RetrofitClient.getInstance().getHaokanRetrofit().create(HAOKANAPI.class);

        api.getList(HKRequestParams.getQueryMap(), HKRequestParams.getBodyMap(channel.getHaokanId(), isLoadmore))
                .subscribeOn(Schedulers.io())
                .map(new Function<HaokanVideo, List<NewsBean>>() {
                    @Override
                    public List<NewsBean> apply(HaokanVideo haokanVideo) throws Exception {
                        return haokanVideo.toNewBean();
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<NewsBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<NewsBean> newsBeans) {
                        INewsListView view = getView();
                        if(null != view){
                            view.updateNewsList(newsBeans, isLoadmore);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        INewsListView view = getView();
                        if(null != view){
                            view.updateNewsList(null, isLoadmore);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void xiguaVideo(){
        XiguaAPI api = RetrofitClient.getInstance().getXiguaRetrofit().create(XiguaAPI.class);
        api.getData(QueryMap.getQueryMap())
                .subscribeOn(Schedulers.io())
                .map(new Function<XiguaModel, List<NewsBean>>() {
                    @Override
                    public List<NewsBean> apply(XiguaModel xiguaModel) throws Exception {
                        return xiguaModel.toNewsBeans();
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<NewsBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<NewsBean> newsBeans) {
                        getView().updateNewsList(newsBeans, false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
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

    private void videoSDK(final boolean isLoadmore, final Channel channel){
        SDKAPI sdkapi = RetrofitClient.getInstance().getSDKRetrofit().create(SDKAPI.class);
        Map<String, String> map = new HashMap<String, String>();
        map.put("channelId", channel.getTitleCode());
        map.put("cdma_lat", LocationMgr.getInstance().getLatitude()+"");
        map.put("cdma_lng", LocationMgr.getInstance().getLongitude()+"");
        Observable<SDKVideoInfo> observable = sdkapi.getList(map);
        observable.subscribeOn(Schedulers.io())
                .map(new Function<SDKVideoInfo, List<NewsBean>>() {
                    @Override
                    public List<NewsBean> apply(SDKVideoInfo sdkVideoInfo) throws Exception {

                        return sdkVideoInfo.toNewsBeans(channel.getTitleCode());
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<NewsBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<NewsBean> newsBeans) {
                        INewsListView view = getView();
                        if(null != view){
                            view.updateNewsList(newsBeans, isLoadmore);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        INewsListView view = getView();
                        if(null != view){
                            view.updateNewsList(null, isLoadmore);
                        }
                        e.printStackTrace();
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
    int page = 0;
    public void getNewsList(final boolean isLoadMore, Channel channel){

        page++;
        ZIXUNAPI boboapi = RetrofitClient.getInstance().getMiguRetrofit().create(ZIXUNAPI.class);
        ChannelRequestBean requestBean = new ChannelRequestBean();
        requestBean.setChannelid(channel.getQuickCode());
        requestBean.setChannelName(channel.getTitle());
        requestBean.setPage(page);
        if(isLoadMore){
            requestBean.setRefresh("Bottom");
        }
        Observable<HttpResponse1> videoList = boboapi.getVideoList(requestBean);
        videoList.subscribeOn(Schedulers.io())
                .map(new Function<HttpResponse1, List<NewsBean>>() {

                    @Override
                    public List<NewsBean> apply(HttpResponse1 httpResponse1) throws Exception {
                        return httpResponse1.switchToOriginContent(isLoadMore);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<NewsBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<NewsBean> newsBeans) {
                        INewsListView view = getView();
                        if(null != view){
                            view.updateNewsList(newsBeans, isLoadMore);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        INewsListView view = getView();
                        if(null != view){
                            view.updateNewsList(null, isLoadMore);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
