package com.pbn.org.news.net.api;

import com.pbn.org.news.model.haokan.HaokanVideo;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface HAOKANAPI {
    @FormUrlEncoded
    @POST("/haokan/api")
    Observable<HaokanVideo> getList(@QueryMap HashMap<String, String> map, @FieldMap HashMap<String, String> fieldMap);
}
