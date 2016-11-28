package com.w4lr.handmovie.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.readystatesoftware.systembartint.SystemBarTintManager;


/**
 * Created by w4lr on 2016/11/28.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private int mLayoutResId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(true);
        // enable navigation bar tint
        tintManager.setNavigationBarTintEnabled(true);

        initViews();
    }

    /**
     * 得到布局资源id
     * @return
     */
    protected abstract int getLayoutResId();

    /**
     * 初始化界面控件
     */
    protected abstract void initViews();

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



}
