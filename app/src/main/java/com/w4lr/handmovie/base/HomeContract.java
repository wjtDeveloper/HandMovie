package com.w4lr.handmovie.base;

import com.w4lr.handmovie.bean.HomeResult;
import com.w4lr.handmovie.presenter.HomePresenter;

import java.util.List;

/**
 * Created by w4lr on 2016/11/19.
 */

public class HomeContract {

    public interface View extends BaseView<HomePresenter,List<HomeResult.SubjectsBean>> {

       void showDetail();

    }

    public interface Presenter extends BasePresenter {
        void goSetting();
    }

}
