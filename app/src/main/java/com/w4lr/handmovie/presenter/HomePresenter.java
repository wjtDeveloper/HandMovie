package com.w4lr.handmovie.presenter;


import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import com.google.gson.Gson;
import com.w4lr.handmovie.base.BaseModel;
import com.w4lr.handmovie.base.HomeContract;
import com.w4lr.handmovie.bean.HomeResult;
import com.w4lr.handmovie.conf.Constant;
import com.w4lr.handmovie.inter.ModelCallBack;
import com.w4lr.handmovie.model.StringModelImpl;
import com.w4lr.handmovie.util.NetworkUtils;

import java.util.List;

/**
 * Created by w4lr on 2016/11/19.
 */

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View mView;

    private Context mContext;

    private BaseModel mModel;

    public HomePresenter(Context context, HomeContract.View view) {
        mContext = context;
        mView = view;
        mView.setPresenter(this);
        mModel = new StringModelImpl();
    }

    /**
     * 加载数据
     */
    @Override
    public void start(boolean latest) {
        mView.startLoading();
        if (!NetworkUtils.isAvailable(mContext)) {
            //网络不可用
            mView.showNetWorkError();
            mView.stopLoading();
            return;
        }

        mModel.loadData(Constant.URL.DOUBAN_MOVIE_250,latest,new ModelCallBack(){

            @Override
            public void onSuccess(String result) {
                HomeResult homeResult = processResult(result);
                List<HomeResult.SubjectsBean> subjects = homeResult.getSubjects();
                if (subjects == null || homeResult.getSubjects().size() < 1) {
                    mView.showError("There has no more data!");
                    mView.stopLoading();
                } else  {
                    mView.showResult(homeResult.getSubjects());
                    mView.stopLoading();
                }
            }

            @Override
            public void onError(String error) {
                mView.showError(error);
                mView.stopLoading();
            }

        });
    }

    /**
     * 将获取到的数据转换为bean并交给view显示
     * @param result
     */
    private HomeResult processResult(String result) {
        Gson gson = new Gson();
        return gson.fromJson(result, HomeResult.class);
    }

    @Override
    public void goSetting() {
        mContext.startActivity(new Intent(Settings.ACTION_SETTINGS));
    }
}
