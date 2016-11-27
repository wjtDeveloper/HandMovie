package com.w4lr.handmovie.activity;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.w4lr.handmovie.R;

/**
 * Created by w4lr on 2016/11/25.
 */
public class PhotoActivity extends AppCompatActivity {

    private SubsamplingScaleImageView imageView;
    private int mScreenWidth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);


        //获取传递过来的数据
        String imageUrl = getIntent().getStringExtra("image_url");
        getScreenWidth();

        imageView = (SubsamplingScaleImageView) findViewById(R.id.imageView);
        Glide.with(this)
                .load(imageUrl)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {

                        int width = bitmap.getWidth();
                        float scale =  mScreenWidth * 1.0f / width / 1.3f;

                        imageView.setImage(ImageSource.bitmap(bitmap), new ImageViewState(scale, new PointF(0, 0), 0));
                    }
                });

    }

    private void getScreenWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mScreenWidth = dm.widthPixels;
    }


}
