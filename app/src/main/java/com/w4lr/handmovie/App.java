package com.w4lr.handmovie;

import android.app.Application;
import android.content.Context;

/**
 * Created by w4lr on 2016/11/19.
 */
public class App  extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return mContext;
    }
}