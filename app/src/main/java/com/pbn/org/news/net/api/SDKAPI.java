package com.pbn.org.news.net.api;

import com.pbn.org.news.model.sdk.SDKVideoInfo;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface SDKAPI {
    @GET("/api/videotab/getVideoList.go")
    Observable<SDKVideoInfo> getList(@QueryMap Map<String, String> map);
}
