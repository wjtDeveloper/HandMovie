package com.w4lr.handmovie.factory;

import android.support.v4.app.Fragment;

import com.w4lr.handmovie.fragment.ForecastFragment;
import com.w4lr.handmovie.fragment.HomeFragment;
import com.w4lr.handmovie.fragment.BaisiFragment;

/**
 * Created by w4lr on 2016/11/19.
 */

public class FragmentFactory  {

    private FragmentFactory() {

    }

    /**
     * 首页framgent类型
     */
    public static final int TYPE_HOME = 1;

    /**
     * 百思不得姐页framgent类型
     */
    public static final int TYPE_BAISI = 2;

    /**
     * 推荐页fragment类型
     */
    public static final int TYPE_FROECAST = 3;


    /**
     * 根据首页viewpager选中的位置，创建对应的fragment
     * @param type 要创建的fragment的类型
     * @return 要创建的fragment
     */
    public static Fragment create(int type) {
        if (type == TYPE_HOME) {
            return new HomeFragment();
        } else if (type == TYPE_BAISI) {
            return new BaisiFragment();
        } else if (type == TYPE_FROECAST) {
            return new ForecastFragment();
        } else {
            throw new RuntimeException("You must input a right type of fragment");
        }
    }

}
