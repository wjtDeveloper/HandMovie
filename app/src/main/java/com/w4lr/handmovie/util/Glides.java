package com.w4lr.handmovie.util;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.w4lr.handmovie.App;
import com.w4lr.handmovie.R;

/**
 * Created by w4lr on 2016/11/21.
 */

public class Glides {

    public static void loadBitmap(String url, ImageView imageView) {
        Glide.with(App.getAppContext())
                .load(url)
                .placeholder(R.drawable.icon_discover_normal)
                .error(R.drawable.icon_discover_normal)
                .into(imageView);
    }

}
