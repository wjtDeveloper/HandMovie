package com.w4lr.handmovie.presenter;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import com.w4lr.handmovie.api.Constant;
import com.w4lr.handmovie.base.HomeContract;
import com.w4lr.handmovie.bean.HomeResult;
import com.w4lr.handmovie.model.Error;
import com.w4lr.handmovie.model.HomeModelImpl;
import com.w4lr.handmovie.util.NetworkUtils;

/**
 * Created by w4lr on 2016/11/19.
 */

public class HomePresenter extends HomeContract.Presenter {

    private static final String TAG = "HomePresenter";

    private Context mContext;

    private int mStart = 0;

    public HomePresenter(Context context, HomeContract.View view) {
        mContext = context;
        SetVM(new HomeModelImpl(this), view);
        mView.setPresenter(this);
    }

    /**
     * 加载数据
     */
    @Override
    public void start(boolean latest) {
        mView.startLoading();

        if (!NetworkUtils.isAvailable(mContext)) {
           showError("", Error.Type.ERROR_TYPE_NETWORK);
            return;
        }

        mModel.loadData(mStart, Constant.PAGE_MAX_SIZE);

    }

    @Override
    public void goSetting() {
        mContext.startActivity(new Intent(Settings.ACTION_SETTINGS));
    }

    @Override
    public void showResult(HomeResult result) {

        mView.showResult(result.getSubjects());

        mView.stopLoading();
    }

    /**
     * 加载更多
     */
    public void loadMore() {
        mStart += Constant.PAGE_MAX_SIZE;
        if (mStart > 250) {
            showError("没有更多数据了",Error.Type.ERROR_TYPE_OTHER);
            return;
        }

        start(true);
    }

}
