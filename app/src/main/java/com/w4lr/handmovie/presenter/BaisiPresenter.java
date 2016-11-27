package com.w4lr.handmovie.presenter;

import android.content.Context;

import com.w4lr.handmovie.base.BaisiContract;
import com.w4lr.handmovie.bean.BaisiResult;
import com.w4lr.handmovie.bean.BaisiResult.ShowapiResBodyBean.PagebeanBean.ContentlistBean;
import com.w4lr.handmovie.model.BaisiModelImpl;
import com.w4lr.handmovie.model.Error;
import com.w4lr.handmovie.util.NetworkUtils;

import java.util.List;

/**
 * Created by w4lr on 2016/11/20.
 */

public class BaisiPresenter extends BaisiContract.Presenter {

    private static final String TAG = "BaisiPresenter";

    private Context mContext;

    private int mAllPages = 100;

    private int mCurPage = 1;

    public BaisiPresenter(Context context, BaisiContract.View view) {
        mContext = context;
        SetVM(new BaisiModelImpl(this),view);
        mView.setPresenter(this);
    }

    @Override
    public void start(boolean latest) {
        mView.startLoading();

        //网络不可用
        if (!NetworkUtils.isAvailable(mContext)) {
            mView.showNetWorkError();
            mView.stopLoading();
        }

        mModel.loadData(mCurPage);
    }


    /**
     * 加载更多
     */
    @Override
    public void loadMore() {
        mCurPage++;
        if (mCurPage > mAllPages) {
            showError("没有更多了",Error.Type.ERROR_TYPE_OTHER);
            return;
        }
        start(true);
    }

    /**
     * 接收Model返回的结果，通知view显示结果
     * @param result
     */
    public void showResult(BaisiResult result) {
        mView.stopLoading();
        int resCode = result.getShowapi_res_code();
        //获取数据成功
        if (resCode == 0) {
            //总页数
            if (mAllPages == 0) {
                mAllPages = result.getShowapi_res_body().getPagebean().getAllPages();
            }
            //当前页
            mCurPage = result.getShowapi_res_body().getPagebean().getCurrentPage();
            //要显示的数据
            List<ContentlistBean> dataSets = result.getShowapi_res_body().getPagebean()
                    .getContentlist();
            mView.showResult(dataSets);
        } else {
            //返回结果有错误
            String error = result.getShowapi_res_error();
            mView.showError(error);
        }
    }

}
