package com.w4lr.handmovie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.w4lr.handmovie.R;
import com.w4lr.handmovie.bean.BaisiResult.ShowapiResBodyBean.PagebeanBean.ContentlistBean;
import com.w4lr.handmovie.util.Glides;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;


/**
 * Created by w4lr on 2016/11/20.
 */

public class BaisiAdapter extends RecyclerView.Adapter {

    public static final String TAG = "BaisiAdapter";

    /**
     * 视频
     */
    public static final int TYPE_VIDEO = 0;

    /**
     * 图片
     */
    public static final int TYPE_IMAGE = 1;

    /**
     * 音频
     */
    public static final int TYPE_AUDIO = 2;

    /**
     * 段子
     */
    public static final int TYPE_DUANZI = 3;

    private List<ContentlistBean> mDataSets;

    private Context mContext;

    public BaisiAdapter(Context context, List<ContentlistBean> dataSets) {
        mContext = context;
        mDataSets = dataSets;
    }

    @Override
    public int getItemViewType(int position) {
        ContentlistBean bean = mDataSets.get(position);
        if ("41".equals(bean.getType())) {
            return TYPE_VIDEO;
        }
        if ("10".equals(bean.getType())) {
            return TYPE_IMAGE;
        }
        if ("31".equals(bean.getType())) {
            return TYPE_AUDIO;
        }
        if ("29".equals(bean.getType())) {
            return TYPE_DUANZI;
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_baisi, null);
        return new BaisiHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        ContentlistBean data = mDataSets.get(position);
        Log.e(TAG, "onBindViewHolder: type = " + type);
        //填充公共数据
        BaisiHolder baisiHolder = (BaisiHolder) holder;
        baisiHolder.setData(data);

        Glides.loadBitmap(data.getProfile_image(),baisiHolder.mProfileImage);
        baisiHolder.mLove.setText(data.getLove());
        baisiHolder.mHate.setText(data.getHate());
        baisiHolder.mText.setText(data.getText());
        baisiHolder.mProfileName.setText(data.getName());

        //非段子类型时，文本显示一行
        if (type == TYPE_DUANZI) {
            baisiHolder.mText.setMaxLines(Integer.MAX_VALUE);
        } else {
            baisiHolder.mText.setMaxLines(1);
        }

        //非视频与音频类型时，隐藏播放按钮,播放器控件
        if (type != TYPE_VIDEO && type != TYPE_AUDIO) {
            baisiHolder.mPlayer.setVisibility(View.GONE);
        } else {
            baisiHolder.mPlayer.setVisibility(View.VISIBLE);
        }

        //非图片类型时，隐藏图片
        if (type != TYPE_IMAGE) {
            baisiHolder.mImage.setVisibility(View.GONE);
        } else {
            baisiHolder.mImage.setVisibility(View.VISIBLE);
        }

        switch (type) {
            case TYPE_VIDEO:
                baisiHolder.mPlayer.setUp(data.getVideo_uri(),JCVideoPlayer.SCREEN_LAYOUT_LIST,
                        data.getText());
                break;
            case TYPE_AUDIO:
                baisiHolder.mPlayer.setUp(data.getVoiceuri(),JCVideoPlayer.SCREEN_LAYOUT_LIST,
                        data.getText());
                break;
            case TYPE_IMAGE:
                Glides.loadBitmap(data.getImage0(),baisiHolder.mImage);
                break;
            case TYPE_DUANZI:
                baisiHolder.mText.setText(data.getText());
                break;
        }


    }

    @Override
    public int getItemCount() {
        return mDataSets == null ? 0 : mDataSets.size();
    }

    class BaisiHolder extends RecyclerView.ViewHolder {

        private TextView mText;
        private TextView mHate;
        private TextView mLove;
        private ImageView mImage;
        private ImageView mProfileImage;
        private TextView mProfileName;
        private JCVideoPlayerStandard mPlayer;

        private ContentlistBean mItemData;

        public BaisiHolder(View itemView) {
            super(itemView);
            mText = (TextView) itemView.findViewById(R.id.tv_item_baisi_text);
            mLove = (TextView) itemView.findViewById(R.id.tv_item_baisi_love);
            mHate = (TextView) itemView.findViewById(R.id.tv_item_baisi_hate);
            mImage = (ImageView) itemView.findViewById(R.id.iv_item_baisi_image);
            mProfileImage = (ImageView) itemView.findViewById(R.id.iv_baisi_profile_image);
            mProfileName = (TextView) itemView.findViewById(R.id.tv_baisi_profile_name);
            mPlayer = (JCVideoPlayerStandard) itemView.findViewById(R.id.jc_item_baisi);
//            initListener();
        }

        public void setData(ContentlistBean data) {
            mItemData = data;
        }

        private void initListener() {
//            //播放按钮的监听
//            mPlayer.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mItemData.getVideo_uri() != null) {
//                        //视频类型
//                        Log.e(TAG, "onClick: audio url = " + mItemData.getVideo_uri());
//                        mPlayer.setUp(mItemData.getVideo_uri(),JCVideoPlayer.SCREEN_LAYOUT_LIST,
//                                mItemData.getText());
//                    } else {
//                        //音频类型
//                        Log.e(TAG, "onClick: audio url = " + mItemData.getVoiceuri());
//                        mPlayer.setUp(mItemData.getVoiceuri(),JCVideoPlayer.SCREEN_LAYOUT_LIST,
//                                mItemData.getText());
//                    }
//                    mPlayer.startButton.performClick();
//
//                }
//            });
        }

    }

}
