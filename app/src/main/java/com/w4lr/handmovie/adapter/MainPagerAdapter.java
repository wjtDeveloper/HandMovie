package com.w4lr.handmovie.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.w4lr.handmovie.factory.FragmentFactory;
import com.w4lr.handmovie.fragment.BaisiFragment;
import com.w4lr.handmovie.fragment.HomeFragment;
import com.w4lr.handmovie.presenter.BaisiPresenter;
import com.w4lr.handmovie.presenter.HomePresenter;

/**
 * Created by w4lr on 2016/11/19.
 * 主页viewpager的adapter,展示三个fragment
 */

public class MainPagerAdapter extends FragmentPagerAdapter {

    private static String[] mTitles = {"首页","推荐","预告"};

    private Context mContext;

    public MainPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) {
            HomeFragment fragment = (HomeFragment) FragmentFactory.create(
                    FragmentFactory.TYPE_HOME);
            new HomePresenter(mContext,fragment);
            return fragment;
        } else if (position == 1) {
            BaisiFragment fragment = (BaisiFragment) FragmentFactory.create(
                    FragmentFactory.TYPE_BAISI);
            new BaisiPresenter(mContext,fragment);
            return fragment;
        } else if (position == 2) {
            return FragmentFactory.create(FragmentFactory.TYPE_FROECAST);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
