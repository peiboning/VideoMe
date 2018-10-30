package com.pbn.org.news.mvp.presenter;

import com.pbn.org.news.NewsApplication;
import com.pbn.org.news.base.BasePresenter;
import com.pbn.org.news.model.haokan.HKRequestParams;
import com.pbn.org.news.model.haokan.HotWorld;
import com.pbn.org.news.mvp.view.IHomeView;
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

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.IoScheduler;
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

/**
 * function:
 *
 * @author peiboning
 * @DATE 2018/10/11
 */
public class HomePresenter extends BasePresenter<IHomeView>{
    public void loadHostWorld(){
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
                .add("hot/words","method=get")
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
                    JSONObject hot_words = jsonObject.getJSONObject("hot/words");
                    JSONObject data = hot_words.optJSONObject("data");
                    if(null != data){
                        final HotWorld hotWorld = new HotWorld();
                        hotWorld.setHotWords(data.optString("hot_word"));
                        JSONObject like = data.optJSONObject("like");
                        if(null != like){
                            hotWorld.setTitle(like.optString("title"));
                            JSONArray list = like.optJSONArray("list");
                            if(null != list && list.length()>0){
                                JSONObject obj = null;
                                List<String> likes = new ArrayList<>();
                                for(int i = 0;i<list.length() ;i++){
                                    obj = list.getJSONObject(i);
                                    likes.add(obj.getString("displayname"));
                                }
                                hotWorld.setLikes(likes);
                            }
                        }

                        if(ThreadChecker.isUIThread()){
                            view.get().updateHotWords(hotWorld);
                        }else{
                            NewsHandler.postToMainTask(new Runnable() {
                                @Override
                                public void run() {
                                    view.get().updateHotWords(hotWorld);
                                }
                            });
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
