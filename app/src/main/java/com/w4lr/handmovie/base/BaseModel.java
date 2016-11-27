package com.w4lr.handmovie.base;


import com.w4lr.handmovie.api.Constant;

import junit.runner.BaseTestRunner;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by w4lr on 2016/11/19.
 */

public abstract class BaseModel {

    private OkHttpClient mClient;

    public static final int TYPE_HOME = 0;

    public static final int TYPE_BAISI = 1;

    /**
     * 获取默认的client
     * @return
     */
    public OkHttpClient getDefaultClient() {
       if (mClient == null) {
           mClient = new OkHttpClient.Builder()
                   .connectTimeout(Constant.CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                   .readTimeout(Constant.READ_TIME_OUT,TimeUnit.MILLISECONDS)
                   .build();
       }
        return mClient;
    }

    /**
     * 获取一个retrofit实例
     * @param type 根据base_url的不同，传入同的类型
     * @param client client实例,如果传入null,则使用默认的client
     * @return
     */
    public Retrofit getRetrofit(int type,OkHttpClient client) {
        String baseUrl;
        if (type == TYPE_HOME) {
            baseUrl = "http://api.douban.com";
        } else if (type == TYPE_BAISI){
            baseUrl = "http://route.showapi.com";
        } else {
            throw new RuntimeException("unknown model type");
        }

        if (client == null) {
            client = getDefaultClient();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        return retrofit;
    }

}
