package com.w4lr.handmovie.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.w4lr.handmovie.R;
import com.w4lr.handmovie.adapter.MainPagerAdapter;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class MainActivity extends BaseActivity {

    private Toolbar mToolbar;
    private ViewPager vp;
    private TabLayout mTabLayout;
    private MainPagerAdapter mAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    /**
     * 初始化界面控件
     */
    @Override
    protected void initViews() {
//        StatusBarCompat.translucentStatusBar(this);
        /*---------------初始化toolbar---------------*/
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("HandMovie");
        setSupportActionBar(mToolbar);

        vp = (ViewPager) findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);

        mAdapter = new MainPagerAdapter(getSupportFragmentManager(),this);
        vp.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(vp);

    }



    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
}
