package com.w4lr.handmovie.model;


import android.os.Handler;
import android.os.Message;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.w4lr.handmovie.base.BaseModel;
import com.w4lr.handmovie.conf.Constant;
import com.w4lr.handmovie.inter.ModelCallBack;
import com.w4lr.handmovie.util.FileUtils;
import com.w4lr.handmovie.util.IOUtils;
import com.w4lr.handmovie.util.LogUtils;
import com.w4lr.handmovie.util.MD5Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


/**
 * Created by w4lr on 2016/11/19.
 * 联网获取数据的model实现类，获取数据后会缓存一个带有缓存时期的数据到本地
 */

public class StringModelImpl implements BaseModel {
    /**
     * 获取数据成功
     */
    private static final int RESULT_SUCCESS = 1;

    /**
     * 获取数据失败
     */
    private static final int RESULT_ERROR = 2;

    private String mUrl;

    private ModelCallBack mModelCallBack;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == RESULT_SUCCESS) {
                String result = (String) msg.obj;
                mModelCallBack.onSuccess(result);
            } else if (msg.what == RESULT_ERROR) {
                String error = (String) msg.obj;
                mModelCallBack.onError(error);
            }
        }
    };

    /**
     * 加载数据
     * @param url      api地址
     * @param latest   是否强制获取最新数据
     * @param callBack 回调
     */
    @Override
    public void loadData(String url, boolean latest, ModelCallBack callBack) {
        mUrl = url;
        mModelCallBack = callBack;

        if (!latest) {
            //先查找本地缓存
            if (getDataFromLoacal()) {
                return;
            }
        }

        //直接从网络拉取数据
        getDataFromServer();
    }

    /**
     * 发送数据获取成功的消息
     *
     * @param result
     */
    private void sendSuccessMessage(String result) {
        Message msg = Message.obtain();
        msg.obj = result;
        msg.what = RESULT_SUCCESS;
        mHandler.sendMessage(msg);
    }

    /**
     * 发送获取数据出错的消息
     *
     * @param info
     */
    private void sendErrorMesssage(String info) {
        Message msg = Message.obtain();
        msg.obj = info;
        msg.what = RESULT_ERROR;
        mHandler.sendMessage(msg);
    }

    /**
     * 从网络拉取数据
     */
    private void getDataFromServer() {
        getDataFromServerByOkHttp();

    }

    private void getDataFromServerByOkHttp() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(mUrl)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                LogUtils.e(e.getMessage());
                sendErrorMesssage(e.getMessage());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                LogUtils.e("网络拉取数据成功");
                String result = response.body().string();
                sendSuccessMessage(result);
                cacheResult(result);
            }
        });
    }

    /**
     * 将数据缓存到本地
     */
    private void cacheResult(String result) {
        File file = new File(getCacheDir(), getCacheFileName());
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(System.currentTimeMillis() + "");
            writer.newLine();
            writer.write(result);
            LogUtils.e("缓存数据到本地成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(writer);
        }
    }

    /**
     * 从本地获取数据
     * @return 是否获取到数据
     */
    private boolean getDataFromLoacal() {
        File file = new File(getCacheDir(), getCacheFileName());
        if (file.exists()) {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(file));
                String time = br.readLine();
                long insertTime = Long.parseLong(time);
                if (insertTime - System.currentTimeMillis() < Constant.EXPIRES) {
                    //缓存文件未过期
                    LogUtils.e("本地拉取数据成功");
                    sendSuccessMessage(br.readLine());
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                IOUtils.close(br);
            }
        }
        return false;
    }

    /**
     * 获取要缓存的文件名
     *
     * @return
     */
    private String getCacheFileName() {
        try {
            return MD5Utils.encode(mUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取要缓存的文件夹路径
     *
     * @return
     */
    private String getCacheDir() {
        return FileUtils.getDir("cache");
    }

}
