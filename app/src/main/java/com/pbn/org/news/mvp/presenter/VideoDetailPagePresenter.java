package com.pbn.org.news.mvp.presenter;

import com.pbn.org.news.NewsApplication;
import com.pbn.org.news.base.BasePresenter;
import com.pbn.org.news.model.haokan.HKRequestParams;
import com.pbn.org.news.model.haokan.HotWorld;
import com.pbn.org.news.model.haokan.SearchVideo;
import com.pbn.org.news.mvp.view.IVideoDetailView;
import com.pbn.org.news.net.RetrofitClient;
import com.pbn.org.news.net.api.HAOKANAPI;
import com.pbn.org.news.utils.FIXHttps;
import com.pbn.org.news.utils.NewsHandler;
import com.pbn.org.news.utils.ThreadChecker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
        Cache cache = new Cache(new File(NewsApplication.getContext().getExternalCacheDir(), "HttpCache"),
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

}
