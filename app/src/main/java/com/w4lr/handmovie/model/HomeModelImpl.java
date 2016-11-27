package com.w4lr.handmovie.model;

import com.w4lr.handmovie.api.ApiService;
import com.w4lr.handmovie.api.Constant;
import com.w4lr.handmovie.base.BaseModel;
import com.w4lr.handmovie.base.HomeContract;
import com.w4lr.handmovie.bean.HomeResult;
import com.w4lr.handmovie.presenter.HomePresenter;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by w4lr on 2016/11/27.
 */

public class HomeModelImpl extends BaseModel {

    private HomeContract.Presenter mPresenter;

    public HomeModelImpl(HomePresenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 加载page页的数据
     * @param start 起始数
     * @param count 请求的数据量
     */
    public void loadData(int start, int count) {

        ApiService service = getRetrofit(TYPE_HOME,null).create(ApiService.class);
        service.getMovieTop(start, Constant.PAGE_MAX_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HomeResult>() {
                    @Override
                    public void call(HomeResult result) {
                        mPresenter.showResult(result);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mPresenter.showError(throwable.getMessage(),Error.Type.ERROR_TYPE_NETWORK);
                    }
                });

    }
}
