package com.w4lr.handmovie.api;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by w4lr on 2016/11/27.
 */

public class BaisiInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        HttpUrl url = oldRequest.url().newBuilder()
                .addQueryParameter("showapi_appid", "27512")
                .addQueryParameter("showapi_sign", "432edcb3e3e0476ea79648239f776bec")
                .build();
        Request newRequest = oldRequest.newBuilder().url(url).build();
        return chain.proceed(newRequest);
    }
}
