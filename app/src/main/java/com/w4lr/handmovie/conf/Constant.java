package com.w4lr.handmovie.conf;

import java.net.PortUnreachableException;

/**
 * Created by w4lr on 2016/11/19.
 */

public class Constant {

    /**
     * 缓存文件有效时间-> 10分钟
     */
    public static final long EXPIRES = 10 * 60 * 1000;

    public static final class URL {

        public static final String DOUBAN_BASE_URL = "http://api.douban.com/";

        public static final String DOUBAN_MOVIE_250 = DOUBAN_BASE_URL + "v2/movie/top250?start=0&count=10";

        public static final String BAISI_BASE_URL = "http://route.showapi.com/255-1?showapi_appid=27512&showapi_sign=432edcb3e3e0476ea79648239f776bec";

    }

}
