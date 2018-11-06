package com.pbn.org.news.mvp.presenter;

import android.content.Context;

import com.pbn.org.news.base.BasePresenter;
import com.pbn.org.news.cache.CacheManager;
import com.pbn.org.news.loclib.LocationMgr;
import com.pbn.org.news.model.common.Image;
import com.pbn.org.news.model.common.NewsBean;
import com.pbn.org.news.model.haokan.HKRequestParams;
import com.pbn.org.news.model.haokan.SearchVideo;
import com.pbn.org.news.model.sdk.SDKVideoInfo;
import com.pbn.org.news.model.zixun.VideoModel;
import com.pbn.org.news.mvp.view.IVideoDetailView;
import com.pbn.org.news.net.RetrofitClient;
import com.pbn.org.news.net.api.SDKAPI;
import com.pbn.org.news.utils.FIXHttps;
import com.pbn.org.news.utils.NetUtil;
import com.pbn.org.news.utils.NewsHandler;
import com.pbn.org.news.utils.ThreadChecker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class VideoDetailPagePresenter extends BasePresenter<IVideoDetailView>{

    public void getRelativeHKVideo(final String vid){
        Cache cache = new Cache(new File(CacheManager.getInstance().getExternalCacheDir(), "HttpCache"),
                1024 * 1024 * 50);

        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cache(cache)
                .hostnameVerifier(FIXHttps.getHostNameVerifier())
                .sslSocketFactory(FIXHttps.getSSLFactory())
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS);
        Map<String, String> map = HKRequestParams.getQueryMap();
        Set<String> keys = map.keySet();
        StringBuilder sb = new StringBuilder("t=1");
        for (String key : keys){
            sb.append("&").append(key).append("=").append(map.get(key));
        }

        RequestBody body = new FormBody.Builder()
                .add("video/detail","method=get&url_key=2209353772427868890&log_param_source=bjh&vid=" + vid)
                .build();
        final Request request = new Request.Builder()
                .url("https://sv.baidu.com/haokan/api?" + sb.toString())
                .post(body)
                .build();
        builder.build().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody requestBody = response.body();
                try {
                    JSONObject jsonObject = new JSONObject(requestBody.string());
                    JSONObject videoDetail = jsonObject.getJSONObject("video/detail");
                    JSONObject data = videoDetail.optJSONObject("data");
                    if(null != data){
                        JSONArray array = data.getJSONArray("curVideoRelate");
                        if(null != array && data.length()>0){
                            JSONObject relateObj = null;
                            final List<SearchVideo> videoList = new ArrayList<>();
                            SearchVideo video;
                            for(int i = 0;i<array.length() ;i++){
                                relateObj = array.getJSONObject(i);
                                video = new SearchVideo();

                                video.setMedia_id(relateObj.optString("vid"));
                                video.setAuthor(relateObj.optString("author"));
                                video.setCover_src(relateObj.optString("cover_src"));
                                video.setDuration(relateObj.optInt("duration"));
                                video.setVideo_src(relateObj.optString("video_src"));
                                video.setTitle(relateObj.optString("title"));
                                video.setPlaycntText(relateObj.optString("playcntText"));
                                video.setContentSource(NewsBean.CONTENT_SOURCE_Haokan);
                                videoList.add(video);
                            }
                            if(ThreadChecker.isUIThread()){
                                view.get().updateRelateVideo(videoList);
                            }else{
                                NewsHandler.postToMainTask(new Runnable() {
                                    @Override
                                    public void run() {
                                        view.get().updateRelateVideo(videoList);
                                    }
                                });
                            }
                            return;
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(ThreadChecker.isUIThread()){
                    view.get().updateRelateError();
                }else{
                    NewsHandler.postToMainTask(new Runnable() {
                        @Override
                        public void run() {
                            view.get().updateRelateError();
                        }
                    });
                }

            }
        });
    }

    public void getRelateVideoFromSDK(String newsId, final String channelId, Context context){
        SDKAPI sdkapi = RetrofitClient.getInstance().getSDKRetrofit().create(SDKAPI.class);
        Map<String, String> map = new HashMap<String, String>();
        map.put("channelId", channelId);
        map.put("newsId", newsId);
        map.put("t", System.currentTimeMillis()+"");
        map.put("rt", "json");
        map.put("net", NetUtil.getNetworkType(context));
        map.put("cdma_lat", LocationMgr.getInstance().getLatitude()+"");
        map.put("cdma_lng", LocationMgr.getInstance().getLongitude()+"");

        sdkapi.getRelateList(map).subscribeOn(Schedulers.io())
                .map(new Function<SDKVideoInfo, List<NewsBean>>() {
                    @Override
                    public List<NewsBean> apply(SDKVideoInfo sdkVideoInfo) throws Exception {
                        return sdkVideoInfo.toNewsBeans(channelId);
                    }
                })
                .map(new Function<List<NewsBean>, List<SearchVideo>>() {
                    @Override
                    public List<SearchVideo> apply(List<NewsBean> newsBeans) throws Exception {

                        return parseNewsListToSearVideos(newsBeans, NewsBean.CONTENT_SOURCE_sdk);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<SearchVideo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<SearchVideo> list) {
                        view.get().updateRelateVideo(list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.get().updateRelateVideo(null);
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private List<SearchVideo> parseNewsListToSearVideos(List<NewsBean> newsBeans, int type) {
        List<SearchVideo> list = new ArrayList<>();
        if(null != newsBeans && newsBeans.size()>0){
            SearchVideo video = null;
            for(NewsBean bean : newsBeans){
                video = new SearchVideo();
                video.setMedia_id(bean.getId());
                video.setAuthor(bean.getSource());
                List<Image> imgs = bean.getImages();
                if(null != imgs && imgs.size() > 0){
                    video.setCover_src(imgs.get(0).getUrl());
                }
                List<VideoModel> videos = bean.getVideos();
                if(null != videos && videos.size()>0){
                    video.setDuration((int) videos.get(0).getDuration());
                    video.setVideo_src(videos.get(0).getUrl());
                }
                video.setTitle(bean.getTitle());
                video.setPlaycntText(parsePlayNumTimes(bean.getPlayCount()));
                video.setContentSource(type);
                video.setChannel(bean.getChannelId());
                list.add(video);
            }
        }
        return list;
    }

    public String parsePlayNumTimes(int num){
        if(num<=0){
            Random random = new Random();
            int a = random.nextInt(1000) + 50;
            return a+"次播放";
        }

        if(num<10000){
            return num + "次播放";
        }

        int tempOne = num % 10000;

        float floatNum = tempOne * 1.0f/10000;
        String a = String.format("%.1f", floatNum);


        return num/10000 + a +"万次播放";
    }

}
