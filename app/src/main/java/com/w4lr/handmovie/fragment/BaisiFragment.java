package com.w4lr.handmovie.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.w4lr.handmovie.R;
import com.w4lr.handmovie.adapter.BaisiAdapter;
import com.w4lr.handmovie.base.BaisiContract;
import com.w4lr.handmovie.bean.BaisiResult.ShowapiResBodyBean.PagebeanBean.ContentlistBean;
import com.w4lr.handmovie.presenter.BaisiPresenter;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by w4lr on 2016/11/19.
 * 百思不得姐页面
 */

public class BaisiFragment extends Fragment implements BaisiContract.View {

    private RecyclerView rv;

    private SwipeRefreshLayout srl;

    private BaisiPresenter mPresenter;

    private BaisiAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_baisi,null);
        initViews(view);
        mPresenter.start(true);
        return view;
    }

    @Override
    public void showDetail() {

    }

    @Override
    public void initViews(View view) {
        srl = (SwipeRefreshLayout) view.findViewById(R.id.srl_baisi);
        rv = (RecyclerView) view.findViewById(R.id.rv_baisi);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadMore();
            }
        });
    }

    @Override
    public void setPresenter(BaisiPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showResult(List<ContentlistBean> result) {
        mAdapter = new BaisiAdapter(getContext(),result);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(manager);
        rv.setAdapter(mAdapter);
    }

    @Override
    public void startLoading() {
        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(true);
            }
        });
    }

    @Override
    public void stopLoading() {
        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(false);
            }
        });
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getContext(),error,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNetWorkError() {
        Toast.makeText(getContext(),"网络错误",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
