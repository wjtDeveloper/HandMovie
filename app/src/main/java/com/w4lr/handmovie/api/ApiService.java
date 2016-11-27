package com.w4lr.handmovie.api;

import com.w4lr.handmovie.bean.BaisiResult;
import com.w4lr.handmovie.bean.HomeResult;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by w4lr on 2016/11/27.
 */

public interface ApiService {

    @GET("/v2/movie/top250")
    Observable<HomeResult> getMovieTop(@Query("start") int start, @Query("count") int count);

    @GET("/255-1")
    Observable<BaisiResult> getBaisiData(@Query("page") int page);

}
