package com.w4lr.handmovie.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.w4lr.handmovie.App;

/**
 * Created by w4lr on 2016/11/28.
 */

public class ThemeManager {

    /**
     * 日间主题模式
     */
    public static final int THEME_DAY = 0;

    /**
     * 夜间主题模式
     */
    public static final int THEME_NIGHT = 1;

    /**
     * 当前的主题
     */
    private int mCurrentTheme = -1;

    public static ThemeManager sInstance;

    private Context mContext;

    private ThemeManager(Context context) {
        mContext = context;
    }

    public static ThemeManager getInstance() {
        if (sInstance == null) {
            sInstance = new ThemeManager(App.getAppContext());
        }
        return sInstance;
    }

    /**
     * 得到当前的主题
     * @return
     */
    private int getCurrentTheme() {
        SharedPreferences sp = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getInt("theme", THEME_DAY);
    }

    /**
     * 保存当前的主题
     * @return
     */
    private void saveCurrentTheme() {
        SharedPreferences sp = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putInt("theme",mCurrentTheme).commit();
    }

    /**
     * 设置主题
     * @param act 当前activity
     * @param theme 主题样式
     * @return
     */
    public int setTheme(Activity act, int theme) {
        mCurrentTheme = theme;
        act.finish();
        saveCurrentTheme();
        return 0;
    }

}
