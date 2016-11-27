package com.w4lr.handmovie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.w4lr.handmovie.R;
import com.w4lr.handmovie.bean.HomeResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by w4lr on 2016/11/19.
 */
public class HomeAdapter extends RecyclerView.Adapter {

    private static final int TYPE_NORMAL = 0;

    private static final int TYPE_LOADMORE = 1;

    private Context mContext;

    private List<HomeResult.SubjectsBean> mDataSets;

    private OnLoadMoreListener mOnLoadMoreListener;

    public HomeAdapter(Context context, List<HomeResult.SubjectsBean> dataSets) {
        mContext = context;
        mDataSets = dataSets;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < mDataSets.size()) {
            return TYPE_NORMAL;
        } else {
            return TYPE_LOADMORE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == TYPE_NORMAL) {
            View view = View.inflate(mContext, R.layout.item_home, null);
            holder = new HomeHolder(view);
        } else {
            View view = View.inflate(mContext,R.layout.layout_loadmore,null);
            holder = new LoadMoreHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type == TYPE_NORMAL) {
            HomeResult.SubjectsBean subject = mDataSets.get(position);
            HomeHolder homeHolder = (HomeHolder) holder;
            //显示数据
            Glide.with(mContext)
                    .load(subject.getImages().getSmall())
                    .placeholder(R.drawable.video_default)
                    .error(R.drawable.video_default)
                    .into(homeHolder.ivIcon);
            homeHolder.tvTitle.setText(subject.getTitle());
            homeHolder.tvYear.setText(subject.getYear());
        }
    }

    @Override
    public int getItemCount() {
        return mDataSets.size() + 1;
    }

    /*
     * 刷新数据
     */
    public void refreshData(List<HomeResult.SubjectsBean> subjects) {
        if (mDataSets == null) {
            mDataSets = new ArrayList<>();
        }
        mDataSets.addAll(subjects);
        notifyItemRangeChanged(mDataSets.size(),subjects.size());
    }

    class HomeHolder extends RecyclerView.ViewHolder {

        ImageView ivIcon;
        TextView tvTitle;
        TextView tvYear;

        public HomeHolder(View itemView) {
            super(itemView);
            ivIcon = (ImageView) itemView.findViewById(R.id.iv_item_home_icon);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_item_home_title);
            tvYear = (TextView) itemView.findViewById(R.id.tv_item_home_year);
        }

    }

    class LoadMoreHolder extends RecyclerView.ViewHolder {

        public LoadMoreHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                }
            });
        }
    }


    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener l ) {
        mOnLoadMoreListener = l;
    }

}
