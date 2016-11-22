package com.w4lr.handmovie.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.w4lr.handmovie.base.BaisiContract;
import com.w4lr.handmovie.bean.BaisiResult;
import com.w4lr.handmovie.conf.Constant;
import com.w4lr.handmovie.inter.ModelCallBack;
import com.w4lr.handmovie.model.StringModelImpl;
import com.w4lr.handmovie.util.NetworkUtils;

/**
 * Created by w4lr on 2016/11/20.
 */

public class BaisiPresenter implements BaisiContract.Presenter {

    private Context mContext;

    private BaisiContract.View mView;

    private StringModelImpl mModel;

    private int mAllPages;

    private int mCurPage = 1;

    public BaisiPresenter(Context context, BaisiContract.View view) {
        mContext = context;
        mView = view;
        mView.setPresenter(this);
        mModel = new StringModelImpl();
    }

    @Override
    public void start(boolean latest) {
        mView.startLoading();
        if (!NetworkUtils.isAvailable(mContext)) {
            mView.showNetWorkError();
            mView.stopLoading();
        }

        mModel.loadData(Constant.URL.BAISI_BASE_URL + "&page=" + mCurPage, latest, new ModelCallBack() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                BaisiResult baisiResult = gson.fromJson(result, BaisiResult.class);
                mAllPages = baisiResult.getShowapi_res_body().getPagebean().getAllPages();
                mCurPage = baisiResult.getShowapi_res_body().getPagebean().getCurrentPage();
                mView.showResult(baisiResult);
                mView.stopLoading();
            }

            @Override
            public void onError(String error) {
                mView.showError(error);
                mView.stopLoading();
            }
        });
    }

    /**
     * 加载更多
     */
    @Override
    public void loadMore() {
        mCurPage++;
        if (mCurPage > mAllPages) {
            mView.showError("没有更多了");
            return;
        }
        start(true);
    }

}
