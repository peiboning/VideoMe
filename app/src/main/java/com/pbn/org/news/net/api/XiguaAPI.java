package com.pbn.org.news.net.api;

import com.pbn.org.news.model.xigua.XiguaModel;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface XiguaAPI {

    @GET("/video/app/stream/v51/")
    Observable<XiguaModel> getData(@QueryMap HashMap<String, String> map);

}
