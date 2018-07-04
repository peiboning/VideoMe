package com.pbn.org.news.net.api;

import com.pbn.org.news.model.ChannelRequestBean;
import com.pbn.org.news.model.zixun.HttpResponse1;
import com.pbn.org.news.model.zixun.RequestBean;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ZIXUNAPI {
    @POST("/engine/channelrec")
    Observable<HttpResponse1> getVideoList(@Body ChannelRequestBean bean);

    @POST("/engine/recommend")
    Observable<HttpResponse1> getRecommend(@Body RequestBean bean);
}
