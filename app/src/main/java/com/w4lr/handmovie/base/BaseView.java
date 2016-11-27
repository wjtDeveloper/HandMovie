package com.w4lr.handmovie.base;

import android.view.View;

import com.w4lr.handmovie.bean.BaisiResult;

import java.util.List;

/**
 * Created by w4lr on 2016/11/19.
 */

public interface BaseView<T,R> {

    void initViews(View view);

    /**
     * 设置presenter
     * @param presenter
     */
    void setPresenter(T presenter);

    /**
     * 显示数据
     * @param result
     */
    void showResult(List<R> result);

    void startLoading();

    void stopLoading();

    void showError(String error);

    void showNetWorkError();

}

