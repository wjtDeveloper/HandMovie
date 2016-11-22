package com.w4lr.handmovie.util;

import android.content.Context;
import android.content.res.Resources;

import com.w4lr.handmovie.App;

/**
 * Created by w4lr on 2016/11/14.
 */

public class Utils {

    public static Context getContext() {
        return App.getAppContext();
    }

    public static Resources getResource() {
        return getContext().getResources();
    }

    public static String getString(int resId) {
        return getResource().getString(resId);
    }

    public static String[] getStringArray(int resId) {
        return getResource().getStringArray(resId);
    }

    public static int getColor(int resId) {
        return getResource().getColor(resId);
    }

    public static String getPackageName() {
        return getContext().getPackageName();
    }

}
