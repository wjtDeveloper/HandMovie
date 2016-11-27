package com.w4lr.handmovie.base;

import com.w4lr.handmovie.bean.HomeResult;
import com.w4lr.handmovie.model.HomeModelImpl;
import com.w4lr.handmovie.presenter.HomePresenter;


/**
 * Created by w4lr on 2016/11/19.
 */

public class HomeContract {

    public interface View extends BaseView<HomePresenter,HomeResult.SubjectsBean> {

       void showDetail();


    }

    public static abstract class Presenter extends BasePresenter<HomeResult,HomeModelImpl> {

        public abstract void goSetting();

        public abstract void showResult(HomeResult result);


    }

}
