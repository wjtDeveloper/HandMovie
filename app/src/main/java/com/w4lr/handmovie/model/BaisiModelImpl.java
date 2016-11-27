package com.w4lr.handmovie.model;


import com.w4lr.handmovie.api.ApiService;
import com.w4lr.handmovie.api.BaisiInterceptor;
import com.w4lr.handmovie.base.BaisiContract;
import com.w4lr.handmovie.base.BaseModel;
import com.w4lr.handmovie.bean.BaisiResult;
import com.w4lr.handmovie.presenter.BaisiPresenter;

import okhttp3.OkHttpClient;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by w4lr on 2016/11/27.
 */

public class BaisiModelImpl extends BaseModel {

    private BaisiContract.Presenter mPresenter;

    public BaisiModelImpl(BaisiPresenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 加载page页的数据
     * @param page
     */
    public void loadData(int page) {
        OkHttpClient client = getDefaultClient().newBuilder()
                .addInterceptor(new BaisiInterceptor())
                .build();

        ApiService service = getRetrofit(TYPE_BAISI,client).create(ApiService.class);
        service.getBaisiData(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BaisiResult>() {
                    @Override
                    public void call(BaisiResult result) {
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
