package com.w4lr.handmovie.activity;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.w4lr.handmovie.R;

/**
 * Created by w4lr on 2016/11/25.
 */
public class PhotoActivity extends BaseActivity {

    private SubsamplingScaleImageView imageView;
    private int mScreenWidth;

    private ProgressBar pbPhoto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
    }

    /**
     * 加载界面数据
     */
    private void initData() {

        //获取传递过来的数据
        String imageUrl = getIntent().getStringExtra("image_url");
        getScreenWidth();

        Glide.with(this)
                .load(imageUrl)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {

                        int width = bitmap.getWidth();
                        float scale =  mScreenWidth * 1.0f / width / 1.3f;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
//
                                @Override
                                public void onGenerated(Palette palette) {
//                                    int darkVibrantColor = palette.getLightMutedColor(getResources().getColor(R.color.colorPrimary));
////                                StatusBarCompat.compat(PhotoActivity.this,darkVibrantColor);
//                                    SystemBarTintManager tintManager = new SystemBarTintManager(PhotoActivity.this);
//                                    tintManager.setStatusBarTintColor(darkVibrantColor);
                                    Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
                                    if (vibrantSwatch == null) return;
                                    if (android.os.Build.VERSION.SDK_INT >= 21) {
                                        Window window = getWindow();
                                        //顶部状态栏颜色加深
                                        window.setStatusBarColor(colorBurn(vibrantSwatch.getRgb()));
                                    }

                                }
                            });
                        }


                        imageView.setImage(ImageSource.bitmap(bitmap), new ImageViewState(scale, new PointF(0, 0), 0));
                        pbPhoto.setVisibility(View.GONE);
                    }
                });
    }

    /*
   * 颜色加深处理
   * */
    private int colorBurn(int RGBValues) {
        int red = RGBValues >> 16 & 0xFF;
        int green = RGBValues >> 8 & 0xFF;
        int blue = RGBValues & 0xFF;
        red = (int) Math.floor(red * (1 - 0.1));
        green = (int) Math.floor(green * (1 - 0.1));
        blue = (int) Math.floor(blue * (1 - 0.1));
        return Color.rgb(red, green, blue);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_photo;
    }

    @Override
    protected void initViews() {
        imageView = (SubsamplingScaleImageView) findViewById(R.id.imageView);
        pbPhoto = (ProgressBar) findViewById(R.id.pb_photo);
        pbPhoto.setVisibility(View.VISIBLE);
    }

    private void getScreenWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mScreenWidth = dm.widthPixels;
    }


}
