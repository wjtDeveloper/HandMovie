package com.w4lr.handmovie.util;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.w4lr.handmovie.App;
import com.w4lr.handmovie.R;

/**
 * Created by w4lr on 2016/11/21.
 */

public class Glides {

    private static final String TAG = "Glides";

    public static void loadBitmap(String url, final ImageView imageView) {
        Glide.with(App.getAppContext())
                .load(url)
                .asBitmap()
                .centerCrop()
                .error(R.drawable.icon_discover_normal)
                .into(imageView);
//                .into(new SimpleTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
//                        int height = bitmap.getHeight();
//                        int width = bitmap.getWidth();
//                        Log.e(TAG, "onResourceReady: width = " + width);
//                        Log.e(TAG, "onResourceReady: height = " + height);
//                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
//                        int width1 = imageView.getWidth();
//                        int height1 = (int) (width1 * 1.0f / width * height + .5f);
//                        params.height = height1;
//                        params.width = width1;
//                        imageView.setLayoutParams(params);
//
//                    }
//
//                });
    }

}
