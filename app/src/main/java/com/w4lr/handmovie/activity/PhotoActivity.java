package com.w4lr.handmovie.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

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
public class PhotoActivity extends BaseActivity {

    private SubsamplingScaleImageView imageView;
    private int mScreenWidth;

    private ProgressBar pbPhoto;

    private Toolbar mToolbar;

    private boolean isShowStatusAndActionBar = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    /**
     * 加载界面数据
     */
    private void initData() {
        mToolbar.setTitle("图片");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        setSupportActionBar(mToolbar);

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
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        imageView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                return false;
//            }
//        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeStatusBarAndActionBar();
            }
        });
    }

    /**
     * 显示或隐藏 状态栏与ToolBar
     */
    private void changeStatusBarAndActionBar() {
        if (isShowStatusAndActionBar) {
            mToolbar.setVisibility(View.GONE);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            mToolbar.setVisibility(View.VISIBLE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        isShowStatusAndActionBar = !isShowStatusAndActionBar;
    }

    private void getScreenWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mScreenWidth = dm.widthPixels;
    }


}
