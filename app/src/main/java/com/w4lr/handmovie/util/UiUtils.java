package com.w4lr.handmovie.util;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * Created by w4lr on 2016/11/22.
 * UI相关工具类
 */

public class UiUtils {

    /**
     * 设置状态栏颜色
     */
    public static void setStatusBarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //加入色块
            View statusView = createStatusView(activity,color);
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            decorView.addView(statusView);
            ViewGroup rootGroup = (ViewGroup) ((ViewGroup)activity.findViewById(android.R.id.content)).getChildAt(0);
            rootGroup.setFitsSystemWindows(true);
            rootGroup.setClipToPadding(true);
        }
    }

    /**
     * 生成和状态栏等高的色块视图
     * @param activity
     * @param color
     * @return
     */
    private static View createStatusView(Activity activity, int color) {
        //得到状态栏高度
        int resourceId = activity.getResources().getIdentifier("status_bar_height","dimen","android");
        int statusBarHeight = activity.getResources().getDimensionPixelOffset(resourceId);
        View statusView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
        statusView.setLayoutParams(params);
        statusView.setBackgroundColor(color);
        return statusView;
    }

}
