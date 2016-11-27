package com.w4lr.handmovie.base;

import com.w4lr.handmovie.bean.BaisiResult;
import com.w4lr.handmovie.model.BaisiModelImpl;
import com.w4lr.handmovie.presenter.BaisiPresenter;

/**
 * Created by w4lr on 2016/11/20.
 */

public interface BaisiContract {

    public interface View extends BaseView<BaisiPresenter,BaisiResult.ShowapiResBodyBean.PagebeanBean.ContentlistBean> {
        /**
         * 显示单项的详情页
         */
        void showDetail();
    }

    /**
     * 加载更多
     */
    public static abstract class Presenter extends BasePresenter<BaisiResult,BaisiModelImpl> {

        public abstract void loadMore();

    }

}
