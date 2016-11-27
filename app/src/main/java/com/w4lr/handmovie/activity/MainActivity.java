package com.w4lr.handmovie.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;

import com.w4lr.handmovie.R;
import com.w4lr.handmovie.adapter.MainPagerAdapter;
import com.w4lr.handmovie.util.StatusBarCompat;
import com.w4lr.handmovie.util.UiUtils;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ViewPager vp;
    private TabLayout mTabLayout;
    private MainPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        UiUtils.setStatusBarColor(this, Color.WHITE);


        if (Build.VERSION.SDK_INT >= 23) {
            //申请权限
            getPermission();
        }

    }

    /**
     * 初始化界面控件
     */
    private void initViews() {
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

    /**
     * 申请写SD卡的权限，用于缓存
     */
    public void getPermission() {
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
}
