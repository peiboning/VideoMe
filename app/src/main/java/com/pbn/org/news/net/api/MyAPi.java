package com.pbn.org.news.net.api;

import com.pbn.org.news.model.quyue.HttpResponse;
import com.pbn.org.news.model.quyue.QueyueNewsBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MyAPi {
    @FormUrlEncoded
    @POST("info/getxdinfo")
    Observable<HttpResponse<List<QueyueNewsBean>>> getxdInfo(@FieldMap Map<String, Object> map);
}
