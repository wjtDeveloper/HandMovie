package com.w4lr.handmovie.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.w4lr.handmovie.R;
import com.w4lr.handmovie.adapter.HomeAdapter;
import com.w4lr.handmovie.base.HomeContract;
import com.w4lr.handmovie.bean.HomeResult;
import com.w4lr.handmovie.presenter.HomePresenter;

import java.util.List;

/**
 * Created by w4lr on 2016/11/19.
 * 首页
 */
public class HomeFragment extends Fragment implements HomeContract.View {

    private static final String TAG = "HomeFragment";
    /**
     * 悬浮按钮
     */
    private FloatingActionButton fab;

    /**
     * 下拉刷新控件
     */
    private SwipeRefreshLayout srl;

    private HomePresenter mPresenter;
    private RecyclerView rv;
    private HomeAdapter mAdapter;

    private boolean isLoading = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_home,null);
        initViews(view);
        initListener();
        mPresenter.start(true);
        return view;
    }

    /**
     * 初始化页面控件
     * @param view
     */
    @Override
    public void initViews(View view) {
        srl = (SwipeRefreshLayout) view.findViewById(R.id.srl_home);
        rv = (RecyclerView) view.findViewById(R.id.rv_home);
    }

    /**
     * 初始化监听事件
     */
    private void initListener() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.start(true);
            }
        });
    }

    private void loadMore() {
        if (!isLoading) {
            mPresenter.loadMore();
        }
    }

    @Override
    public void setPresenter(HomePresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showResult(List<HomeResult.SubjectsBean> subjects) {
        if (mAdapter == null) {
            //数据第一次加载或刷新的数据
            mAdapter = new HomeAdapter(getContext(),subjects);
            mAdapter.setOnLoadMoreListener(new HomeAdapter.OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    loadMore();
                }
            });
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            rv.setLayoutManager(layoutManager);
            rv.setAdapter(mAdapter);
        } else {
            mAdapter.refreshData(subjects);
        }

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
        Toast.makeText(getContext(),"网络不可用",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDetail() {

    }

}
