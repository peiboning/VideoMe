package com.pbn.org.news.net;

import com.pbn.org.news.NewsApplication;
import com.pbn.org.news.cache.CacheManager;
import com.pbn.org.news.utils.FIXHttps;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient sInstance = null;
    private Retrofit retrofit;
    private Retrofit retrofitZIXUN;
    private Retrofit retrofitSDK;
    private Retrofit retrofitBOBO;
    private Retrofit retrofitXigua;
    private Retrofit retrofitHaokan;

    public static RetrofitClient getInstance() {
        if (null == sInstance) {
            synchronized (RetrofitClient.class) {
                if (null == sInstance) {
                    sInstance = new RetrofitClient();
                }
            }
        }
        return sInstance;
    }

    private RetrofitClient() {
    }

    public synchronized Retrofit getquAppRetrofit() {
        if(null == retrofit){

            Cache cache = new Cache(new File(CacheManager.getInstance().getExternalCacheDir(), "HttpCache"),
                    1024 * 1024 * 50);

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.cache(cache)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS);

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.quapp.cn/")
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public synchronized Retrofit getMiguRetrofit() {
        if(null == retrofitZIXUN){

            Cache cache = new Cache(new File(CacheManager.getInstance().getExternalCacheDir(), "HttpCache"),
                    1024 * 1024 * 50);

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.cache(cache)
//                    .sslSocketFactory(SSLFactory.getSslSocket().getSocketFactory())
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS);

            retrofitZIXUN = new Retrofit.Builder()
                    .baseUrl("http://ss.sohu.com")
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofitZIXUN;
    }

    public synchronized Retrofit getSDKRetrofit() {
        if(null == retrofitSDK){

            Cache cache = new Cache(new File(CacheManager.getInstance().getExternalCacheDir(), "HttpCache"),
                    1024 * 1024 * 50);

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.cache(cache)
                    .hostnameVerifier(FIXHttps.getHostNameVerifier())
                    .sslSocketFactory(FIXHttps.getSSLFactory())
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS);

            retrofitSDK = new Retrofit.Builder()
                    .baseUrl("https://api.k.sohu.com")
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofitSDK;
    }

    public synchronized Retrofit getBOBORetrofit() {
        if(null == retrofitSDK){

            Cache cache = new Cache(new File(CacheManager.getInstance().getExternalCacheDir(), "HttpCache"),
                    1024 * 1024 * 50);

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.cache(cache)
                    .hostnameVerifier(FIXHttps.getHostNameVerifier())
                    .sslSocketFactory(FIXHttps.getSSLFactory())
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS);

            retrofitSDK = new Retrofit.Builder()
                    .baseUrl("https://api.bbobo.com")
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofitSDK;
    }
    public synchronized Retrofit getXiguaRetrofit() {
        if(null == retrofitXigua){

            Cache cache = new Cache(new File(CacheManager.getInstance().getExternalCacheDir(), "HttpCache"),
                    1024 * 1024 * 50);

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.cache(cache)
                    .hostnameVerifier(FIXHttps.getHostNameVerifier())
                    .sslSocketFactory(FIXHttps.getSSLFactory())
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS);

            retrofitXigua = new Retrofit.Builder()
                    .baseUrl("https://lf.snssdk.com")
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofitXigua;
    }

    public synchronized Retrofit getHaokanRetrofit() {
        if(null == retrofitHaokan){

            Cache cache = new Cache(new File(CacheManager.getInstance().getExternalCacheDir(), "HttpCache"),
                    1024 * 1024 * 50);

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.cache(cache)
                    .hostnameVerifier(FIXHttps.getHostNameVerifier())
                    .sslSocketFactory(FIXHttps.getSSLFactory())
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS);

            retrofitHaokan = new Retrofit.Builder()
                    .baseUrl("https://sv.baidu.com")
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofitHaokan;
    }
}