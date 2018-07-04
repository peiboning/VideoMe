package com.pbn.org.news.net.api;

import com.pbn.org.news.model.bobo.BOBOModel;
import com.pbn.org.news.model.bobo.BORequestBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface BOBOAPI {
    @FormUrlEncoded
    @POST("/v1/recommend/index.json")
    Observable<BOBOModel> getVideo(@FieldMap Map<String, String> map);
}
