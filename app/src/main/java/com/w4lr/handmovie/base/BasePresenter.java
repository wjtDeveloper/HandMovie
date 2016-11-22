package com.w4lr.handmovie.base;

/**
 * Created by w4lr on 2016/11/19.
 */

public interface BasePresenter {

    /**
     * 开始获取数据
     * @param latest 是否强制刷新 true : 强制从网络获取数据 false : 如果本地数据未过期，从本地获取
     *               否则从网络获取
     */
    void start(boolean latest);

}
