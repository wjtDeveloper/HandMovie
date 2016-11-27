package com.w4lr.handmovie.base;

import com.w4lr.handmovie.model.Error;
import com.w4lr.handmovie.util.LogUtils;

/**
 * Created by w4lr on 2016/11/19.
 */

public abstract class BasePresenter<R,M> {

    public M mModel;

    public BaseView mView;

    public void SetVM(M model,BaseView view) {
        mModel = model;
        mView = view;
    }

    /**
     * 开始获取数据
     *
     * @param latest 是否强制刷新 true : 强制从网络获取数据 false : 如果本地数据未过期，从本地获取
     *               否则从网络获取
     */
    public abstract void start(boolean latest);



    /**
     * 接收数据并显示到view层
     * @param result
     */
    public abstract void showResult(R result);

    /**
     * 显示错误信息
     *
     * @param error     错误信息
     * @param errorType 错误类型
     */
    public void showError(String error, int errorType) {
        if (errorType == Error.Type.ERROR_TYPE_NETWORK) {
            //网络错误
            mView.showNetWorkError();
        } else {
            //其它错误
            mView.showError("未知错误");
            LogUtils.e(this.getClass().getSimpleName(), "showError: " + error);
        }
        mView.stopLoading();
    }

}

