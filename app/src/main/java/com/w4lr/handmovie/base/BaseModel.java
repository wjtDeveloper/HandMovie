package com.w4lr.handmovie.base;

import com.w4lr.handmovie.inter.ModelCallBack;

/**
 * Created by w4lr on 2016/11/19.
 */

public interface BaseModel {

    void loadData(String url, boolean latest, ModelCallBack callBack);

}
